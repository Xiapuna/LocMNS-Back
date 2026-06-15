package com.mns.cda.locmnsback.dto;

public record EquipmentCreateDto(
        String name,
        int typeId,
        int modelId,
        int locationId
) {
}
