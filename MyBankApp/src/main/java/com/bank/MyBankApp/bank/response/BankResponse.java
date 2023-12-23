package com.bank.MyBankApp.bank.response;

import com.bank.MyBankApp.address.model.Address;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class BankResponse {
    private String name;
    private Address bankAddress;
}
