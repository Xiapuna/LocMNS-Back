package com.mns.cda.locmnsback.controller;

import com.mns.cda.locmnsback.dao.LoanStateDao;
import com.mns.cda.locmnsback.dto.LoanStateCreateDto;
import com.mns.cda.locmnsback.dto.LoanStateDto;
import com.mns.cda.locmnsback.dto.LoanStateUpdateDto;
import com.mns.cda.locmnsback.model.LoanState;
import com.mns.cda.locmnsback.services.LoanStateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class LoanStateController {

    protected final LoanStateService loanStateService;

    @GetMapping("/state/list")
    public ResponseEntity<List<LoanStateDto>> getAll() {
        return ResponseEntity.ok(loanStateService.getAll());
    }
    @GetMapping("/state/{id}")
    public ResponseEntity<LoanStateDto> get(@PathVariable int id) {
        return ResponseEntity.ok(loanStateService.get(id));
    }

    @PostMapping("/state")
    public ResponseEntity<LoanStateDto> create(@RequestBody LoanStateCreateDto loanStateToInsert) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(loanStateService.create(loanStateToInsert));
    }

    @DeleteMapping("/state/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        loanStateService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/state/{id}")
    public ResponseEntity<LoanStateDto> update(@PathVariable int id, @RequestBody LoanStateUpdateDto loanStateToUpdate) {
        return ResponseEntity.ok(loanStateService.update(id, loanStateToUpdate));
    }
}

