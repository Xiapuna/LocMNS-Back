package com.mns.cda.locmnsback.dto;

public record AppUserDto(
        int id,
        String firstName,
        String name,
        String email,
        int roleId,
        String roleName,
        int accreditationId,
        String accreditationName
) {
}
