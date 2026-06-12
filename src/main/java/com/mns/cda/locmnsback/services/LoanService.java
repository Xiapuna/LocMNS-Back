package com.mns.cda.locmnsback.services;

import com.mns.cda.locmnsback.dao.*;
import com.mns.cda.locmnsback.dto.LoanCreateDto;
import com.mns.cda.locmnsback.dto.LoanHistoryDto;
import com.mns.cda.locmnsback.dto.UserReservationDto;
import com.mns.cda.locmnsback.enums.LoanStatus;
import com.mns.cda.locmnsback.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import javax.xml.parsers.SAXParser;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LoanService {

    private final LoanDao loanDao;
    private final AppUserDao appUserDao;
    private final LoanStateDao loanStateDao;
    private final LoanHistoryDao loanHistoryDao;
    private final EquipmentDao equipmentDao;

    public Loan startLoan(int id) {
        Loan loan = loanDao.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Prêt introuvable"
                ));

        if (loan.getLoanStatus() != LoanStatus.VALIDATED) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Le prêt ne peut pas être démarré car il n'est pas au statut VALIDATED.");
        }

        if (loan.getStartDate().isAfter(LocalDate.now())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Impossible de démarrer un prêt avant sa date de début.");
        }

        loan.setLoanStatus(LoanStatus.ONGOING);
        addHistory(loan, LoanStatus.ONGOING);

        return loanDao.save(loan);
    }

    public void requestExtension(int id, AppUser userRequester) {
        Loan loan = loanDao.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Prêt introuvable"
                ));

        boolean isAdmin = userRequester.getRole().getName().equals("ADMIN");
        boolean isOwner = userRequester.getId().equals(loan.getAppUser().getId());

        if (!isAdmin && !isOwner) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Vous ne pouvez pas demander une extension de ce prêt."
            );
        }

        if (loan.getLoanStatus() != LoanStatus.ONGOING
                && loan.getLoanStatus() != LoanStatus.VALIDATED) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Impossible de demander une prolongation : le prêt n'est pas en cours.");
        }

        loan.setLoanStatus(LoanStatus.REQUESTED_EXTENSION);
        addHistory(loan, LoanStatus.REQUESTED_EXTENSION);
        loanDao.save(loan);
    }

    public void requestReturn(int id, AppUser userRequester) {
        Loan loan = loanDao.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Prêt introuvable"
                ));

        boolean isAdmin = userRequester.getRole().getName().equals("ADMIN");
        boolean isOwner = userRequester.getId().equals(loan.getAppUser().getId());

        if (!isAdmin && !isOwner) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Vous ne pouvez pas demander le retour de ce prêt."
            );
        }

        if (loan.getLoanStatus() != LoanStatus.ONGOING
                && loan.getLoanStatus() != LoanStatus.VALIDATED) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Impossible de demander un retour : le prêt n'est pas en cours.");
        }

        loan.setLoanStatus(LoanStatus.REQUESTED_RETURN);
        addHistory(loan, LoanStatus.REQUESTED_RETURN);

        loanDao.save(loan);
    }

    public Loan extendLoan(Integer id, LocalDate newEndDate) {
        Loan loan = loanDao.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Prêt introuvable"
                ));

        if (loan.getLoanStatus() != LoanStatus.REQUESTED_EXTENSION) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "La prolongation ne peut être validée que si le prêt est en statut REQUESTED_EXTENSION.");
        }

        if (newEndDate.isBefore(loan.getEndDate())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "La nouvelle date de fin du prêt doit être postérieure à la date actuelle.");
        }

        List<Loan> existingLoans = loanDao.findByEquipmentId(loan.getEquipment().getId());

        for (Loan existing : existingLoans) {
            if (!existing.getId().equals(loan.getId())) {
                boolean overlap =
                        !newEndDate.isBefore(existing.getStartDate())
                                && !loan.getStartDate().isAfter(existing.getEndDate());

                if (overlap) {
                    throw new ResponseStatusException(
                            HttpStatus.CONFLICT,
                            "Impossible de prolonger : chevauchement avec un autre prêt du "
                                    + existing.getStartDate() + " au " + existing.getEndDate());
                }
            }
        }

        loan.setEndDate(newEndDate);

        LocalDate today = LocalDate.now();

        if (today.isBefore(loan.getStartDate())) {
            loan.setLoanStatus(LoanStatus.VALIDATED);
        } else if (today.isAfter(loan.getEndDate())) {
            loan.setLoanStatus(LoanStatus.RETURNED);
        } else {
            loan.setLoanStatus(LoanStatus.ONGOING);
        }

        addHistory(loan, loan.getLoanStatus());

        return loanDao.save(loan);
    }

    public Loan validateReturn(int id) {
        Loan loan = loanDao.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Prêt introuvable"
                ));

        if (loan.getLoanStatus() != LoanStatus.REQUESTED_RETURN) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Le retour ne peut être validé que si le prêt est en statut REQUESTED_RETURN.");
        }

        loan.setRealEndDate(LocalDate.now());
        loan.setLoanStatus(LoanStatus.RETURNED);
        addHistory(loan, LoanStatus.RETURNED);

        return loanDao.save(loan);
    }

    private void addHistory(Loan loan, LoanStatus newStatus) {
        LoanState state = loanStateDao.findByName(newStatus.name());

        if (state == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "État de prêt introuvable pour le statut : " + newStatus.name());
        }

        LoanHistory history = new LoanHistory();
        history.setLoan(loan);
        history.setLoanState(state);
        history.setDateChangement(LocalDateTime.now());

        loanHistoryDao.save(history);
    }
    @Transactional
    public Loan createLoan(Loan loan) {
        loan.setLoanStatus(LoanStatus.VALIDATED);
        Loan saved = loanDao.save(loan);
        addHistory(saved, LoanStatus.VALIDATED);

        return saved;
    }

    public List<UserReservationDto> getUserLoans(int id, AppUser userRequester) {
        AppUser userTarget = appUserDao.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Utilisateur introuvable"
                ));

        boolean isAdmin = userRequester.getRole().getName().equals("ADMIN");
        boolean isSelf = userRequester.getId() == userTarget.getId();

        if (!isAdmin && !isSelf) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Accès refusé"
            );
        }

        return loanDao.findByAppUserId(id)
                .stream()
                .map(l -> new UserReservationDto(
                        l.getId(),
                        l.getEquipment().getId(),
                        l.getEquipment().getModel().getType().getId(),
                        l.getEquipment().getName(),
                        l.getStartDate(),
                        l.getEndDate(),
                        l.getLoanStatus().name()
                ))
                .toList();

    }

    public List<Loan> getAll() {
        return loanDao.findAll();
    }

    public Loan get(int id) {
        return loanDao.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Prêt introuvable"
                ));
    }

    public List<Loan> getByStatus(LoanStatus status) {
        if (status == null) {
            return loanDao.findAll();
        }

        return loanDao.findByLoanStatus(status);
    }

    public List<LoanHistoryDto> getLoanHistory(int id) {
        Loan loan = loanDao.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Prêt introuvable"
                ));

        return loan.getHistory()
                .stream()
                .map(h -> new LoanHistoryDto(
                        loan.getId(),
                        h.getDateChangement().toLocalDate(),
                        h.getLoanState().getName()
                ))
                .toList();
    }

    public void delete(int id) {
        Loan loan = loanDao.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Prêt introuvable"
                ));

        loanDao.delete(loan);

    }

    public void update(int id, Loan loanToUpdate) {
        Loan existing = loanDao.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Prêt introuvable"
                ));

        loanToUpdate.setId(existing.getId());

        loanDao.save(loanToUpdate);
    }

    public Loan create(LoanCreateDto dto) {
        LocalDate startDate = dto.startDate();
        LocalDate endDate = dto.endDate();

        if(startDate.isBefore(LocalDate.now())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "La date de début du prêt ne peux pas être dans le passé."
            );
        }

        if(endDate.isBefore(startDate)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "La date de fin du prêt doit être après la date de début."
            );
        }

        Equipment equipment = equipmentDao.findById(dto.equipmentId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Équipement introuvable"
                ));

        AppUser user = appUserDao.findById(dto.appUserId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Utilisateur introuvable"
                ));

        List<Loan> existingLoan = loanDao.findByEquipmentId(dto.equipmentId());
        for (Loan existing : existingLoan) {
            boolean overlap =
                    !startDate.isAfter(existing.getEndDate()) &&
                    !endDate.isBefore(existing.getStartDate());

            if (overlap) {
                throw new ResponseStatusException(
                        HttpStatus.CONFLICT,
                        "L'équipement est déjà réservé du " + existing.getStartDate() + " au " + existing.getEndDate()
                );
            }
        }

        Loan loan = new Loan();
        loan.setStartDate(startDate);
        loan.setEndDate(endDate);
        loan.setEquipment(equipment);
        loan.setAppUser(user);
        loan.setLoanStatus(LoanStatus.VALIDATED);

        return loanDao.save(loan);

    }

}
