package com.springsecurity.BankApplication.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ContactController {
    @GetMapping("/contacts")
    public String getContact(){
        return "Fetching contact from db";
    }
}
