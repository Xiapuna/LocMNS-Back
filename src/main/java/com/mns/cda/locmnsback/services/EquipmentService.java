package com.mns.cda.locmnsback.services;

import com.mns.cda.locmnsback.dao.*;
import com.mns.cda.locmnsback.dto.EquipmentCreateDto;
import com.mns.cda.locmnsback.dto.EquipmentDto;
import com.mns.cda.locmnsback.dto.EquipmentUpdateDto;
import com.mns.cda.locmnsback.dto.LoanCalendarDto;
import com.mns.cda.locmnsback.model.Equipment;
import com.mns.cda.locmnsback.model.Location;
import com.mns.cda.locmnsback.model.Model;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EquipmentService implements IEquipmentService {

    private final EquipmentDao equipmentDao;
    private final LoanDao loanDao;
    private final ModelDao modelDao;
    private final LocationDao locationDao;

    @Override
    public List<EquipmentDto> getAll() {
        return equipmentDao.findAll()
                .stream()
                .map(e -> new EquipmentDto(
                        e.getId(),
                        e.getName(),
                        e.getModel().getType().getId(),
                        e.getModel().getType().getName(),
                        e.getModel().getId(),
                        e.getModel().getName(),
                        e.getModel().getDescription(),
                        e.getLocation().getId(),
                        e.getLocation().getName()
                ))
                .toList();
    }

    @Override
    public EquipmentDto get(int id) {
        Equipment e = equipmentDao.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Équipement introuvable"
                ));

        return new EquipmentDto(
                        e.getId(),
                        e.getName(),
                        e.getModel().getType().getId(),
                        e.getModel().getType().getName(),
                        e.getModel().getId(),
                        e.getModel().getName(),
                        e.getModel().getDescription(),
                        e.getLocation().getId(),
                        e.getLocation().getName()
        );
    }

    @Override
    public List<LoanCalendarDto> getLoansForEquipment(int id) {

        Equipment equipment = equipmentDao.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Équipement introuvable"
                ));

        return loanDao.findByEquipmentId(id)
                .stream()
                .map(l -> new LoanCalendarDto(
                        l.getStartDate(),
                        l.getEndDate()
                ))
                .toList();
    }

    @Override
    public EquipmentDto create(EquipmentCreateDto dto) {

        Model model = modelDao.findById(dto.modelId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Model introuvable"
                ));

        Location location = locationDao.findById(dto.locationId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Localisation introuvable"
                ));

        Equipment equipment = new Equipment();
        equipment.setId(null);
        equipment.setName(dto.name());
        equipment.setModel(model);
        equipment.setLocation(location);

        Equipment e = equipmentDao.save(equipment);

        return new EquipmentDto(
                e.getId(),
                e.getName(),
                e.getModel().getType().getId(),
                e.getModel().getType().getName(),
                e.getModel().getId(),
                e.getModel().getName(),
                e.getModel().getDescription(),
                e.getLocation().getId(),
                e.getLocation().getName()
        );
    }

    @Override
    public void delete(int id) {
        Equipment equipment = equipmentDao.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Équipement introuvable"
                ));

        equipmentDao.delete(equipment);
    }

    @Override
    public EquipmentDto update (int id, EquipmentUpdateDto dto) {
        Equipment existing = equipmentDao.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Équipement introuvable"
                ));

        Model model = modelDao.findById(dto.modelId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Model introuvable"
                ));

        Location location = locationDao.findById(dto.locationId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Localisation introuvable"
                ));

        existing.setName(dto.name());
        existing.setModel(model);
        existing.setLocation(location);

        Equipment e = equipmentDao.save(existing);

        return new EquipmentDto(
                e.getId(),
                e.getName(),
                e.getModel().getType().getId(),
                e.getModel().getType().getName(),
                e.getModel().getId(),
                e.getModel().getName(),
                e.getModel().getDescription(),
                e.getLocation().getId(),
                e.getLocation().getName()
        );
    }
}