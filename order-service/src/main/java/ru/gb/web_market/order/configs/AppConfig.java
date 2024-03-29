package ru.gb.web_market.order.configs;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import ru.gb.web_market.order.properties.AuthServiceIntegrationProperties;
import ru.gb.web_market.order.properties.ProductServiceIntegrationProperties;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableConfigurationProperties({
        ProductServiceIntegrationProperties.class,
        AuthServiceIntegrationProperties.class
})
@RequiredArgsConstructor
public class AppConfig {
    private final ProductServiceIntegrationProperties productServiceIntegrationProperties;

    @Bean
    public WebClient productServiceWebClient(){
/*
        TcpClient tcpclient = TcpClient
                .create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, productServiceIntegrationProperties.getConnectTimeout())
                .doOnConnected(connection -> {
                    connection.addHandlerLast(new ReadTimeoutHandler(productServiceIntegrationProperties.getReadTimeout(), TimeUnit.MILLISECONDS));
                    connection.addHandlerLast(new WriteTimeoutHandler(productServiceIntegrationProperties.getWriteTimeout(), TimeUnit.MILLISECONDS));
                });

        return WebClient
                .builder()
                .baseUrl(productServiceIntegrationProperties.getUrl())
                .clientConnector(new ReactorClientHttpConnector(HttpClient.from(tcpclient)))
                .build();

                // это для старого! спринга по лекции.
*/

        HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, productServiceIntegrationProperties.getConnectTimeout())
//                .responseTimeout(Duration.ofMillis(5000))
                .doOnConnected(connection ->
                        connection.addHandlerLast(new ReadTimeoutHandler(productServiceIntegrationProperties.getReadTimeout(), TimeUnit.MILLISECONDS))
                                .addHandlerLast(new WriteTimeoutHandler(productServiceIntegrationProperties.getWriteTimeout(), TimeUnit.MILLISECONDS)));

        return WebClient.builder()
//                .baseUrl(productServiceIntegrationProperties.getUrlProductsService())
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();

        // Это для нового спринга по докам

    }

}
