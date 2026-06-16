package com.mns.cda.locmnsback.services;

import com.mns.cda.locmnsback.controller.AccreditationController;
import com.mns.cda.locmnsback.dao.AccreditationDao;
import com.mns.cda.locmnsback.dao.AppUserDao;
import com.mns.cda.locmnsback.dao.LoanDao;
import com.mns.cda.locmnsback.dao.RoleDao;
import com.mns.cda.locmnsback.dto.AppUserCreateDto;
import com.mns.cda.locmnsback.dto.AppUserDto;
import com.mns.cda.locmnsback.dto.AppUserUpdateDto;
import com.mns.cda.locmnsback.dto.UserReservationDto;
import com.mns.cda.locmnsback.model.Accreditation;
import com.mns.cda.locmnsback.model.AppUser;
import com.mns.cda.locmnsback.model.Role;
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

    private final AppUserDao appUserDao;
    private final RoleDao roleDao;
    private final AccreditationDao accreditationDao;

    public List<AppUserDto> getAll() {
        return appUserDao.findAll()
                .stream()
                .map(u -> new AppUserDto(
                        u.getId(),
                        u.getFirstName(),
                        u.getName(),
                        u.getEmail(),
                        u.getRole().getId(),
                        u.getRole().getName(),
                        u.getAccreditation().getId(),
                        u.getAccreditation().getName()
                ))
                .toList();
    }

    public AppUserDto get(int id) {
        AppUser u = appUserDao.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Utilisateur introuvable"
                ));

        return new AppUserDto(
                u.getId(),
                u.getFirstName(),
                u.getName(),
                u.getEmail(),
                u.getRole().getId(),
                u.getRole().getName(),
                u.getAccreditation().getId(),
                u.getAccreditation().getName()
        );
    }

    public AppUserDto create (AppUserCreateDto dto) {

        Role role = roleDao.findById(dto.roleId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Rôle introuvable"
                ));

        Accreditation accreditation = accreditationDao.findById(dto.accreditationId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Accréditation introuvable"
                ));

        AppUser user = new AppUser();
        user.setId(null);
        user.setFirstName(dto.firstName());
        user.setName(dto.name());
        user.setEmail(dto.email());
        user.setPassword(encoder.encode(dto.password()));
        user.setRole(role);
        user.setAccreditation(accreditation);

        AppUser saved = appUserDao.save(user);

        return new AppUserDto(
                saved.getId(),
                saved.getFirstName(),
                saved.getName(),
                saved.getEmail(),
                saved.getRole().getId(),
                saved.getRole().getName(),
                saved.getAccreditation().getId(),
                saved.getAccreditation().getName()
        );
    }
    public void delete(int id) {
        AppUser user = appUserDao.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Utilisateur introuvable"
                ));
        appUserDao.delete(user);
    }

    public AppUserDto update(int id, AppUserUpdateDto dto) {
        AppUser existing = appUserDao.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Utilisateur introuvable"
                ));

        Role role = roleDao.findById(dto.roleId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Rôle introuvable"
                ));

        Accreditation accreditation = accreditationDao.findById(dto.accreditationId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Accréditation introuvable"
                ));


        existing.setFirstName(dto.firstName());
        existing.setName(dto.name());
        existing.setEmail(dto.email());
        existing.setRole(role);
        existing.setAccreditation(accreditation);

        AppUser saved = appUserDao.save(existing);

        return new AppUserDto(
                saved.getId(),
                saved.getFirstName(),
                saved.getName(),
                saved.getEmail(),
                saved.getRole().getId(),
                saved.getRole().getName(),
                saved.getAccreditation().getId(),
                saved.getAccreditation().getName()
        );
    }

//    public void insert(AppUser user) {
//        user.setId(null);
//        user.setPassword(encoder.encode(user.getPassword())); // Encodage du mdp de l'utilisateur
//        appUserDao.save(user);
//    }

}