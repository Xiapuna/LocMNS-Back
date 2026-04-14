package com.mns.cda.locmnsback.controller;

import com.mns.cda.locmnsback.dao.UserDao;
import com.mns.cda.locmnsback.model.User;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    protected UserDao userDao;

    @GetMapping("/user/list")
    public List<User> getAll() {
        return userDao.findAll();
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<User> get(@PathVariable int id) {

        Optional<User> optionalUser = userDao.findById(id);

        if(optionalUser.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(optionalUser.get(), HttpStatus.OK);
    }

    @PostMapping("/user")
    public ResponseEntity<User> create(@RequestBody User userToInsert) {

        userToInsert.setId(null);

        userDao.save(userToInsert);

        return new ResponseEntity<>(userToInsert, HttpStatus.CREATED);
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<User> delete(@PathVariable int id) {

        Optional<User> optionalUser = userDao.findById(id);

        if(optionalUser.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        userDao.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<Void> update(@PathVariable int id, @RequestBody User userToUpdate) {

        Optional<User> optionalUser = userDao.findById(id);

        if(optionalUser.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        userDao.save(userToUpdate);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
