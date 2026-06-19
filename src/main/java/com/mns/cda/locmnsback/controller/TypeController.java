package com.mns.cda.locmnsback.controller;

import com.mns.cda.locmnsback.dao.TypeDao;
import com.mns.cda.locmnsback.dto.TypeCreateDto;
import com.mns.cda.locmnsback.dto.TypeDto;
import com.mns.cda.locmnsback.dto.TypeUpdateDto;
import com.mns.cda.locmnsback.services.TypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class TypeController {

    protected final TypeService typeService;

    @GetMapping("/type/list")
    public ResponseEntity<List<TypeDto>> getAll() {

        return ResponseEntity.ok(typeService.getAll());
    }

    @GetMapping("/type/{id}")
    public ResponseEntity<TypeDto> get(@PathVariable int id) {

        return ResponseEntity.ok(typeService.get(id));
    }

    @PostMapping("/type")
    public ResponseEntity<TypeDto> create(@RequestBody TypeCreateDto typeToInsert) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(typeService.create(typeToInsert));
    }

    @DeleteMapping("/type/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {

        typeService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/type/{id}")
    public ResponseEntity<TypeDto> update(@PathVariable int id, @RequestBody TypeUpdateDto typeToUpdate) {

        return ResponseEntity.ok(typeService.update(id, typeToUpdate));
    }
}
