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
import ru.gb.web_market.api.dto.UserDto;
import ru.gb.web_market.user.entities.AuthRequest;
import ru.gb.web_market.user.entities.AuthResponse;
import ru.gb.web_market.user.entities.User;
import ru.gb.web_market.user.services.JwtService;
import ru.gb.web_market.user.services.RoleService;
import ru.gb.web_market.user.services.UserService;

import java.text.SimpleDateFormat;
import java.time.*;
import java.util.Collections;
import java.util.Date;

@Slf4j
@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserService userService;

    @GetMapping("/information")
    public UserDto getInformation(@RequestHeader String username){
        User user = userService.findByUsername(username).orElseThrow(() -> new BadCredentialsException("Пользователь не найден"));
        UserDto userDto = new UserDto();
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());
        userDto.setPhone(user.getPhone());
        if(user.getBirthday() != null){
            userDto.setBirthday(convertToLong(user.getBirthday()));
        }
        return userDto;
    }

    @PostMapping("/save")
    public void saveInformation(@RequestHeader String username, @RequestBody UserDto userDto){
        User user = userService.findByUsername(username).orElseThrow(() -> new BadCredentialsException("Пользователь не найден"));
        if(userDto.getUsername() != null) {
            user.setUsername(userDto.getUsername());
        }
        if(userDto.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        }
        if(userDto.getEmail() != null) {
            user.setEmail(userDto.getEmail());
        }
        if(userDto.getPhone() != null) {
            user.setPhone(userDto.getPhone());
        }
        if(userDto.getBirthday() != null) {
            user.setBirthday(convertToLocalDate(userDto.getBirthday()));
        }
        userService.save(user);
    }

    private Long convertToLong(LocalDate dateToConvert) {
        return Date.from(dateToConvert.atTime(LocalTime.MIDNIGHT).atZone(ZoneId.systemDefault()).toInstant()).getTime();
    }

    private LocalDate convertToLocalDate(Long milliseconds) {
        return Instant.ofEpochMilli(milliseconds).atZone(ZoneId.systemDefault()).toLocalDate();
    }



}
