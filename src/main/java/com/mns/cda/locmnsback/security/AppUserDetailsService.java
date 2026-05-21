package com.mns.cda.locmnsback.security;

import com.mns.cda.locmnsback.dao.AppUserDao;
import com.mns.cda.locmnsback.model.AppUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AppUserDetailsService implements UserDetailsService {

    protected final AppUserDao appUserDao;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Optional<AppUser> optionalAppUser = appUserDao.findByEmail(email);

        if (optionalAppUser.isEmpty()) {
            throw new UsernameNotFoundException(email);
        }

        return new AppUserDetails(optionalAppUser.get());
    }

}
