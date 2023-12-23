package com.bank.MyBankApp.branch.Service;

import com.bank.MyBankApp.branch.dto.request.CreateBranchRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class BranchServiceImplTest {
    @Autowired
    private BranchService branchService;
    private CreateBranchRequest createBranchRequest1;
    private CreateBranchRequest createBranchRequest2;
    private CreateBranchRequest createBranchRequest3;
    @BeforeEach
    void setUp() {
        createBranchRequest1 = new CreateBranchRequest();
//        createBranchRequest1.setBranchNumber("FBNNGAAB"); //abeookuta
        createBranchRequest1.setStreetNumber(25);
        createBranchRequest1.setStreetName("Alagomeji");
        createBranchRequest1.setTownName("Sabo");
        createBranchRequest1.setCityName("Yaba");
        createBranchRequest1.setState("Lagos");
        createBranchRequest1.setCountry("Nigeria");


        createBranchRequest2 = new CreateBranchRequest();
//        createBranchRequest2.setBranchNumber("FBNNGA"); //abeookuta
        createBranchRequest2.setStreetNumber(25);
        createBranchRequest2.setStreetName("Alagomeji");
        createBranchRequest2.setTownName("Sabo");
        createBranchRequest2.setCityName("Yaba");
        createBranchRequest2.setState("Lagos");
        createBranchRequest2.setCountry("Nigeria");


        createBranchRequest3 = new CreateBranchRequest();
//        createBranchRequest3.setBranchNumber("FBNNGAAB"); //abeookuta
        createBranchRequest3.setStreetNumber(25);
        createBranchRequest3.setStreetName("Alagomeji");
        createBranchRequest3.setTownName("Sabo");
        createBranchRequest3.setCityName("Yaba");
        createBranchRequest3.setState("Lagos");
        createBranchRequest3.setCountry("Nigeria");



    }

    @Test
    void createNewBranch() {
    }
}