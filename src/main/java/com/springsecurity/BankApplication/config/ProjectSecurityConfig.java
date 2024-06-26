package com.springsecurity.BankApplication.config;
import com.springsecurity.BankApplication.filters.JwtTokenGenerator;
import com.springsecurity.BankApplication.filters.JwtTokenValidator;
import com.springsecurity.BankApplication.filters.RequestAuthorisation;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class ProjectSecurityConfig {

        ;


        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

            http.sessionManagement((session) -> session
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            ).cors(withDefaults()).csrf(csrf-> csrf.ignoringRequestMatchers("/register","/contact")).addFilterBefore(new RequestAuthorisation(), BasicAuthenticationFilter.class)
                    .addFilterAfter(new JwtTokenGenerator(),BasicAuthenticationFilter.class)
                    .addFilterBefore(new JwtTokenValidator(),BasicAuthenticationFilter.class)
                    .authorizeHttpRequests((requests) -> requests
                    .requestMatchers("/notices","/contact","/register/**","users/**").permitAll()
                    .requestMatchers("/myAccount").hasRole("USER")
                    .requestMatchers("/myBalance").hasAnyRole("USER","ADMIN")
                    .requestMatchers("/myLoans").hasRole("USER")
                    .requestMatchers("/myCards").hasRole("USER")
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
