package com.mns.cda.locmnsback.controller;

import com.mns.cda.locmnsback.dto.AuthLoginDto;
import com.mns.cda.locmnsback.dto.AuthResponseDto;
import com.mns.cda.locmnsback.dto.AuthSignInDto;
import com.mns.cda.locmnsback.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class AuthController {

    private final AuthService authService;

    @PostMapping("/sign-in")
    public ResponseEntity<Void> signIn(
            @RequestBody
            AuthSignInDto userToInsert) {

        authService.signIn(userToInsert);

        return new ResponseEntity<>(HttpStatus.CREATED);

    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody AuthLoginDto user) {

        try {
            return ResponseEntity.ok(authService.login(user));
        } catch (AuthenticationException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        }
    }
}
