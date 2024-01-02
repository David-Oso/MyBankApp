package com.bank.MyBankApp.utilities;

public final class ValidationUtils {
//    customer
    public static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
    public static final String NIN_REGEX = "^\\d{11}$";
    public static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
    public static final String NAME_REGEX = "^[A-Z][a-zA-Z]{0,39}$";
    public static final String BVN_REGEX = "^\\d{11}$";
    public static final String DATE_OF_BIRTH_REGEX = "dd/MM/yyyy";
    public static final String PHONE_NUMBER_REGEX = "^(\\+?234|0)[789][01]\\d{8}$";

//    account
    public static final String PIN_REGEX = "^\\d{4}$";
    public static final String IBAN_REGEX = "^DE\\d{2} \\d{4} \\d{4} \\d{4} \\d{4} \\d{2}$";
    public static final String ACCOUNT_NUMBER_REGEX = "^\\d{10}$";

//    address
    public static final String ADDRESS_REGEX = "^[a-zA-Z]+$";
    public static final String STREET_NUMBER_REGEX = "^[1-9][0-9]{0,2}$";
}
