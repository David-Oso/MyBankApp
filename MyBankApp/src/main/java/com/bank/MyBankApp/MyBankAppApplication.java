package com.bank.MyBankApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class MyBankAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyBankAppApplication.class, args);
	}

}
