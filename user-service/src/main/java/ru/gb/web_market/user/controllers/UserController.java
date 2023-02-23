package ru.gb.web_market.user.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gb.web_market.user.entities.AuthRequest;
import ru.gb.web_market.user.entities.AuthResponse;
import ru.gb.web_market.user.entities.User;
import ru.gb.web_market.user.services.JwtService;
import ru.gb.web_market.user.services.RoleService;
import ru.gb.web_market.user.services.UserService;

import java.util.Collections;

@Slf4j
@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserService userService;






}
