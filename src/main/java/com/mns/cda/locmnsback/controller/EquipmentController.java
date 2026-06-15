package com.mns.cda.locmnsback.controller;

import com.mns.cda.locmnsback.dto.EquipmentCreateDto;
import com.mns.cda.locmnsback.dto.EquipmentDto;
import com.mns.cda.locmnsback.dto.EquipmentUpdateDto;
import com.mns.cda.locmnsback.dto.LoanCalendarDto;
import com.mns.cda.locmnsback.model.Equipment;
import com.mns.cda.locmnsback.security.IsAdmin;
import com.mns.cda.locmnsback.security.IsUser;
import com.mns.cda.locmnsback.services.EquipmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class EquipmentController {

    private final EquipmentService equipmentService;

    @GetMapping("/equipment/list")
    @IsUser
    public ResponseEntity<List<EquipmentDto>> getAll() {

        return ResponseEntity.ok(equipmentService.getAll());
    }

    @GetMapping("/equipment/{id}")
    @IsUser
    public ResponseEntity<EquipmentDto> get(@PathVariable int id) {

        return ResponseEntity.ok(equipmentService.get(id));
    }

    @GetMapping("/equipment/{id}/loans")
    @IsUser
    public ResponseEntity<List<LoanCalendarDto>> getLoansForEquipment (@PathVariable int id){
            return ResponseEntity.ok(equipmentService.getLoansForEquipment(id));
    }

    @PostMapping("/equipment")
    @IsAdmin
    public ResponseEntity<EquipmentDto> create(@RequestBody EquipmentCreateDto equipmentToInsert) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(equipmentService.create(equipmentToInsert));
    }

    @DeleteMapping("/equipment/{id}")
    @IsAdmin
    public ResponseEntity<Void> delete(@PathVariable int id) {
        equipmentService.delete(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/equipment/{id}")
    @IsAdmin
    public ResponseEntity<EquipmentDto> update(@PathVariable int id, @RequestBody EquipmentUpdateDto equipmentToUpdate) {

        return ResponseEntity.ok(equipmentService.update(id, equipmentToUpdate));
    }
}
