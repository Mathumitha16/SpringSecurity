package com.springsecurity.BankApplication.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {

    @GetMapping("/myAccounts")
    public String GetAccounts(){
        return "Fetch Accounts from DB";

    }



}
