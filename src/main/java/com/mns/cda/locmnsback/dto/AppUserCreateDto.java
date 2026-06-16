package com.mns.cda.locmnsback.dto;

public record AppUserCreateDto(
        String firstName,
        String name,
        String email,
        String password,
        int roleId,
        int accreditationId
) {
}
