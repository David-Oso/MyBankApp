package com.bank.MyBankApp.branch.Service;

import com.bank.MyBankApp.account.model.Account;
import com.bank.MyBankApp.branch.dto.request.CreateBranchRequest;
import com.bank.MyBankApp.branch.dto.response.BranchResponse;
import com.bank.MyBankApp.branch.dto.response.CreateBranchResponse;
import com.bank.MyBankApp.branch.model.Branch;
import com.bank.MyBankApp.customer.model.Customer;
import com.bank.MyBankApp.loan.model.Loan;

public interface BranchService {
    CreateBranchResponse createNewBranch(CreateBranchRequest request);
    BranchResponse getBranchByBranchNumber(String branchNumber);
    BranchResponse getBranchById(Integer id);
//    void addAccount(Account account);
    void addAccount(Account account);
}
//