package com.mns.cda.locmnsback.mock;

import com.mns.cda.locmnsback.dto.EquipmentCreateDto;
import com.mns.cda.locmnsback.dto.EquipmentDto;
import com.mns.cda.locmnsback.dto.EquipmentUpdateDto;
import com.mns.cda.locmnsback.dto.LoanCalendarDto;
import com.mns.cda.locmnsback.services.IEquipmentService;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

public class MockEquipmentService implements IEquipmentService {

    @Override
    public List<EquipmentDto> getAll() {
        return List.of();
    }

    @Override
    public EquipmentDto get(int id) {
        if (id == 1) {
            // Faux EquipmentDto renvoyé si l'ID est 1
            return new EquipmentDto(
                    1,
                    "Projecteur Epson",
                    1,
                    "Vidéo",
                    1,
                    "Epson EB-2250U",
                    "Projecteur Full HD",
                    1,
                    "Salle 101"
            );
        }

        // Simule le comportement de ton EquipmentService si l'ID n'existe pas (ex: test 404)
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Équipement introuvable"
        );
    }

    @Override
    public List<LoanCalendarDto> getLoansForEquipment(int id) {
        return List.of();
    }

    @Override
    public EquipmentDto create(EquipmentCreateDto dto) {
        return new EquipmentDto(
                1,
                dto.name(),
                1,
                "Type",
                dto.modelId(),
                "Model",
                "Description",
                dto.locationId(),
                "Localisation"
        );
    }

    @Override
    public void delete(int id) {
        if (id != 1) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Équipement introuvable"
            );
        }
    }

    @Override
    public EquipmentDto update(int id, EquipmentUpdateDto dto) {
        if (id != 1) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Équipement introuvable"
            );
        }
        return new EquipmentDto(
                id,
                dto.name(),
                1,
                "Type",
                dto.modelId(),
                "Model",
                "Description",
                dto.locationId(),
                "Localisation"
        );
    }
}