package com.springsecurity.BankApplication.config;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;
import java.util.HashSet;
import java.util.Set;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class ProjectSecurityConfig {



        @Bean
        public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
            http.authorizeHttpRequests((requests) -> requests
                    .requestMatchers("/notices","/contacts").permitAll()
                    .anyRequest().authenticated()
            );

            http.formLogin(withDefaults());
            http.httpBasic(withDefaults());
            return http.build();
        }
        /*
       In Memory user detail Manager
        @Bean
        public InMemoryUserDetailsManager  userCreate(){
            UserDetails admin = User.withDefaultPasswordEncoder()
                                .username("admin")
                                .password("123456")
                                .authorities("admin").build();
            UserDetails user = User.withDefaultPasswordEncoder()
                    .username("user")
                    .password("123456")
                    .authorities("read").build();
            Set<UserDetails> createUser = new HashSet<UserDetails>();
            createUser.add(admin);
            createUser.add(user);
            return new InMemoryUserDetailsManager(createUser);



        }*/


    @Bean
    public PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }



}
