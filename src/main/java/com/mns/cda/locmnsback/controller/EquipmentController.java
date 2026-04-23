package com.mns.cda.locmnsback.controller;

import com.mns.cda.locmnsback.dao.EquipmentDao;
import com.mns.cda.locmnsback.model.Equipment;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.config.RepositoryNameSpaceHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class EquipmentController {

    protected final EquipmentDao equipmentDao;

    @GetMapping("/equipment/list")
    public List<Equipment> getAll() {
        return equipmentDao.findAll();
    }

    @GetMapping("/equipment/{id}")
    public ResponseEntity<Equipment> get(@PathVariable int id) {

        Optional<Equipment> optionalEquipment = equipmentDao.findById(id);

        if (optionalEquipment.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(optionalEquipment.get(), HttpStatus.OK);

    }

    @PostMapping("/equipment")
    public ResponseEntity<Equipment> create(@RequestBody Equipment equipmentToInsert) {

        equipmentToInsert.setId(null);

        equipmentDao.save(equipmentToInsert);

        return new ResponseEntity<>(equipmentToInsert, HttpStatus.CREATED);
    }

    @DeleteMapping("/equipment/{id}")
    public ResponseEntity<Equipment> delete(@PathVariable int id) {

        Optional<Equipment> optionalEquipment = equipmentDao.findById(id);

        if(optionalEquipment.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        equipmentDao.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/equipment/{id}")
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
