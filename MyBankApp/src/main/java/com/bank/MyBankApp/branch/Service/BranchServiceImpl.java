package com.bank.MyBankApp.branch.Service;

import com.bank.MyBankApp.account.model.Account;
import com.bank.MyBankApp.address.model.Address;
import com.bank.MyBankApp.appUser.model.AppUser;
import com.bank.MyBankApp.branch.dto.request.CreateBranchRequest;
import com.bank.MyBankApp.branch.dto.response.BranchResponse;
import com.bank.MyBankApp.branch.dto.response.CreateBranchResponse;
import com.bank.MyBankApp.branch.model.Branch;
import com.bank.MyBankApp.branch.repository.BranchRepository;
import com.bank.MyBankApp.exception.NotFoundException;
import com.bank.MyBankApp.loan.model.Loan;
import com.bank.MyBankApp.mail.MailService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import static com.bank.MyBankApp.utilities.MyBankAppUtils.GET_BRANCH_APPROVAL_MAIL_TEMPLATE;

@Service
@RequiredArgsConstructor
public class BranchServiceImpl implements BranchService{
    private final BranchRepository branchRepository;
    private final ModelMapper modelMapper;
    private final MailService mailService;
    @Value(("${spring.mail.username}"))
    private String senderEmail;

    @Override
    public CreateBranchResponse createNewBranch(CreateBranchRequest request) {
        Branch branch = modelMapper.map(request, Branch.class);
        Address branchAddress = modelMapper.map(request, Address.class);
        branch.setBranchAddress(branchAddress);
        Branch savedBranch = branchRepository.save(branch);
//        sendApprovalToBank
        String subject = "Branch Approval Request";
        String htmlContent =
                String.format(GET_BRANCH_APPROVAL_MAIL_TEMPLATE,
                        savedBranch.getId(), savedBranch.getAppUser().getEmail());
        mailService.sendMail(senderEmail, subject, htmlContent);
        return CreateBranchResponse.builder()
                .branchName(savedBranch.getBranchName())
                .branchNumber(savedBranch.getBranchNumber())
                .build();
    }

    @Override
    public BranchResponse getBranchByBranchNumber(String branchNumber) {
        Branch branch = getByBranchNumber(branchNumber);
        return mapBranchToResponse(branch);
    }

    @Override
    public BranchResponse getBranchById(Integer branchId) {
        Branch branch = getById(branchId);
        return mapBranchToResponse(branch);
    }

    @Override
    public void addAccount(Integer branchId, Account account) {
        Branch branch = getById(branchId);
        branch.getAccounts().add(account);
        branchRepository.save(branch);
    }

    @Override
    public void addLoan(Integer branchId, Loan loan) {
        Branch  branch = getById(branchId);
        branch.getLoans().add(loan);
        branchRepository.save(branch);
    }

    private static BranchResponse mapBranchToResponse(Branch branch){
        AppUser appUser = branch.getAppUser();
        Address address = branch.getBranchAddress();
        return BranchResponse.builder()
                .branchName(branch.getBranchName())
                .branchNumber(branch.getBranchNumber())
                .email(appUser.getEmail())
                .phoneNumber(appUser.getPhoneNumber())
                .streetNumber(address.getStreetNumber())
                .streetName(address.getStreetName())
                .townName(address.getTownName())
                .cityName(address.getCityName())
                .state(address.getState())
                .country(address.getCountry())
                .build();
    }

//    @Override
    private Branch getById(Integer branchId) {
        return branchRepository.findById(branchId).orElseThrow(
                ()-> new NotFoundException("Branch with this id not found."));
    }

    private Branch getByBranchNumber(String branchNumber){
        return branchRepository.findByBranchNumber(branchNumber).orElseThrow(
                ()-> new NotFoundException("Branch with this branch number not found"));
    }
}
