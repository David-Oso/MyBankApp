package com.bank.MyBankApp.branch.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CreateBranchResponse {
   private String branchName;
   private String branchNumber;
}
