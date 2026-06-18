package com.mns.cda.locmnsback.services;

import com.mns.cda.locmnsback.dao.AppUserDao;
import com.mns.cda.locmnsback.dao.RoleDao;
import com.mns.cda.locmnsback.dto.AuthLoginDto;
import com.mns.cda.locmnsback.dto.AuthResponseDto;
import com.mns.cda.locmnsback.dto.AuthSignInDto;
import com.mns.cda.locmnsback.model.AppUser;
import com.mns.cda.locmnsback.model.Role;
import com.mns.cda.locmnsback.security.AppUserDetails;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    @Value("${jwt.secret}")
    private String jwtSecret;

    private final AppUserDao appUserDao;
    private final PasswordEncoder encoder;
    private final RoleDao roleDao;
    private final AuthenticationProvider authenticationProvider;

    public AppUser signIn(AuthSignInDto dto) {

        Role userRole = roleDao.findByName("ROLE_USER")
                .orElseThrow(() -> new IllegalStateException("Default role ROLE_USER not found"));

        if (appUserDao.findByEmail(dto.email()).isPresent()) {
            throw new IllegalArgumentException("Email already used");
        }

        AppUser user = new AppUser();
        user.setFirstName(dto.firstName());
        user.setName(dto.name());
        user.setEmail(dto.email());
        user.setRole(userRole);
        user.setPassword(encoder.encode(dto.password()));

        return appUserDao.save(user);
    }

    public AuthResponseDto login(AuthLoginDto user) {

        AppUserDetails appUser = (AppUserDetails) authenticationProvider
                .authenticate(new UsernamePasswordAuthenticationToken(
                        user.email(),
                        user.password()))
                .getPrincipal();

        String jwt = Jwts.builder()
                .setSubject(user.email())
                .addClaims(Map.of(
                        "role", appUser.getUser().getRole().getName(),
                        "id", appUser.getUser().getId(),
                        "firstname", appUser.getUser().getFirstName(),
                        "name", appUser.getUser().getName()
                ))
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact();

        return new AuthResponseDto(jwt);
    }
}