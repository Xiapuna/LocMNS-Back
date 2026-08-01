package com.mns.cda.locmnsback.services;

import com.mns.cda.locmnsback.dao.*;
import com.mns.cda.locmnsback.dto.*;
import com.mns.cda.locmnsback.enums.LoanStatus;
import com.mns.cda.locmnsback.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LoanService {

    private final LoanDao loanDao;
    private final AppUserDao appUserDao;
    private final LoanStateDao loanStateDao;
    private final LoanHistoryDao loanHistoryDao;
    private final EquipmentDao equipmentDao;

    // --- NOUVELLE MÉTHODE OUTIL ---
    // Permet de récupérer le statut actuel en lisant le dernier historique
    private LoanStatus getCurrentStatus(Loan loan) {
        if (loan.getHistory() == null || loan.getHistory().isEmpty()) {
            return null;
        }
        return loan.getHistory().stream()
                .max(Comparator.comparing(LoanHistory::getDateChangement))
                .map(h -> LoanStatus.valueOf(h.getLoanState().getName()))
                .orElse(null);
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

        // On l'ajoute à la liste en mémoire pour que les DTO soient à jour instantanément
        loan.getHistory().add(history);
    }

    public List<UserReservationDto> getUserLoans(int id, AppUser userRequester) {
        AppUser userTarget = appUserDao.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Utilisateur introuvable"
                ));

        boolean isAdmin = userRequester.getRole().getName().equals("ADMIN");
        boolean isSelf = userRequester.getId().equals(userTarget.getId());

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
                        getCurrentStatus(l) != null ? getCurrentStatus(l).name() : "UNKNOWN"
                ))
                .toList();

    }

    public List<LoanDto> getAll() {
        return loanDao.findAll()
                .stream()
                .map(l -> new LoanDto(
                        l.getId(),
                        l.getStartDate(),
                        l.getEndDate(),
                        getCurrentStatus(l) != null ? getCurrentStatus(l).name() : "UNKNOWN",
                        l.getEquipment().getId(),
                        l.getEquipment().getName(),
                        l.getAppUser().getId(),
                        l.getAppUser().getName(),
                        l.getAppUser().getFirstName()
                ))
                .toList();
    }

    public LoanDto get(int id) {
        Loan l = loanDao.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Prêt introuvable"
                ));

        return new LoanDto(
                l.getId(),
                l.getStartDate(),
                l.getEndDate(),
                getCurrentStatus(l) != null ? getCurrentStatus(l).name() : "UNKNOWN",
                l.getEquipment().getId(),
                l.getEquipment().getName(),
                l.getAppUser().getId(),
                l.getAppUser().getName(),
                l.getAppUser().getFirstName()
        );
    }

    public List<LoanDto> getByStatus(LoanStatus status) {
        List<Loan> allLoans = loanDao.findAll();

        // On filtre en Java car le statut n'est plus une simple colonne en base de données
        if (status != null) {
            allLoans = allLoans.stream()
                    .filter(l -> getCurrentStatus(l) == status)
                    .toList();
        }

        return allLoans.stream()
                .map(l -> new LoanDto(
                        l.getId(),
                        l.getStartDate(),
                        l.getEndDate(),
                        getCurrentStatus(l) != null ? getCurrentStatus(l).name() : "UNKNOWN",
                        l.getEquipment().getId(),
                        l.getEquipment().getName(),
                        l.getAppUser().getId(),
                        l.getAppUser().getName(),
                        l.getAppUser().getFirstName()
                ))
                .toList();
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

    public LoanDto create(LoanCreateDto dto) {
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

        // On sauvegarde d'abord le prêt pour qu'il ait un ID en base de données
        Loan saved = loanDao.save(loan);

        // Puis on lui ajoute son statut initial via l'historique
        addHistory(saved, LoanStatus.VALIDATED);

        return new LoanDto(
                saved.getId(),
                saved.getStartDate(),
                saved.getEndDate(),
                getCurrentStatus(saved).name(),
                saved.getEquipment().getId(),
                saved.getEquipment().getName(),
                saved.getAppUser().getId(),
                saved.getAppUser().getName(),
                saved.getAppUser().getFirstName()
        );

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

        LoanStatus currentStatus = getCurrentStatus(loan);
        if (currentStatus != LoanStatus.ONGOING && currentStatus != LoanStatus.VALIDATED) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Impossible de demander un retour : le prêt n'est pas en cours.");
        }

        addHistory(loan, LoanStatus.REQUESTED_RETURN);
        loanDao.save(loan);
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

        LoanStatus currentStatus = getCurrentStatus(loan);
        if (currentStatus != LoanStatus.ONGOING && currentStatus != LoanStatus.VALIDATED) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Impossible de demander une prolongation : le prêt n'est pas en cours.");
        }

        addHistory(loan, LoanStatus.REQUESTED_EXTENSION);
        loanDao.save(loan);
    }

    public void delete(int id) {
        Loan loan = loanDao.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Prêt introuvable"
                ));

        loanDao.delete(loan);

    }

    public LoanDto update(int id, LoanUpdateDto dto) {
        Loan loan = loanDao.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Prêt introuvable"
                ));

        if (loan.getStartDate().isBefore(LocalDate.now())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Impossible de modifier un prêt déjà commencé."
            );
        }

        if (dto.startDate().isAfter(dto.endDate())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "La date de fin doit être après la date de début."
            );
        }

        loan.setStartDate(dto.startDate());
        loan.setEndDate(dto.endDate());

        Loan saved = loanDao.save(loan);

        return new LoanDto(
                saved.getId(),
                saved.getStartDate(),
                saved.getEndDate(),
                getCurrentStatus(saved).name(),
                saved.getEquipment().getId(),
                saved.getEquipment().getName(),
                saved.getAppUser().getId(),
                saved.getAppUser().getName(),
                saved.getAppUser().getFirstName()
        );
    }

    public LoanDto startLoan(int id) {
        Loan loan = loanDao.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Prêt introuvable"
                ));

        if (getCurrentStatus(loan) != LoanStatus.VALIDATED) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Le prêt ne peut pas être démarré car il n'est pas au statut VALIDATED.");
        }

        if (loan.getStartDate().isAfter(LocalDate.now())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Impossible de démarrer un prêt avant sa date de début.");
        }

        addHistory(loan, LoanStatus.ONGOING);
        Loan saved = loanDao.save(loan);

        return new LoanDto(
                saved.getId(),
                saved.getStartDate(),
                saved.getEndDate(),
                getCurrentStatus(saved).name(),
                saved.getEquipment().getId(),
                saved.getEquipment().getName(),
                saved.getAppUser().getId(),
                saved.getAppUser().getName(),
                saved.getAppUser().getFirstName()
        );
    }

    public LoanDto extendLoan(Integer id, LocalDate newEndDate) {
        Loan loan = loanDao.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Prêt introuvable"
                ));

        if (getCurrentStatus(loan) != LoanStatus.REQUESTED_EXTENSION) {
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
        LoanStatus nextStatus;

        if (today.isBefore(loan.getStartDate())) {
            nextStatus = LoanStatus.VALIDATED;
        } else if (today.isAfter(loan.getEndDate())) {
            nextStatus = LoanStatus.RETURNED;
        } else {
            nextStatus = LoanStatus.ONGOING;
        }

        addHistory(loan, nextStatus);
        Loan saved = loanDao.save(loan);

        return new LoanDto(
                saved.getId(),
                saved.getStartDate(),
                saved.getEndDate(),
                getCurrentStatus(saved).name(),
                saved.getEquipment().getId(),
                saved.getEquipment().getName(),
                saved.getAppUser().getId(),
                saved.getAppUser().getName(),
                saved.getAppUser().getFirstName()
        );
    }

    public LoanDto validateReturn(int id) {
        Loan loan = loanDao.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Prêt introuvable"
                ));

        if (getCurrentStatus(loan) != LoanStatus.REQUESTED_RETURN) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Le retour ne peut être validé que si le prêt est en statut REQUESTED_RETURN.");
        }

        loan.setRealEndDate(LocalDate.now());
        addHistory(loan, LoanStatus.RETURNED);

        Loan saved = loanDao.save(loan);

        return new LoanDto(
                saved.getId(),
                saved.getStartDate(),
                saved.getEndDate(),
                getCurrentStatus(saved).name(),
                saved.getEquipment().getId(),
                saved.getEquipment().getName(),
                saved.getAppUser().getId(),
                saved.getAppUser().getName(),
                saved.getAppUser().getFirstName()
        );
    }
}