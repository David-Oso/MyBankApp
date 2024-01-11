package com.bank.MyBankApp.utilities;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.stream.Collectors;

public final class MyBankAppUtils {
    public static final String BANK_NAME = "First Bank of Nigeria";
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
    private static final String DEPOSIT_TEMPLATE_LOCATION = "C:\\Users\\User\\Documents\\MyBankApp\\MyBankApp\\src\\main\\resources\\templates\\depositNotificationMail.html";
    private static final String WITHDRAW_TEMPLATE_LOCATION = "C:\\Users\\User\\Documents\\MyBankApp\\MyBankApp\\src\\main\\resources\\templates\\withdrawNotificationMail.html";
    private static final String TRANSFER_TEMPLATE_LOCATION = "C:\\Users\\User\\Documents\\MyBankApp\\MyBankApp\\src\\main\\resources\\templates\\transferNotificationMail.html";


    public static final String GET_BRANCH_APPROVAL_MAIL_TEMPLATE = getTemplate(BRANCH_APPROVAL_TEMPLATE_LOCATION);
    public static final String GET_BRANCH_APPROVED_MAIL_TEMPLATE = getTemplate(BRANCH_APPROVED_TEMPLATE_LOCATION);
    public static final String GET_DEPOSIT_MAIL_TEMPLATE = getTemplate(DEPOSIT_TEMPLATE_LOCATION);
    public static final String GET_WITHDRAW_MAIL_TEMPLATE = getTemplate(WITHDRAW_TEMPLATE_LOCATION);
    public static final String GET_TRANSFER_MAIL_TEMPLATE = getTemplate(TRANSFER_TEMPLATE_LOCATION);

    public static final int NUMBER_OF_ITEMS_PER_PAGE = 10;

    public static final String TEST_IMAGE_LOCATION = "C:\\Users\\User\\Documents\\MyBankApp\\MyBankApp\\src\\main\\resources\\static\\testImage.jpg";

}
