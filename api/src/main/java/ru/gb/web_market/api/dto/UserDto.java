package ru.gb.web_market.api.dto;


import java.util.Date;


public class UserDto {
    private String username;
    private String password;
    private String email;
    private String phone;
    private Long birthday;

    public UserDto() {
    }

    public UserDto(String username, String email, String phone, Long birthday) {
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.birthday = birthday;
    }

    private UserDto(Builder builder) {
        setUsername(builder.username);
        setPassword(builder.password);
        setEmail(builder.email);
        setPhone(builder.phone);
        setBirthday(builder.birthday);
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

    public Long getBirthday() {
        return birthday;
    }

    public void setBirthday(Long birthday) {
        this.birthday = birthday;
    }


    public static final class Builder {
        private String username;
        private String password;
        private String email;
        private String phone;
        private Long birthday;

        private Builder() {
        }

        public static Builder newBuilder() {
            return new Builder();
        }

        public Builder setUsername(String username) {
            this.username = username;
            return this;
        }

        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder setPhone(String phone) {
            this.phone = phone;
            return this;
        }

        public Builder setBirthday(Long birthday) {
            this.birthday = birthday;
            return this;
        }

        public UserDto build() {
            return new UserDto(this);
        }
    }
}
