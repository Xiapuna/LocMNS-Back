package com.mns.cda.locmnsback.services;

import com.mns.cda.locmnsback.dao.LoanDao;
import com.mns.cda.locmnsback.enums.LoanStatus;
import com.mns.cda.locmnsback.model.Loan;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class LoanService {

    private final LoanDao loanDao;

    public Loan startLoan(Integer id) {
        Loan loan = loanDao.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Prêt introuvable avec l'id : " + id));

        if (loan.getLoanStatus() != LoanStatus.VALIDATED) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Le prêt ne peut pas être démarré car il n'est pas au statut VALIDATED.");
        }

        if (loan.getStartDate().isAfter(LocalDate.now())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Impossible de démarrer un prêt avant sa date de début.");
        }

        loan.setLoanStatus(LoanStatus.ONGOING);

        return loanDao.save(loan);
    }

    public Loan requestExtension(Integer id) {
        Loan loan = loanDao.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Prêt introuvable avec l'id : " + id));

        if (loan.getLoanStatus() != LoanStatus.ONGOING) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Impossible de demander une prolongation : le prêt n'est pas en cours.");
        }

        loan.setLoanStatus(LoanStatus.REQUESTED_EXTENSION);

        return loanDao.save(loan);
    }

    public Loan requestReturn(Integer id) {
        Loan loan = loanDao.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Prêt introuvable avec l'id : " + id));

        if (loan.getLoanStatus() != LoanStatus.ONGOING) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Impossible de demander un retour : le prêt n'est pas en cours.");
        }

        loan.setLoanStatus(LoanStatus.REQUESTED_RETURN);

        return loanDao.save(loan);
    }

}
