package ru.gb.web_market.user.properties;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@Data
@ConfigurationProperties(JwtProperties.PREFIX)
public class JwtProperties {
    public static final String PREFIX = "jwt";

    private Duration expireTime;
    private String secret;

}
