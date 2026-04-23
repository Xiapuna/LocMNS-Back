package com.mns.cda.locmnsback.controller;

import com.mns.cda.locmnsback.dao.LoanDao;
import com.mns.cda.locmnsback.model.Loan;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class LoanController {

    protected final LoanDao loanDao;

    @GetMapping("/loan/list")
    public List<Loan> getAll() {
        return loanDao.findAll();
    }

    @GetMapping("/loan/{id}")
    public ResponseEntity<Loan> get(@PathVariable int id) {

        Optional<Loan> optionalLoan = loanDao.findById(id);

        if(optionalLoan.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(optionalLoan.get(), HttpStatus.OK);
    }

    @PostMapping("/loan")
    public ResponseEntity<Loan> create(@RequestBody Loan loanToInsert) {

        loanToInsert.setId(null);

        loanDao.save(loanToInsert);

        return new ResponseEntity<>(loanToInsert, HttpStatus.CREATED);
    }

    @DeleteMapping("/loan/{id}")
    public ResponseEntity<Loan> delete(@PathVariable int id) {

        Optional<Loan> optionalLoan = loanDao.findById(id);

        if(optionalLoan.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        loanDao.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/loan/{id}")
    public ResponseEntity<Void> update(@PathVariable int id, @RequestBody Loan loanToUpdate) {

        Optional<Loan> optionalLoan = loanDao.findById(id);

        if(optionalLoan.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        loanToUpdate.setId(id);

        loanDao.save(loanToUpdate);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
