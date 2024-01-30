package ru.gb.web_market.order.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

//@ConstructorBinding
@ConfigurationProperties(prefix = "integrations.product-service")
@Data
public class ProductServiceIntegrationProperties {

    private String urlProductsService;
    private Integer readTimeout;
    private Integer writeTimeout;
    private Integer connectTimeout;
}
