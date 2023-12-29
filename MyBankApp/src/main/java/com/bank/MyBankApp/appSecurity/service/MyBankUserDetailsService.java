package com.bank.MyBankApp.appSecurity.service;

import com.bank.MyBankApp.appSecurity.SecuredUser;
import com.bank.MyBankApp.appUser.model.AppUser;
import com.bank.MyBankApp.appUser.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyBankUserDetailsService implements UserDetailsService {
    private final AppUserRepository appUserRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        final AppUser appUser = appUserRepository.findByEmail(email).orElseThrow(
                ()-> new UsernameNotFoundException("App user with this email not found."));
        return new SecuredUser(appUser);
    }
}
