package com.bank.MyBankApp.utilities;

public final class ValidationUtils {
public static final String EMAIL_REGEX_STRING = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
    public static final String PASSWORD_REGEX_STRING = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
    public static final String NAME_REGEX = "^[A-Z][a-zA-Z]{0,39}$";
    public static final String PHONE_NUMBER_REGEX = "^(\\+?234|0)[789][01]\\d{8}$";
    public static final String PIN_REGEX = "^\\d{4}$";
    public static final String AMOUNT_REGEX = "^\\d";
    public static final String ACCOUNT_NUMBER_REGEX = "^\\d{10}$";
    public static final String DATE_OF_BIRTH_REGEX = "dd/MM/yyyy";
}
