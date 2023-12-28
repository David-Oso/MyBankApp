package com.bank.MyBankApp.appUser.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Permission {
    BRANCH_ADMIN_READ("branch_admin:read"),
    BRANCH_ADMIN_UPDATE("branch_admin:update"),
    BRANCH_ADMIN_CREATE("branch_admin:create"),
    BRANCH_ADMIN_DELETE("branch_admin:delete"),

    BANK_ADMIN_READ("bank_admin:read"),
    BANK_ADMIN_UPDATE("bank_admin:update"),
    BANK_ADMIN_CREATE("bank_admin:create"),
    BANK_ADMIN_DELETE("bank_admin:delete");

    private final String permission;
}
