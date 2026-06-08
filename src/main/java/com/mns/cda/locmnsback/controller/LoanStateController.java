package com.mns.cda.locmnsback.controller;

import com.mns.cda.locmnsback.dao.LoanStateDao;
import com.mns.cda.locmnsback.model.LoanState;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class LoanStateController {

    protected final LoanStateDao loanStateDao;

    @GetMapping("/state/list")
    public List<LoanState> getAll() {
        return loanStateDao.findAll();
    }

    @GetMapping("/state/{id}")
    public ResponseEntity<LoanState> get(@PathVariable int id) {

        Optional<LoanState> optionalState = loanStateDao.findById(id);

        if(optionalState.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(optionalState.get(), HttpStatus.OK);
    }

    @PostMapping("/state")
    public ResponseEntity<LoanState> create(@RequestBody LoanState loanStateToInsert) {

        loanStateToInsert.setId(null);

        loanStateDao.save(loanStateToInsert);

        return new ResponseEntity<>(loanStateToInsert, HttpStatus.CREATED);
    }

    @DeleteMapping("/state/{id}")
    public ResponseEntity<LoanState> delete(@PathVariable int id) {
        Optional<LoanState> optionalState = loanStateDao.findById(id);

        if(optionalState.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        loanStateDao.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/state/{id}")
    public ResponseEntity<LoanState> update(@PathVariable int id, @RequestBody LoanState loanStateToUpdate) {
        Optional<LoanState> optionalState = loanStateDao.findById(id);

        if(optionalState.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        loanStateToUpdate.setId(id);

        loanStateDao.save(loanStateToUpdate);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

