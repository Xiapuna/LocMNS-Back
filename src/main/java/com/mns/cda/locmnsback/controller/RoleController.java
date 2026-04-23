package com.mns.cda.locmnsback.controller;

import com.mns.cda.locmnsback.dao.RoleDao;
import com.mns.cda.locmnsback.model.Role;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class RoleController {

    protected final RoleDao roleDao;

    @GetMapping("/role/list")
    public List<Role> getAll() {
        return roleDao.findAll();
    }

    @GetMapping("/role/{id}")
    public ResponseEntity<Role> get(@PathVariable int id) {

        Optional<Role> optionalRole = roleDao.findById(id);

        if(optionalRole.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(optionalRole.get(), HttpStatus.OK);
    }

    @PostMapping("/role")
    public ResponseEntity<Role> create(@RequestBody Role roleToInsert) {

        roleToInsert.setId(null);

        roleDao.save(roleToInsert);

        return new ResponseEntity<>(roleToInsert, HttpStatus.CREATED);
    }

    @DeleteMapping("/role/{id}")
    public ResponseEntity<Role> delete(@PathVariable int id) {

        Optional<Role> roleOptional = roleDao.findById(id);

        if(roleOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        roleDao.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/role/{id}")
    public ResponseEntity<Void> update(@PathVariable int id, @RequestBody Role roleToUpdate) {

        Optional<Role> optionalRole = roleDao.findById(id);

        if(optionalRole.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        roleDao.save(roleToUpdate);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
