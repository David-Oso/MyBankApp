package com.bank.MyBankApp.bank.service;

import com.bank.MyBankApp.address.model.Address;
import com.bank.MyBankApp.bank.model.Bank;
import com.bank.MyBankApp.bank.repository.BankRepository;
import com.bank.MyBankApp.bank.response.BankResponse;
import com.bank.MyBankApp.exception.MyBankException;
import com.bank.MyBankApp.utilities.MyBankAppUtils;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class BankServiceImpl implements BankService{
    private final BankRepository bankRepository;
    private final ModelMapper modelMapper;
    @Value("${bank.code}")
    private String bankCode;
    @Value("${bank.phone_number}")
    private String bankPhoneNumber;
    @Value("${bank.email}")
    private String bankEmail;

    @PostConstruct
    private void createBank(){
        if(bankRepository.count() < 1){
            Bank bank = new Bank();
            bank.setBankCode(bankCode);
            bank.setName(MyBankAppUtils.BANK_NAME);
            bank.setBankPhoneNumber(bankPhoneNumber);
            bank.setBankEmail(bankEmail);
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
        Bank bank = getBank(id);
        return getBankResponse(bank);
    }

    private Bank getBank(Integer id) {
        return bankRepository.findBankById(id).orElseThrow(
                ()-> new MyBankException("Bank with the entered id not found"));
    }

    @Override
    public Bank getBankByBankId(Integer bankId) {
        return getBank(bankId);
    }

    @Override
    public BankResponse getBankByBankCode(String bankCode) {
        Bank bank = bankRepository.findByBankCode(bankCode).orElseThrow(
                ()-> new MyBankException("Bank with the entered code not found"));
        return getBankResponse(bank);
    }

    private BankResponse getBankResponse(Bank bank){
        Address bankAddress = modelMapper.map(bank.getBankAddress(), Address.class);
        return BankResponse.builder()
                .name(bank.getName())
                .bankAddress(bankAddress)
                .build();
    }
}
