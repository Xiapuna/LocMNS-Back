package com.mns.cda.locmnsback.services;

import com.mns.cda.locmnsback.dao.AppUserDao;
import com.mns.cda.locmnsback.model.AppUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AppUserService {

    private final PasswordEncoder encoder;

    protected final AppUserDao appUserDao;

    public void insert(AppUser user) {
        user.setId(null);
        user.setPassword(encoder.encode(user.getPassword())); // Encodage du mdp de l'utilisateur
        appUserDao.save(user);
    }
}