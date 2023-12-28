package com.bank.MyBankApp.customer.controller;

import com.bank.MyBankApp.customer.dto.request.AddCustomerAddressRequest;
import com.bank.MyBankApp.customer.dto.request.RegisterCustomerRequest;
import com.bank.MyBankApp.customer.dto.response.RegisterCustomerResponse;
import com.bank.MyBankApp.customer.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping("register")
    public ResponseEntity<?> registerCustomer(@Valid @RequestBody RegisterCustomerRequest request){
        RegisterCustomerResponse response = customerService.registerCustomer(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("add-customer-address/{id}")
    public ResponseEntity<?> addCustomerAddress(@Valid @RequestBody AddCustomerAddressRequest request, @PathVariable  Integer id){
        return ResponseEntity.ok(customerService.addCustomerAddress(request, id));
    }

    @GetMapping("get/{id}")
    public ResponseEntity<?> getCustomerById(@PathVariable Integer id){
        return ResponseEntity.ok(customerService.getCustomerById(id));
    }

    @GetMapping("get/email")
    public ResponseEntity<?> getCustomerByEmail(@RequestParam String email){
        return ResponseEntity.ok(customerService.getCustomerByEmail(email));
    }

    @GetMapping("get/phone-number")
    public ResponseEntity<?> getCustomerByPhoneNumber(@RequestParam String phoneNumber){
        return ResponseEntity.ok(customerService.getCustomerByPhoneNumber(phoneNumber));
    }

    @GetMapping("get/nin")
    public ResponseEntity<?> getCustomerByNin(@RequestParam String nin){
        return ResponseEntity.ok(customerService.getCustomerByNin(nin));
    }

    @GetMapping("get/bvn")
    public  ResponseEntity<?> getCustomerByBvn(@RequestParam String bvn){
        return ResponseEntity.ok(customerService.getCustomerByBvn(bvn));
    }

    @GetMapping("get/all")
    public ResponseEntity<?> getAllCustomers(@RequestParam int pageNumber){
        return ResponseEntity.ok(customerService.getAllCustomers(pageNumber));
    }

    @DeleteMapping("delete/{id}")
    public void deleteCustomerById(@PathVariable Integer id){
        customerService.deleteByCustomerId(id);
    }

    @DeleteMapping("delete/all")
    public void deleteAllCustomers(){
        customerService.deleteAll();
    }
}
