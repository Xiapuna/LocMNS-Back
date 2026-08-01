package com.mns.cda.locmnsback.services;

import com.mns.cda.locmnsback.dto.EquipmentCreateDto;
import com.mns.cda.locmnsback.dto.EquipmentDto;
import com.mns.cda.locmnsback.dto.EquipmentUpdateDto;
import com.mns.cda.locmnsback.dto.LoanCalendarDto;

import java.util.List;

public interface IEquipmentService {

    List<EquipmentDto> getAll();

    EquipmentDto get(int id);

    List<LoanCalendarDto> getLoansForEquipment(int id);

    EquipmentDto create(EquipmentCreateDto dto);

    void delete(int id);

    EquipmentDto update(int id, EquipmentUpdateDto dto);
}