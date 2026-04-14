package com.mns.cda.locmnsback.controller;

import com.mns.cda.locmnsback.dao.RequestDao;
import com.mns.cda.locmnsback.model.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class RequestController {

    @Autowired
    protected RequestDao requestDao;

    @GetMapping("/request/list")
    public List<Request> getAll() {
        return requestDao.findAll();
    }

    @GetMapping("/request/{id}")
    public ResponseEntity<Request> get(@PathVariable int id) {

        Optional<Request> optionalRequest = requestDao.findById(id);

        if(optionalRequest.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(optionalRequest.get(), HttpStatus.OK);

    }

    @PostMapping("/request")
    public ResponseEntity<Request> create(@RequestBody Request requestToInsert) {

        requestToInsert.setId(null);

        requestDao.save(requestToInsert);

        return new ResponseEntity<>(requestToInsert, HttpStatus.CREATED);
    }

    @DeleteMapping("/request/{id}")
    public ResponseEntity<Request> delete(@PathVariable int id) {

        Optional<Request> optionalRequest = requestDao.findById(id);

        if(optionalRequest.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        requestDao.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/request/{id}")
    public ResponseEntity<Void> update(@PathVariable int id, @RequestBody Request requestToUpdate) {

        Optional<Request> optionalRequest = requestDao.findById(id);

        if(optionalRequest.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        requestDao.save(requestToUpdate);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}