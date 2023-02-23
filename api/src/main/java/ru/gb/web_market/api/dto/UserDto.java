package ru.gb.web_market.api.dto;

import java.time.LocalDate;

public class UserDto {
    private String username;

    private String password;
    private String email;
    private String phone;
    private LocalDate birthday;

    public UserDto() {
    }

    public UserDto(String username, String password, String email, String phone, LocalDate birthday) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.birthday = birthday;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }
}
