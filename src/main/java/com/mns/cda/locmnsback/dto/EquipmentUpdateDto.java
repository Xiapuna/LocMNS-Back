package com.mns.cda.locmnsback.dto;

import java.time.LocalDate;

public record EquipmentUpdateDto(
        String name,
        int typeId,
        int modelId,
        int locationId
) {
}
