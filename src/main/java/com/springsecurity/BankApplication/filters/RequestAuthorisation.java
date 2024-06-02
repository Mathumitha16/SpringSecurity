package com.springsecurity.BankApplication.filters;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpRequest;
import org.springframework.security.authentication.BadCredentialsException;


import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.Base64;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

public class RequestAuthorisation implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String BasicHeader = httpRequest.getHeader(AUTHORIZATION);
        BasicHeader.trim();
       byte[] decoded = Base64.getDecoder().decode(BasicHeader.substring(6));
       String decodedValue = new String(decoded);
       int colon = decodedValue.indexOf(":");
       if(colon == -1){

       throw new BadCredentialsException("Invalid Authorization header");



       }
       else{
           String userName = decodedValue.substring(0,colon);
           if(userName.indexOf("test")!=-1){
               httpResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);

           }
       }


       chain.doFilter(request, response);

    }
}
