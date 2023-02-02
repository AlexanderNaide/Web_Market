package ru.gb.web_market.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.Objects;

//@Component
//public class StandardAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {
//
//    private UserDetailsService userDetailsService;
//
//    @Autowired
//    public void setUserDetailsService(UserDetailsService userDetailsService) {
//        this.userDetailsService = userDetailsService;
//    }
//
//    @Override
//    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
//        if(!Objects.equals(userDetails.getPassword(), authentication.getCredentials())){
//            throw new BadCredentialsException("Пароль неверен.");
//        }
//    }
//
//    @Override
//    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication){
//        try {
//            return userDetailsService.loadUserByUsername(username);
//        } catch (AuthenticationException e){
//            throw new BadCredentialsException("Пользователь не найден.");
//        }
//    }
//}
