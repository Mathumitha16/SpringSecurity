package com.springsecurity.BankApplication.model;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name="customer")

public class Customer {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;
    @Column(name="email")
    private String email;
    @Column(name="pwd")
    private String password;
    @Column(name="role")
    private String role;
    @Column(name="enabled")
    private Boolean enabled;
    @Column(name="Account_Not_Locked")
    private Boolean accountNotLocked;
    @Column(name="Credentials_Not_Expired")
    private Boolean credentialsNotExpired;
    @Column(name="Account_Non_Expired")
    private Boolean accountNotExpired;
}
