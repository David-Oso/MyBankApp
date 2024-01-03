package com.bank.MyBankApp.customer.controller;

import com.bank.MyBankApp.appUser.dto.request.ChangePasswordRequest;
import com.bank.MyBankApp.customer.dto.request.AddCustomerAddressRequest;
import com.bank.MyBankApp.customer.dto.request.LoginRequest;
import com.bank.MyBankApp.customer.dto.request.RegisterCustomerRequest;
import com.bank.MyBankApp.customer.dto.request.UploadImageRequest;
import com.bank.MyBankApp.customer.dto.response.RegisterCustomerResponse;
import com.bank.MyBankApp.customer.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

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

    @PostMapping("login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest){
        return ResponseEntity.ok(customerService.login(loginRequest));
    }

    @GetMapping("get/{id}")
    public ResponseEntity<?> getCustomerById(@PathVariable Integer id){
        return ResponseEntity.ok(customerService.getCustomerById(id));
    }

//    @PreAuthorize("hasAnyAuthority('branch_admin:read', 'bank_admin:read')")
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

    @PutMapping("change-password")
    public ResponseEntity<?> changePassword(@Valid @RequestBody ChangePasswordRequest request, Principal user){
        return ResponseEntity.ok(customerService.changePassword(request, user));
    }

    @PostMapping(value = "upload-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadImage(@Valid @ModelAttribute UploadImageRequest request){
        return ResponseEntity.ok(customerService.uploadImage(request));
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteCustomerById(@PathVariable Integer id){
        customerService.deleteByCustomerId(id);
        return ResponseEntity.accepted().build();
    }

    @DeleteMapping("delete/all")
    public ResponseEntity<Void> deleteAllCustomers(){
        customerService.deleteAll();
        return ResponseEntity.accepted().build();
    }
    @PostMapping("/logout")
    public ResponseEntity<?> logout(){
        return ResponseEntity.ok("Logout successfully");
    }
}
