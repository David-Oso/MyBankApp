package com.bank.MyBankApp.branch.Service;

import com.bank.MyBankApp.address.model.Address;
import com.bank.MyBankApp.branch.dto.request.CreateBranchRequest;
import com.bank.MyBankApp.branch.dto.response.CreateBranchResponse;
import com.bank.MyBankApp.branch.model.Branch;
import com.bank.MyBankApp.branch.repository.BranchRepository;
import com.bank.MyBankApp.exception.NotFoundException;
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
    public Branch getByBranchById(Integer branchId) {
        return branchRepository.findById(branchId).orElseThrow(
                ()-> new NotFoundException("Branch with this id not found."));
    }


}
