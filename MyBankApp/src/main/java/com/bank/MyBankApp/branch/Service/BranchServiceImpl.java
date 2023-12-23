package com.bank.MyBankApp.branch.Service;

import com.bank.MyBankApp.address.model.Address;
import com.bank.MyBankApp.branch.dto.request.CreateBranchRequest;
import com.bank.MyBankApp.branch.dto.response.CreateBranchResponse;
import com.bank.MyBankApp.branch.model.Branch;
import com.bank.MyBankApp.branch.repository.BranchRepository;
import com.bank.MyBankApp.utilities.MyBankAppUtils;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BranchServiceImpl implements BranchService{
    private final BranchRepository branchRepository;
    private final ModelMapper modelMapper;
    @Override
    public CreateBranchResponse createNewBranch(CreateBranchRequest request) {
        Branch branch = modelMapper.map(request, Branch.class);
        branch.setBranchName(MyBankAppUtils.BANK_NAME);
        Address branchAddress = modelMapper.map(request, Address.class);
        branch.setBranchAddress(branchAddress);
        Branch savedBranch = branchRepository.save(branch);
        return CreateBranchResponse.builder()
                .branchName(savedBranch.getBranchName())
                .branchNumber(savedBranch.getBranchNumber())
                .build();
    }

    @Override
    public String generateBranchNumber(Address address) {
        return null;
    }
}
