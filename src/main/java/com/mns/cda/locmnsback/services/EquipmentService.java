package com.mns.cda.locmnsback.services;

import com.mns.cda.locmnsback.dao.EquipmentDao;
import com.mns.cda.locmnsback.dao.LoanDao;
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

    public List<Equipment> getAll() {
        return equipmentDao.findAll();
    }

    public Equipment get(int id) {
        return equipmentDao.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Équipement introuvable"
                ));
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

    public Equipment create(Equipment equipmentToInsert) {
        equipmentToInsert.setId(null);
        return equipmentDao.save(equipmentToInsert);
    }

    public void delete(int id) {
        Equipment equipment = equipmentDao.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Équipement introuvable"
                ));

        equipmentDao.delete(equipment);
    }

    public void update (int id, Equipment equipmentToUpdate) {
        Equipment existing = equipmentDao.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Équipement introuvable"
                ));
        equipmentToUpdate.setId(existing.getId());
        equipmentDao.save(equipmentToUpdate);
    }
}