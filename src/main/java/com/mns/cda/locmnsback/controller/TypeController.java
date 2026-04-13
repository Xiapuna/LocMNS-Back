package com.mns.cda.locmnsback.controller;

import com.mns.cda.locmnsback.dao.TypeDao;
import com.mns.cda.locmnsback.model.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@RestController
public class TypeController {

    @Autowired
    protected TypeDao typeDao;

    @GetMapping("/type/list")
    public List<Type> getAll() {
        return typeDao.findAll();
    }

    @GetMapping("/type/{id}")
    public ResponseEntity<Type> get(@PathVariable int id) {

        Optional<Type> optionalType = typeDao.findById(id);

        if (optionalType.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(optionalType.get(), HttpStatus.OK);
    }

    @PostMapping("/type")
    public ResponseEntity<Type> create(@RequestBody Type typeToInsert) {

        typeToInsert.setId(null);

        typeDao.save(typeToInsert);

        return new ResponseEntity<>(typeToInsert, HttpStatus.CREATED);
    }

    @DeleteMapping("/type/{id}")
    public ResponseEntity<Type> delete(@PathVariable int id) {

        Optional<Type> optionalType = typeDao.findById(id);

        if(optionalType.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        typeDao.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/type/{id}")
    public ResponseEntity<Void> update(@PathVariable int id, @RequestBody Type typeToUpdate) {

        Optional<Type> optionalType = typeDao.findById(id);

        if(optionalType.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        typeDao.save(typeToUpdate);

        return  new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
