package com.mns.cda.locmnsback.controller;

import com.mns.cda.locmnsback.dao.AppUserDao;
import com.mns.cda.locmnsback.model.AppUser;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class AppUserController {

    protected final AppUserDao appUserDao;

    @GetMapping("/appuser/list")
    public List<AppUser> getAll() {
        return appUserDao.findAll();
    }

    @GetMapping("/appuser/{id}")
    public ResponseEntity<AppUser> get(@PathVariable int id) {

        Optional<AppUser> optionalUser = appUserDao.findById(id);

        if(optionalUser.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(optionalUser.get(), HttpStatus.OK);
    }

    @PostMapping("/appuser")
    public ResponseEntity<AppUser> create(@RequestBody AppUser appUserToInsert) {

        appUserToInsert.setId(null);

        appUserDao.save(appUserToInsert);

        return new ResponseEntity<>(appUserToInsert, HttpStatus.CREATED);
    }

    @DeleteMapping("/appuser/{id}")
    public ResponseEntity<AppUser> delete(@PathVariable int id) {

        Optional<AppUser> optionalUser = appUserDao.findById(id);

        if(optionalUser.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        appUserDao.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/appuser/{id}")
    public ResponseEntity<Void> update(@PathVariable int id, @RequestBody AppUser appUserToUpdate) {

        Optional<AppUser> optionalUser = appUserDao.findById(id);

        if(optionalUser.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        appUserToUpdate.setId(id);

        appUserDao.save(appUserToUpdate);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
