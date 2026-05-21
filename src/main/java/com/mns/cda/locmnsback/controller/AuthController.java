package com.mns.cda.locmnsback.controller;

import com.mns.cda.locmnsback.model.AppUser;
import com.mns.cda.locmnsback.security.AppUserDetails;
import com.mns.cda.locmnsback.services.AppUserService;
import com.mns.cda.locmnsback.view.AppUserView;
import com.fasterxml.jackson.annotation.JsonView;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class AuthController {

    private final AppUserService userService;
    private final AuthenticationProvider authenticationProvider;
//    private final PasswordEncoder passwordEncoder;

    @PostMapping("/sign-in")
//    @JsonView(AppUserView.class)
    public ResponseEntity<AppUser> signIn(
            @RequestBody
//            @Validated(AppUser.OnCreate.class)
            AppUser userToInsert) {

//        userToInsert.setPassword(passwordEncoder.encode(userToInsert.getPassword()));
        userService.insert(userToInsert);

        return new ResponseEntity<>(userToInsert, HttpStatus.CREATED);

    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AppUser user) {

        try {
            AppUserDetails appUser = (AppUserDetails) authenticationProvider
                    .authenticate(new UsernamePasswordAuthenticationToken(
                            user.getEmail(),
                            user.getPassword()))
                    .getPrincipal();
            String jwt = Jwts.builder()
                    .setSubject(user.getEmail())
                    .addClaims(Map.of("role", appUser.getUser().getRole().getName(),
                            "id", appUser.getUser().getId())) // Pour un ManyToMany .addClaims(Map.of("roles", user.getRoles().stream().map(RoleEnum r -> r.getName()).collect(Collectors.joining(",")))
                    .signWith(SignatureAlgorithm.HS256, "azerty")
                    .compact();

            return new ResponseEntity<>(jwt, HttpStatus.OK);
        } catch (AuthenticationException e){

            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
}
