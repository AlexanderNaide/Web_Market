package ru.gb.web_market.core.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "integrations.auth-service")
@Data
public class AuthServiceIntegrationProperties {

    private String urlAuthService;
    private Integer readTimeout;
    private Integer writeTimeout;
    private Integer connectTimeout;
}
