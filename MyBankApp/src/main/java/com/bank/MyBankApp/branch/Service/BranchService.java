package com.bank.MyBankApp.branch.Service;

import com.bank.MyBankApp.branch.dto.request.CreateBranchRequest;
import com.bank.MyBankApp.branch.dto.response.CreateBranchResponse;

public interface BranchService {
    CreateBranchResponse createNewBranch(CreateBranchRequest request);
}
