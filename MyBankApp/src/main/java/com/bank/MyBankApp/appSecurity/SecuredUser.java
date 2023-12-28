package com.bank.MyBankApp.appSecurity;

import com.bank.MyBankApp.appUser.model.AppUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@AllArgsConstructor
@Getter
public class SecuredUser implements UserDetails {
    private final AppUser appUser;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return appUser.getRole().getAuthorities();
    }

    @Override
    public String getPassword() {
        return appUser.getPassword();
    }

    @Override
    public String getUsername() {
        return appUser.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return appUser.isEnabled();
    }
}
