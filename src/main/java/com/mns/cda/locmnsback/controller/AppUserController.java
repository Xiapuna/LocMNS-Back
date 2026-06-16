package com.mns.cda.locmnsback.controller;

import com.mns.cda.locmnsback.dao.AppUserDao;
import com.mns.cda.locmnsback.dto.AppUserCreateDto;
import com.mns.cda.locmnsback.dto.AppUserDto;
import com.mns.cda.locmnsback.dto.AppUserUpdateDto;
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
    public ResponseEntity<List<AppUserDto>> getAll() {

        return ResponseEntity.ok(userService.getAll());
    }

    @GetMapping("/appuser/{id}")
    @IsUser
    public ResponseEntity<AppUserDto> get(@PathVariable int id) {

        return ResponseEntity.ok(userService.get(id));
    }

    @GetMapping("/appuser/{id}/loans")
    @IsUser
    public ResponseEntity<List<UserReservationDto>> getUserLoans (@PathVariable int id, @AuthenticationPrincipal AppUserDetails userDetails){
        return ResponseEntity.ok(
                loanService.getUserLoans(id, userDetails.getUser())
        );
    }

    @PostMapping("/appuser")
    @IsAdmin
    public ResponseEntity<AppUserDto> create(@RequestBody AppUserCreateDto userToInsert) {

        AppUserDto created = userService.create(userToInsert);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @DeleteMapping("/appuser/{id}")
    @IsAdmin
    public ResponseEntity<AppUserDto> delete(@PathVariable int id) {

        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/appuser/{id}")
    @IsAdmin
    public ResponseEntity<AppUserDto> update(@PathVariable int id, @RequestBody AppUserUpdateDto userToUpdate) {

        AppUserDto update = userService.update(id, userToUpdate);
        return ResponseEntity.ok(update);
    }
}
