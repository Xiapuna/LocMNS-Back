package com.mns.cda.locmnsback.services;

import com.mns.cda.locmnsback.dao.EquipmentDao;
import com.mns.cda.locmnsback.dao.LoanDao;
import com.mns.cda.locmnsback.dto.EquipmentDto;
import com.mns.cda.locmnsback.dto.LoanCalendarDto;
import com.mns.cda.locmnsback.model.Equipment;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EquipmentService {

    private final EquipmentDao equipmentDao;
    private final LoanDao loanDao;

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
                        e.getLocation().getId(),
                        e.getLocation().getName(),
                        e.getModel().getDescription()
                ))
                .toList();
    }

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
                        e.getLocation().getId(),
                        e.getLocation().getName(),
                        e.getModel().getDescription()
        );
    }

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

    public EquipmentDto create(Equipment equipmentToInsert) {
        equipmentToInsert.setId(null);
        Equipment e = equipmentDao.save(equipmentToInsert);

        return new EquipmentDto(
                e.getId(),
                e.getName(),
                e.getModel().getType().getId(),
                e.getModel().getType().getName(),
                e.getModel().getId(),
                e.getModel().getName(),
                e.getLocation().getId(),
                e.getLocation().getName(),
                e.getModel().getDescription()
        );
    }

    public void delete(int id) {
        Equipment equipment = equipmentDao.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Équipement introuvable"
                ));

        equipmentDao.delete(equipment);
    }

    public EquipmentDto update (int id, Equipment equipmentToUpdate) {
        Equipment existing = equipmentDao.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Équipement introuvable"
                ));
        equipmentToUpdate.setId(existing.getId());
        Equipment e = equipmentDao.save(equipmentToUpdate);

        return new EquipmentDto(
                e.getId(),
                e.getName(),
                e.getModel().getType().getId(),
                e.getModel().getType().getName(),
                e.getModel().getId(),
                e.getModel().getName(),
                e.getLocation().getId(),
                e.getLocation().getName(),
                e.getModel().getDescription()
        );
    }
}