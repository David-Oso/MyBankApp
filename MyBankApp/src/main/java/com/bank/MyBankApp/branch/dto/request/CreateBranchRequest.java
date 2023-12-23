package com.bank.MyBankApp.branch.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateBranchRequest {
    private String branchNumber;
    private String branchName;
    //    address
    private Integer streetNumber;
    private String streetName;
    private String townName;
    private String cityName;
    private String state;
    private String country;
}
