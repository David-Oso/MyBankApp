package com.bank.MyBankApp.bank.service;

import com.bank.MyBankApp.address.model.Address;
import com.bank.MyBankApp.bank.model.Bank;
import com.bank.MyBankApp.bank.repository.BankRepository;
import com.bank.MyBankApp.bank.response.BankResponse;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class BankServiceImpl implements BankService{
    private final BankRepository bankRepository;
    @Value("${bank.code}")
    private String bankCode;

    @PostConstruct
    private void createBank(){
        if(bankRepository.count() < 1){
            Bank bank = new Bank();
            bank.setBankCode(bankCode);
            bank.setName("First Bank of Nigeria");
            Address bankAddress = createBankAddress();
            bank.setBankAddress(bankAddress);
            bankRepository.save(bank);
            log.info("\n::::::::::::::::::::  BANK CREATED  ::::::::::::::::::::\n");
        }
    }

    private Address createBankAddress(){
        Address address = new Address();
        address.setStreetNumber(5);
        address.setStreetName("Herbert Macaulay");
        address.setTownName("Sabo");
        address.setCityName("Yaba");
        address.setState("Lagos");
        address.setCountry("Nigeria");
        return address;
    }

    @Override
    public BankResponse getBankById(Integer id) {
        return null;
    }

    @Override
    public BankResponse getBankByBankCode(String bankCode) {
        return null;
    }
}
