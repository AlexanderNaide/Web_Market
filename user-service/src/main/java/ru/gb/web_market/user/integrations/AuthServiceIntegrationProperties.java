package ru.gb.web_market.user.integrations;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "integrations.product-service")
@Data
public class AuthServiceIntegrationProperties {

    private String url;
    private Integer readTimeout;
    private Integer writeTimeout;
    private Integer connectTimeout;
}
