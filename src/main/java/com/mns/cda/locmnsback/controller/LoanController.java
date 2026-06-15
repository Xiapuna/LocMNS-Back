package com.mns.cda.locmnsback.controller;

import com.mns.cda.locmnsback.dao.LoanDao;
import com.mns.cda.locmnsback.dto.*;
import com.mns.cda.locmnsback.enums.LoanStatus;
import com.mns.cda.locmnsback.model.AppUser;
import com.mns.cda.locmnsback.model.Equipment;
import com.mns.cda.locmnsback.model.Loan;
import com.mns.cda.locmnsback.security.AppUserDetails;
import com.mns.cda.locmnsback.security.IsAdmin;
import com.mns.cda.locmnsback.security.IsUser;
import com.mns.cda.locmnsback.services.LoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class LoanController {

    protected final LoanDao loanDao;
    protected final LoanService loanService;

    @GetMapping("/loan/list")
    @IsUser
    public ResponseEntity<List<LoanDto>> getAll() {
        return ResponseEntity.ok(loanService.getAll());
    }

    @GetMapping("/loan/{id}")
    @IsUser
    public ResponseEntity<LoanDto> get(@PathVariable int id) {

        return ResponseEntity.ok(loanService.get(id));
    }

    @GetMapping("/loans")
    @IsAdmin
    public ResponseEntity<List<LoanDto>> getByStatus(@RequestParam(required = false) LoanStatus status) {
        return ResponseEntity.ok(loanService.getByStatus(status));
    }

    @GetMapping("/loans/{id}/history")
    @IsAdmin
    public ResponseEntity<List<LoanHistoryDto>> getLoanHistory(@PathVariable int id) {
        return ResponseEntity.ok(loanService.getLoanHistory(id));
    }

    @PostMapping("/loan")
    @IsUser
    public ResponseEntity<LoanDto> create(@RequestBody LoanCreateDto loanCreateDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(loanService.create(loanCreateDto));
    }

    @PostMapping("/loan/{id}/request-return")
    @IsUser
    public ResponseEntity<Void> requestReturn(@PathVariable int id, @AuthenticationPrincipal AppUserDetails userDetails) {
        loanService.requestReturn(id, userDetails.getUser());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/loan/{id}/request-extension")
    @IsUser
    public ResponseEntity<Void> requestExtension(@PathVariable int id, @AuthenticationPrincipal AppUserDetails userDetails) {

        loanService.requestExtension(id, userDetails.getUser());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/loan/{id}")
    @IsAdmin
    public ResponseEntity<Loan> delete(@PathVariable int id) {

        loanService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/loan/{id}")
    @IsAdmin
    public ResponseEntity<LoanDto> update(@PathVariable int id, @RequestBody LoanUpdateDto loanToUpdate) {
        return ResponseEntity.ok(loanService.update(id, loanToUpdate));
    }

    @PutMapping("/loans/{id}/start")
    @IsUser
    public ResponseEntity<LoanDto> startLoan(@PathVariable int id) {

        return ResponseEntity.ok(loanService.startLoan(id));
    }

    @PutMapping("/loans/{id}/extend")
    @IsAdmin
    public ResponseEntity<LoanDto> extendLoan(@PathVariable int id, @RequestBody LoanExtensionDto dto) {
        return ResponseEntity.ok(loanService.extendLoan(id, dto.newEndDate()));
    }

    @PutMapping("/loans/{id}/return")
    @IsAdmin
    public ResponseEntity<LoanDto> validateReturn(@PathVariable int id)
    {
        return ResponseEntity.ok(loanService.validateReturn(id));
    }
}
