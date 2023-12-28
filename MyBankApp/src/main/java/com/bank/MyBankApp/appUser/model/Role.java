package com.bank.MyBankApp.appUser.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.bank.MyBankApp.appUser.model.Permission.*;

@RequiredArgsConstructor
public enum Role {
    CUSTOMER(Collections.emptySet()),
    BRANCH_ADMIN(
            Set.of(
                    BRANCH_ADMIN_READ,
                    BRANCH_ADMIN_UPDATE,
                    BRANCH_ADMIN_DELETE,
                    BRANCH_ADMIN_CREATE
            )
    ),
    BANK_ADMIN(
            Set.of(
                    BRANCH_ADMIN_READ,
                    BRANCH_ADMIN_UPDATE,
                    BRANCH_ADMIN_DELETE,
                    BRANCH_ADMIN_CREATE,

                    BANK_ADMIN_READ,
                    BANK_ADMIN_UPDATE,
                    BANK_ADMIN_DELETE,
                    BANK_ADMIN_CREATE
            )
    );

    @Getter
    private final Set<Permission> permissions;
    public List<SimpleGrantedAuthority> getAuthorities(){
        var authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_"+this.name()));
        return authorities;
    }
}