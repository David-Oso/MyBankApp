package com.bank.MyBankApp.branch.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class BranchResponse {
    private String branchName;
    private String branchNumber;
    private String firstName;
    private String middleName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private Integer streetNumber;
    private String streetName;
    private String townName;
    private String cityName;
    private String state;
    private String country;
}
