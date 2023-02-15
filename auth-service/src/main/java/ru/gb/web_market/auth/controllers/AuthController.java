package ru.gb.web_market.auth.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.gb.web_market.auth.entities.AuthRequest;
import ru.gb.web_market.auth.entities.AuthResponse;
import ru.gb.web_market.auth.services.JwtService;

@Slf4j
@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
//@CrossOrigin("*")
public class AuthController {
    private final AuthenticationManager authenticationManager;
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

}
