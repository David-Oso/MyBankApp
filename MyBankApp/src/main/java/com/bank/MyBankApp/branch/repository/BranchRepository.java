package com.bank.MyBankApp.branch.repository;

import com.bank.MyBankApp.branch.model.Branch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BranchRepository extends JpaRepository<Branch, Integer> {
    Optional<Branch> findByBranchNumber(String branchNumber);
}
