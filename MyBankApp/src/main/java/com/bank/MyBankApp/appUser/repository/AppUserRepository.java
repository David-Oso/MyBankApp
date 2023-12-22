package com.bank.MyBankApp.appUser.repository;

import com.bank.MyBankApp.appUser.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository extends JpaRepository<AppUser, Integer> {
}
