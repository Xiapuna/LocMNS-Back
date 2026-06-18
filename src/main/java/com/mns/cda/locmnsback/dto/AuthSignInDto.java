package com.mns.cda.locmnsback.dto;

public record AuthSignInDto(
        String firstName,
        String name,
        String email,
        String password
) {
}
