package com.bank.MyBankApp.utilities;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.stream.Collectors;

public final class MyBankAppUtils {
    public static String BANK_NAME = "First Bank of Nigeria";
    private static String getTemplate(String templateLocation){
        try(BufferedReader reader =
                    new BufferedReader(new FileReader(templateLocation))){
            return reader.lines().collect(Collectors.joining());
        }catch (IOException exception){
            throw new RuntimeException(exception.getMessage());
        }
    }
    private static final String BRANCH_APPROVAL_TEMPLATE_LOCATION = "C:\\Users\\User\\Documents\\MyBankApp\\MyBankApp\\src\\main\\resources\\templates\\branchApprovalMail.html";
    private static final String BRANCH_APPROVED_TEMPLATE_LOCATION = "C:\\Users\\User\\Documents\\MyBankApp\\MyBankApp\\src\\main\\resources\\templates\\branchApprovedMail.html";
    public static String GET_BRANCH_APPROVAL_MAIL_TEMPLATE = getTemplate(BRANCH_APPROVAL_TEMPLATE_LOCATION);
    public static String GET_BRANCH_APPROVED_MAIL_TEMPLATE = getTemplate(BRANCH_APPROVED_TEMPLATE_LOCATION);

    public static final int NUMBER_OF_ITEMS_PER_PAGE = 10;

}
