package com.mns.cda.locmnsback.controller;

import com.mns.cda.locmnsback.dao.AppUserDao;
import com.mns.cda.locmnsback.dto.UserReservationDto;
import com.mns.cda.locmnsback.model.AppUser;
import com.mns.cda.locmnsback.security.AppUserDetails;
import com.mns.cda.locmnsback.security.IsAdmin;
import com.mns.cda.locmnsback.security.IsUser;
import com.mns.cda.locmnsback.services.AppUserService;
import com.mns.cda.locmnsback.services.LoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class AppUserController {

    protected final AppUserDao appUserDao;
    protected final LoanService loanService;

    protected final AppUserService userService;


    @GetMapping("/appuser/list")
    @IsAdmin
    public List<AppUser> getAll() {
        return appUserDao.findAll();
    }

    @GetMapping("/appuser/{id}")
    @IsUser
    public ResponseEntity<AppUser> get(@PathVariable int id) {
        return ResponseEntity.ok(userService.getById(id));
    }

    @GetMapping("/appuser/{id}/loans")
    @IsUser
    public ResponseEntity<List<UserReservationDto>> getUserLoans (@PathVariable int id, @AuthenticationPrincipal AppUserDetails userDetails){
        return ResponseEntity.ok(
                loanService.getUserLoans(id, userDetails.getUser())
        );
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

        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/appuser/{id}")
    @IsUser
    public ResponseEntity<Void> update(@PathVariable int id, @RequestBody AppUser appUserToUpdate) {

        userService.update(id, appUserToUpdate);
        return ResponseEntity.noContent().build();
    }
}
