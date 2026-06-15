package com.mns.cda.locmnsback.dto;

public record EquipmentDto(
        int id,
        String name,
        int typeId,
        String typeName,
        int modelId,
        String modelName,
        String modelDescription,
        int locationId,
        String locationName
) {
}
