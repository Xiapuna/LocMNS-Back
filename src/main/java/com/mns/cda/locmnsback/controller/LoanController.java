package com.mns.cda.locmnsback.controller;

import com.mns.cda.locmnsback.dao.LoanDao;
import com.mns.cda.locmnsback.dto.LoanCreateDto;
import com.mns.cda.locmnsback.dto.LoanExtensionDto;
import com.mns.cda.locmnsback.model.AppUser;
import com.mns.cda.locmnsback.model.Equipment;
import com.mns.cda.locmnsback.model.Loan;
import com.mns.cda.locmnsback.security.IsAdmin;
import com.mns.cda.locmnsback.security.IsUser;
import com.mns.cda.locmnsback.services.LoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class LoanController {

    protected final LoanDao loanDao;
    protected final LoanService loanService;

    @GetMapping("/loan/list")
    @IsUser
    public List<Loan> getAll() {
        return loanDao.findAll();
    }

    @GetMapping("/loan/{id}")
    @IsUser
    public ResponseEntity<Loan> get(@PathVariable int id) {

        Optional<Loan> optionalLoan = loanDao.findById(id);

        if(optionalLoan.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(optionalLoan.get(), HttpStatus.OK);
    }

    @PostMapping("/loan")
    @IsUser
    public ResponseEntity<?> create(@RequestBody LoanCreateDto loanCreateDto) {

        System.out.println(">>> CONTROLLER APPELÉ <<<");

        LocalDate startDate = loanCreateDto.startDate();
        LocalDate endDate = loanCreateDto.endDate();
        Integer equipmentId = loanCreateDto.equipmentId();
        Integer appUserId = loanCreateDto.appUserId();

        if (startDate.isBefore(LocalDate.now())) {
            return new ResponseEntity<>("La date de début ne peut pas être dans le passé.", HttpStatus.BAD_REQUEST);
        }

        if (endDate.isBefore(startDate)) {
            return new ResponseEntity<>("La date de fin doit être après la date de début.", HttpStatus.BAD_REQUEST);
        }

        List<Loan> existingLoans = loanDao.findByEquipmentId(equipmentId);

        for (Loan existing : existingLoans) {
            LocalDate existingStart = existing.getStartDate();
            LocalDate existingEnd = existing.getEndDate();

            boolean overlap =
                !startDate.isAfter(existing.getEndDate()) &&
                !endDate.isBefore(existing.getStartDate());

            if (overlap) {
                return new ResponseEntity(
                    "L'équipement est déjà réservé du " + existingStart + " au " + existingEnd,
                    HttpStatus.CONFLICT
                );
            }
        }

        Loan loan = new Loan();
        loan.setStartDate(startDate);
        loan.setEndDate(endDate);

        Equipment equipment = new Equipment();
        equipment.setId(equipmentId);
        loan.setEquipment(equipment);

        AppUser user = new AppUser();
        user.setId(appUserId);
        loan.setAppUser(user);

        loanDao.save(loan);

        return new ResponseEntity<>(loan, HttpStatus.CREATED);
    }

    @DeleteMapping("/loan/{id}")
    @IsAdmin
    public ResponseEntity<Loan> delete(@PathVariable int id) {

        Optional<Loan> optionalLoan = loanDao.findById(id);

        if(optionalLoan.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        loanDao.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/loan/{id}")
    @IsAdmin
    public ResponseEntity<Void> update(@PathVariable int id, @RequestBody Loan loanToUpdate) {

        Optional<Loan> optionalLoan = loanDao.findById(id);

        if(optionalLoan.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        loanToUpdate.setId(id);

        loanDao.save(loanToUpdate);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/loans/{id}/start")
    @IsUser
    public Loan startLoan(@PathVariable int id) {
        return loanService.startLoan(id);
    }

    @PutMapping("/loans/{id}/request-extension")
    @IsUser
    public Loan requestExtension(@PathVariable int id) {
        return loanService.requestExtension(id);
    }

    @PutMapping("/loans/{id}/request-return")
    @IsUser
    public Loan requestReturn(@PathVariable int id) {
        return loanService.requestReturn(id);
    }

    @PutMapping("/loans/{id}/extend")
    @IsAdmin
    public Loan extendLoan(@PathVariable int id, @RequestBody LoanExtensionDto dto) {
        return loanService.extendLoan(id, dto.newEndDate());
    }


}
