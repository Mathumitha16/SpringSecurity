package com.springsecurity.BankApplication.config;

import com.springsecurity.BankApplication.model.Customer;
import com.springsecurity.BankApplication.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BankAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();
        String userEnteredPassword = authentication.getCredentials().toString();
        Customer customer = customerRepository.findByEmail(email);
        if(customer ==null){
            throw new BadCredentialsException("User Does not exists");
        }
        else{
            String pwd = customer.getPwd();
            List<GrantedAuthority> authorities = new ArrayList<>();
            SimpleGrantedAuthority auth = new SimpleGrantedAuthority(customer.getRole());
            authorities.add(auth);
           if(passwordEncoder.matches(userEnteredPassword,pwd)){

               return new UsernamePasswordAuthenticationToken(email,pwd,authorities);

           }
           else {
               throw new BadCredentialsException("Password does not Match");
           }

        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
