package ru.gb.web_market.order.security;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@Data
@ConfigurationProperties(JwtProperties.PREFIX)
public class JwtProperties {
    public static final String PREFIX = "jwt";

//    private long expireTimeMillis;  //так можно по простому хранить интервал в миллисекундах, но есть продвинутый способ в виде класса Duration
    private Duration expireTime; // Более удобный способ задания времени (см. доки) и из Duration можно удобно получать любое значение (.toMillis(), .toMinutes(), toDays() и пр.)
    private String secret;

}
