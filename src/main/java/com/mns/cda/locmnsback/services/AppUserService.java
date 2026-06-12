package com.mns.cda.locmnsback.services;

import com.mns.cda.locmnsback.dao.AppUserDao;
import com.mns.cda.locmnsback.dao.LoanDao;
import com.mns.cda.locmnsback.dto.UserReservationDto;
import com.mns.cda.locmnsback.model.AppUser;
import com.mns.cda.locmnsback.security.AppUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

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

    public AppUser getById(int id) {
        return appUserDao.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Utilisateur Introuvable"
                ));
    }

    public void delete(int id) {
        AppUser user = appUserDao.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Utilisateur introuvable"
                ));
        appUserDao.delete(user);
    }

    public void update(int id, AppUser appUserToUpdate) {
        AppUser existing = appUserDao.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Utilisateur introuvable"
                ));

        appUserToUpdate.setId(existing.getId());

        appUserDao.save(appUserToUpdate);
    }

}