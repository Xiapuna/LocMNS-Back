package com.mns.cda.locmnsback.controller;

import com.mns.cda.locmnsback.dao.AppUserDao;
import com.mns.cda.locmnsback.model.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class AppUserController {

    @Autowired
    protected AppUserDao appUserDao;

    @GetMapping("/user/list")
    public List<AppUser> getAll() {
        return appUserDao.findAll();
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<AppUser> get(@PathVariable int id) {

        Optional<AppUser> optionalUser = appUserDao.findById(id);

        if(optionalUser.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(optionalUser.get(), HttpStatus.OK);
    }

    @PostMapping("/user")
    public ResponseEntity<AppUser> create(@RequestBody AppUser appUserToInsert) {

        appUserToInsert.setId(null);

        appUserDao.save(appUserToInsert);

        return new ResponseEntity<>(appUserToInsert, HttpStatus.CREATED);
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<AppUser> delete(@PathVariable int id) {

        Optional<AppUser> optionalUser = appUserDao.findById(id);

        if(optionalUser.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        appUserDao.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<Void> update(@PathVariable int id, @RequestBody AppUser appUserToUpdate) {

        Optional<AppUser> optionalUser = appUserDao.findById(id);

        if(optionalUser.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        appUserDao.save(appUserToUpdate);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
