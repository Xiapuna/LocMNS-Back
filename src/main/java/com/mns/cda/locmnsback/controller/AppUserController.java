package com.mns.cda.locmnsback.controller;

import com.mns.cda.locmnsback.dao.AppUserDao;
import com.mns.cda.locmnsback.dao.LoanDao;
import com.mns.cda.locmnsback.model.AppUser;
import com.mns.cda.locmnsback.model.Loan;
import com.mns.cda.locmnsback.security.IsAdmin;
import com.mns.cda.locmnsback.security.IsUser;
import com.mns.cda.locmnsback.services.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class AppUserController {

    protected final AppUserDao appUserDao;
    protected final LoanDao loanDao;

    protected final AppUserService userService;


    @GetMapping("/appuser/list")
    @IsAdmin
    public List<AppUser> getAll() {
        return appUserDao.findAll();
    }

    @GetMapping("/appuser/{id}")
    @IsUser
    public ResponseEntity<AppUser> get(@PathVariable int id) {

        Optional<AppUser> optionalUser = appUserDao.findById(id);

        if(optionalUser.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(optionalUser.get(), HttpStatus.OK);
    }

    @GetMapping("/appuser/{id}/loans")
    @IsUser
    public List<Loan> getUserLoans (@PathVariable int id){
        return loanDao.findByAppUserId(id);
    }

    @PostMapping("/appuser")
    @IsUser
    public ResponseEntity<AppUser> create(@RequestBody AppUser userToInsert) {

        userService.insert(userToInsert);

        return new ResponseEntity<>(userToInsert, HttpStatus.CREATED);
    }

    @DeleteMapping("/appuser/{id}")
    @IsAdmin
    public ResponseEntity<AppUser> delete(@PathVariable int id) {

        Optional<AppUser> optionalUser = appUserDao.findById(id);

        if(optionalUser.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        appUserDao.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/appuser/{id}")
    @IsUser
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
