package com.mns.cda.locmnsback.controller;

import com.mns.cda.locmnsback.dao.LocationDao;
import com.mns.cda.locmnsback.model.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class LocationController {

    @Autowired
    protected LocationDao locationDao; // Injection de la dépendance (dépendance avec le @Repository)

    @GetMapping("/location/list")
    public List<Location> getAll() {

        return locationDao.findAll(); // Obtenir toute la liste des salles
    }

    @GetMapping("/location/{id}")
    public ResponseEntity<Location> get(@PathVariable int id) {

        Optional<Location> optionalLocation = locationDao.findById(id);

        if(optionalLocation.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(optionalLocation.get(), HttpStatus.OK);
    }

    @PostMapping("/location")
    public ResponseEntity<Location> create(@RequestBody Location locationToInsert) {

        locationToInsert.setId(null); // Lors de la création, on écrase l'id pour éviter les doublons

        locationDao.save(locationToInsert); // Sauvegarde du nouvel user inséré

        return new ResponseEntity<>(locationToInsert, HttpStatus.CREATED);
    }

    @DeleteMapping("/location/{id}")
    public ResponseEntity<Location> delete(@PathVariable int id) {

        Optional<Location> optionalLocation = locationDao.findById(id);

        if(optionalLocation.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        locationDao.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/location/{id}")
    public ResponseEntity<Void> update(@PathVariable int id, @RequestBody Location locationToUpdate) {
        Optional<Location> optionalLocation = locationDao.findById(id);

        if(optionalLocation.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        locationToUpdate.setId(id);

        locationDao.save(locationToUpdate);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
