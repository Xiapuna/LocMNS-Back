package com.mns.cda.locmnsback.controller;

import com.mns.cda.locmnsback.dao.ModelDao;
import com.mns.cda.locmnsback.dao.TypeDao;
import com.mns.cda.locmnsback.model.Model;
import jdk.javadoc.doclet.Reporter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class ModelController {

    protected final ModelDao modelDao;

    @GetMapping("/model/list")
    public List<Model> getAll() {

        return modelDao.findAll();
    }

    @GetMapping("/model/{id}")
    public ResponseEntity<Model> get(@PathVariable int id) {

        Optional<Model> optionalModel = modelDao.findById(id);

        if (optionalModel.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(optionalModel.get(), HttpStatus.OK);
    }

    @PostMapping("/model")
    public ResponseEntity<Model> create(@RequestBody Model modelToInsert) {

        modelToInsert.setId(null);

        modelDao.save(modelToInsert);

        return new ResponseEntity<>(modelToInsert, HttpStatus.CREATED);
    }

    @DeleteMapping("/model/{id}")
    public ResponseEntity<Model> delete(@PathVariable int id) {

        Optional<Model> optionalModel = modelDao.findById(id);

        if (optionalModel.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        modelDao.deleteById(id);

        return  new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/model/{id}")
    public ResponseEntity<Void> update(@PathVariable int id, @RequestBody Model modelToUpdate) {

        Optional<Model> optionalModel = modelDao.findById(id);

        if(optionalModel.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        modelToUpdate.setId(id);

        modelDao.save(modelToUpdate);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
