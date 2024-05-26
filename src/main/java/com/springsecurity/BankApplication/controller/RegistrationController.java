package com.springsecurity.BankApplication.controller;

import com.springsecurity.BankApplication.model.Customer;
import com.springsecurity.BankApplication.repository.CustomerRepository;
import com.springsecurity.BankApplication.service.RegistrationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RegistrationController {
    @Autowired
    private RegistrationService registrationService;
    @Autowired
    private CustomerRepository customerRepository;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody Customer customer){
       try{
        registrationService.createCustomer(customer);

             registrationService.findCustomer(customer.getEmail());
             return ResponseEntity.status(HttpStatus.CREATED).body(customer);
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }


    }
    @RequestMapping("/user")
    public Customer getUserDetailsAfterLogin(Authentication authentication) {
        return customerRepository.findByEmail(authentication.getName());


    }
}
