package com.springsecurity.BankApplication.filters;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.*;

public class JwtTokenGenerator extends OncePerRequestFilter {
    private static final String key = "h!@17#9e@723!8rf=-yh*&hq100~2BE@WD=0^%";
    private String pop(Collection<? extends GrantedAuthority> collect){

        HashSet<String> auth = new HashSet<>();
        for(GrantedAuthority x : collect){
            auth.add(x.getAuthority());

        }
        return String.join(",",auth);

    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        Map<String,String> claims = new HashMap<>();
        if(authentication!=null) {

           Object authorities = authentication.getCredentials();

            SecretKey secretKey = Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8));


            String jwt = Jwts.builder().setIssuer("BankApp").setSubject("JWTToken")
                    .claim("username",authentication.getName())
                    .claim("Authorities",pop(authentication.getAuthorities()))
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(new Date().getTime()+30000000))
                    .signWith(secretKey).compact();
            response.setHeader("Authorization",jwt);
            filterChain.doFilter(request,response);
        }




    }
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return !request.getServletPath().equals("/user");
    }
}
