package com.bank.MyBankApp.appSecurity.jwtToken;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MyBankJwtTokenRepository extends JpaRepository<MyBankJwtToken, Integer> {
    @Query(value = """
        select t from MyBankJwtToken  t inner join AppUser  appuser
        on t.appUser.id = appuser.id
        where  appuser.id = :id and (t.isExpired = false or t.isRevoked = false)
        """)
    List<MyBankJwtToken> findAllValidTokenByAppUser(Integer id);
    List<MyBankJwtToken> findAllByAppUserId(Integer appUserId);
    Optional<MyBankJwtToken> findByAccessToken(String accessToken);
}
