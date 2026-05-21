package com.mns.cda.locmnsback.controller;

import com.mns.cda.locmnsback.dao.EquipmentDao;
import com.mns.cda.locmnsback.dao.LoanDao;
import com.mns.cda.locmnsback.dto.LoanCalendarDto;
import com.mns.cda.locmnsback.model.Equipment;
import com.mns.cda.locmnsback.model.Loan;
import com.mns.cda.locmnsback.security.IsAdmin;
import com.mns.cda.locmnsback.security.IsUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.stream.Collectors;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class EquipmentController {

    protected final EquipmentDao equipmentDao;
    protected final LoanDao loanDao;

    @GetMapping("/equipment/list")
    @IsUser
    public List<Equipment> getAll() {
        return equipmentDao.findAll();
    }

    @GetMapping("/equipment/{id}")
    @IsUser
    public ResponseEntity<Equipment> get(@PathVariable int id) {

        Optional<Equipment> optionalEquipment = equipmentDao.findById(id);

        if (optionalEquipment.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(optionalEquipment.get(), HttpStatus.OK);

    }

    @GetMapping("/equipment/{id}/loans")
    @IsUser
    public List<LoanCalendarDto> getLoansForEquipment (@PathVariable int id){
            return loanDao.findByEquipmentId(id)
                    .stream()
                    .map(l -> new LoanCalendarDto(l.getStartDate(), l.getEndDate()))
                    .toList();
    }

    @PostMapping("/equipment")
    @IsAdmin
    public ResponseEntity<Equipment> create(@RequestBody Equipment equipmentToInsert) {

        equipmentToInsert.setId(null);

        equipmentDao.save(equipmentToInsert);

        return new ResponseEntity<>(equipmentToInsert, HttpStatus.CREATED);
    }

    @DeleteMapping("/equipment/{id}")
    @IsAdmin
    public ResponseEntity<Equipment> delete(@PathVariable int id) {

        Optional<Equipment> optionalEquipment = equipmentDao.findById(id);

        if(optionalEquipment.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        equipmentDao.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/equipment/{id}")
    @IsAdmin
    public ResponseEntity<Void> update(@PathVariable int id, @RequestBody Equipment equipmentToUpdate) {

        Optional<Equipment> optionalEquipment = equipmentDao.findById(id);

        if(optionalEquipment.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        equipmentToUpdate.setId(id);

        equipmentDao.save(equipmentToUpdate);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
