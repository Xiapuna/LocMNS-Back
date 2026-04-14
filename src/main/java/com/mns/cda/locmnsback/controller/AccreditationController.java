package com.mns.cda.locmnsback.controller;

import com.mns.cda.locmnsback.dao.AccreditationDao;
import com.mns.cda.locmnsback.model.Accreditation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class AccreditationController {

    @Autowired
    protected AccreditationDao accreditationDao;

    @GetMapping("/accreditation/list")
    public List<Accreditation> getAll() {
        return accreditationDao.findAll();
    }

    @GetMapping("/accreditation/{id}")
    public ResponseEntity<Accreditation> get(@PathVariable int id) {

        Optional<Accreditation> optionalAccreditation = accreditationDao.findById(id);

        if(optionalAccreditation.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(optionalAccreditation.get(), HttpStatus.OK);
    }

    @PostMapping("/accreditation")
    public ResponseEntity<Accreditation> create(@RequestBody Accreditation accreditationToInsert) {

        accreditationToInsert.setId(null);

        accreditationDao.save(accreditationToInsert);

        return new ResponseEntity<>(accreditationToInsert, HttpStatus.CREATED);
    }

    @DeleteMapping("/accreditation/{id}")
    public ResponseEntity<Accreditation> delete(@PathVariable int id) {

        Optional<Accreditation> optionalAccreditation = accreditationDao.findById(id);

        if(optionalAccreditation.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        accreditationDao.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/accreditation/{id}")
    public ResponseEntity<Accreditation> update(@PathVariable int id, @RequestBody Accreditation accreditationToUpdate) {

        Optional<Accreditation> optionalAccreditation = accreditationDao.findById(id);

        if(optionalAccreditation.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        accreditationDao.save(accreditationToUpdate);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

