package com.mns.cda.locmnsback.services;

import com.mns.cda.locmnsback.dao.TypeDao;
import com.mns.cda.locmnsback.dto.TypeCreateDto;
import com.mns.cda.locmnsback.dto.TypeDto;
import com.mns.cda.locmnsback.dto.TypeUpdateDto;
import com.mns.cda.locmnsback.model.Type;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TypeService {

    private final TypeDao typeDao;

    public List<TypeDto> getAll() {
        return typeDao.findAll()
                .stream()
                .map(type -> new TypeDto(type.getId(), type.getName()))
                .toList();
    }

    public TypeDto get(int id) {
        Type type = typeDao.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Type introuvable"
                ));

        return new TypeDto(type.getId(), type.getName());
    }

    public TypeDto create(TypeCreateDto dto) {
        Type type = new Type();
        type.setName(dto.name());

        Type saved = typeDao.save(type);

        return new TypeDto(saved.getId(), saved.getName());
    }

    public void delete(int id) {
        if (!typeDao.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Type introuvable"
            );
        }

        typeDao.deleteById(id);
    }

    public TypeDto update(int id, TypeUpdateDto dto) {
        Type type = typeDao.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Type introuvable"
                ));

        type.setName(dto.name());

        Type saved = typeDao.save(type);

        return new TypeDto(saved.getId(), saved.getName());
    }
}
