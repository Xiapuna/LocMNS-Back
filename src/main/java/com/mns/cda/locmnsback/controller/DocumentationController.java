package com.mns.cda.locmnsback.controller;

import com.mns.cda.locmnsback.dao.DocumentationDao;
import com.mns.cda.locmnsback.model.Documentation;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class DocumentationController {

    protected final DocumentationDao documentationDao;

    @GetMapping("/documentation/list")
    public List<Documentation> getAll() {
        return documentationDao.findAll();
    }

    @GetMapping("/documentation/{id}")
    public ResponseEntity<Documentation> get(@PathVariable int id) {

        Optional<Documentation> optionalDocumentation = documentationDao.findById(id);

        if(optionalDocumentation.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(optionalDocumentation.get(), HttpStatus.OK);
    }

    @PostMapping("/documentation")
    public ResponseEntity<Documentation> create(@RequestBody Documentation documentationToInsert) {

        documentationToInsert.setId(null);

        documentationDao.save(documentationToInsert);

        return new ResponseEntity<>(documentationToInsert, HttpStatus.CREATED);

    }

    @DeleteMapping("/documentation/{id}")
    public ResponseEntity<Documentation> delete(@PathVariable int id) {

        Optional<Documentation> optionalDocumentation = documentationDao.findById(id);

        if(optionalDocumentation.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        documentationDao.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/documentation/{id}")
    public ResponseEntity<Void> update(@PathVariable int id, @RequestBody Documentation documentationToUpdate) {

        Optional<Documentation> optionalDocumentation = documentationDao.findById(id);

        if(optionalDocumentation.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        documentationToUpdate.setId(id);

        documentationDao.save(documentationToUpdate);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
