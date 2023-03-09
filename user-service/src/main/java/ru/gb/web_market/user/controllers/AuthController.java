package ru.gb.web_market.user.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.gb.web_market.user.entities.AuthRequest;
import ru.gb.web_market.user.entities.AuthResponse;
import ru.gb.web_market.user.entities.User;
import ru.gb.web_market.user.services.JwtService;
import ru.gb.web_market.user.services.RoleService;
import ru.gb.web_market.user.services.UserService;

import java.util.Arrays;
import java.util.Collections;

@Slf4j
@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserService userService;
    private final RoleService roleService;
    private final JwtService jwtService;

    @PostMapping("/token")
    public AuthResponse token(@RequestBody AuthRequest request){
        log.info("Request from: {}", request.getUsername());
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());

        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        UserDetails user = (UserDetails) authenticate.getPrincipal();
        String token = jwtService.generateToken(user);
        return new AuthResponse(token);
    }

    @PostMapping("/registrations")
    public AuthResponse register(@RequestBody AuthRequest request){
        log.info("Request registration from: {}", request.getUsername());
        if(userService.findByUsername(request.getUsername()).isPresent()){
            throw new BadCredentialsException("Пользователь c таким логином уже существует.");
        } else {
            User user = new User();
            user.setUsername(request.getUsername());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setRoles(Collections.singletonList(roleService.findByRole("ROLE_USER").orElse(null)));
            userService.save(user);
        }
        return token(request);
    }

}
