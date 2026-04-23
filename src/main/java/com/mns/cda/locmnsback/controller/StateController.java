package com.mns.cda.locmnsback.controller;

import com.mns.cda.locmnsback.dao.StateDao;
import com.mns.cda.locmnsback.model.State;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class StateController {

    protected final StateDao stateDao;

    @GetMapping("/state/list")
    public List<State> getAll() {
        return stateDao.findAll();
    }

    @GetMapping("/state/{id}")
    public ResponseEntity<State> get(@PathVariable int id) {

        Optional<State> optionalState = stateDao.findById(id);

        if(optionalState.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(optionalState.get(), HttpStatus.OK);
    }

    @PostMapping("/state")
    public ResponseEntity<State> create(@RequestBody State stateToInsert) {

        stateToInsert.setId(null);

        stateDao.save(stateToInsert);

        return new ResponseEntity<>(stateToInsert, HttpStatus.CREATED);
    }

    @DeleteMapping("/state/{id}")
    public ResponseEntity<State> delete(@PathVariable int id) {
        Optional<State> optionalState = stateDao.findById(id);

        if(optionalState.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        stateDao.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/state/{id}")
    public ResponseEntity<State> update(@PathVariable int id, @RequestBody State stateToUpdate) {
        Optional<State> optionalState = stateDao.findById(id);

        if(optionalState.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        stateToUpdate.setId(id);

        stateDao.save(stateToUpdate);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

