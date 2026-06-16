package com.mns.cda.locmnsback.dto;

public record AppUserUpdateDto(
        String firstName,
        String name,
        String email,
        int roleId,
        int accreditationId
) {
}
