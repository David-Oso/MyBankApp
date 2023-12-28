package com.bank.MyBankApp.bank.service;

import com.bank.MyBankApp.address.model.Address;
import com.bank.MyBankApp.bank.model.Bank;
import com.bank.MyBankApp.bank.repository.BankRepository;
import com.bank.MyBankApp.bank.response.BankResponse;
import com.bank.MyBankApp.branch.model.Branch;
import com.bank.MyBankApp.branch.repository.BranchRepository;
import com.bank.MyBankApp.exception.MyBankException;
import com.bank.MyBankApp.exception.NotFoundException;
import com.bank.MyBankApp.mail.MailService;
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
    private final BranchRepository branchRepository;
    private final MailService mailService;

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

    @Override
    public String addNewBranch(Integer bankId, Branch branch) {
        Bank bank = getBank(bankId);
        bank.getBranches().add(branch);
        bankRepository.save(bank);
        log.info("\n:::::::::::::::::::::  NEW BRANCH ADDED  :::::::::::::::::::::\n");
        return "New branch added";
    }

    @Override
    public String approveBranch(Integer branchId) {
        Branch branch = getBranchById(branchId);
        branch.setApproved(true);
        Address address = branch.getBranchAddress();
        String branchNumber = generateBranchNumber(address.getStreetNumber(), address.getStreetName(),
                address.getTownName(), address.getCityName(), address.getState());
        branch.setBranchName(branchNumber);
        branchRepository.save(branch);
        String subject = "Branch approval";
        String htmlContent = MyBankAppUtils.GET_BRANCH_APPROVED_MAIL_TEMPLATE;
        mailService.sendMail("First bank", branch.getBranchEmail(), subject, htmlContent);
        log.info("\n:::::::::::::::::::::  BRANCH APPROVED  :::::::::::::::::::::\n");
        return "Branch approved successfully";
    }
//    Q22#92f3

    private Branch getBranchById(Integer branchId) {
        return branchRepository.findById(branchId).orElseThrow(
                ()-> new NotFoundException("Branch with this id not found"));
    }

    private static String generateBranchNumber(int streetNumber, String streetName, String townName, String city, String state) {
        StringBuilder branchNumber = new StringBuilder("FIRNG");
        String street = getInitials(String.valueOf(streetNumber));
        String streetN = getInitials(streetName);
        String town =  getInitials(townName);
        String cty = getInitials(city);
        String ste = getInitials(state);
        branchNumber.append(street).append(streetN).append(town).append(cty).append(ste);
        return branchNumber.toString().toUpperCase();
    }

    private static String getInitials(String name){
        return String.valueOf(name.charAt(0));
    }

    private BankResponse getBankResponse(Bank bank){
        Address bankAddress = modelMapper.map(bank.getBankAddress(), Address.class);
        return BankResponse.builder()
                .name(bank.getName())
                .bankAddress(bankAddress)
                .build();
    }
}
