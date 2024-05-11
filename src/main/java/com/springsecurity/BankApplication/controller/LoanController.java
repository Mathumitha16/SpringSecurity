package com.springsecurity.BankApplication.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoanController {
    @GetMapping("/myLoans")
    public String getLoanData(){
        return "Fetch loan data from DB";
    }
}
