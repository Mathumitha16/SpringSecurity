package com.springsecurity.BankApplication.config;
import com.springsecurity.BankApplication.filters.JwtTokenGenerator;
import com.springsecurity.BankApplication.filters.JwtTokenValidator;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class ProjectSecurityConfig {

        ;


        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

            http.sessionManagement((session) -> session
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            ).cors(withDefaults()).csrf(csrf-> csrf.ignoringRequestMatchers("/register","/contact"))
                    .addFilterAfter(new JwtTokenGenerator(),BasicAuthenticationFilter.class)
                    .addFilterBefore(new JwtTokenValidator(),BasicAuthenticationFilter.class)
                    .authorizeHttpRequests((requests) -> requests
                    .requestMatchers("/notices","/contact","/register/**").permitAll()
                    .requestMatchers("/myAccount").hasAnyRole("USER","ADMIN")
                    .requestMatchers("/myBalance").hasAnyRole("USER","ADMIN")
                    .requestMatchers("/myLoans").hasAnyRole("USER","ADMIN")
                    .requestMatchers("/myCards").hasAnyRole("USER","ADMIN")
                    .requestMatchers("/user").authenticated()
            );

            http.formLogin(withDefaults());
            http.httpBasic(withDefaults());
            return http.build();
        }



    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }



}
