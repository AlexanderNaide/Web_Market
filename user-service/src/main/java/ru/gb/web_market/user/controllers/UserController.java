package ru.gb.web_market.user.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.gb.web_market.api.dto.MessageDto;
import ru.gb.web_market.api.dto.UserDto;
import ru.gb.web_market.user.entities.User;
import ru.gb.web_market.user.services.UserService;

import java.time.*;
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
        if (!user.getUsername().equals(userDto.getUsername())){
            if(userDto.getUsername() == null) {
                throw new BadCredentialsException("Имя пользователя не может быть пустым");
            } else {
                if (userService.containsUsername(userDto.getUsername()) == 0) {
                    user.setUsername(userDto.getUsername());
                } else {
                    throw new BadCredentialsException("Логин уже занят.");
                }
            }
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

    @PostMapping("/valid_name")
    public MessageDto validName(@RequestHeader String username, @RequestParam String newUserName){
        return new MessageDto(userService.containsUsername(newUserName) == 0 ? "Ok" : "Такой логин уже занят");
    }

    private Long convertToLong(LocalDate dateToConvert) {
        return Date.from(dateToConvert.atTime(LocalTime.MIDNIGHT).atZone(ZoneId.systemDefault()).toInstant()).getTime();
    }

    private LocalDate convertToLocalDate(Long milliseconds) {
        return Instant.ofEpochMilli(milliseconds).atZone(ZoneId.systemDefault()).toLocalDate();
    }



}
