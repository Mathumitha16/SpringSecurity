package com.springsecurity.BankApplication.service;

import com.springsecurity.BankApplication.model.Customer;
import com.springsecurity.BankApplication.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class RegistrationService {
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    PasswordEncoder pwdEncode;

    public void  createCustomer(Customer customer){

        String hash = pwdEncode.encode(customer.getPwd());
        customer.setPwd(hash);
        customer.setCreateDt(new Date(System.currentTimeMillis()));

        customerRepository.save(customer);


    }
    public void  findCustomer(String email){
        Customer c = customerRepository.findByEmail(email);
        if(c==null){
            throw new RuntimeException();
        }


    }
}
