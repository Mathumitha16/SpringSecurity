package com.springsecurity.BankApplication.config;

import com.springsecurity.BankApplication.model.Customer;
import com.springsecurity.BankApplication.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class BankUsers implements UserDetailsService {
    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Customer customer = customerRepository.findByEmail(username);
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(customer.getRole()));
        if(customer==null){
            throw new UsernameNotFoundException("No user exists with username : "+username);
        }
        return new User(customer.getEmail(),customer.getPassword(),customer.getEnabled(),customer.getAccountNotExpired(),customer.getCredentialsNotExpired(),customer.getAccountNotLocked(),authorities);
    }
}
