package ru.gb.web_market.auth.configs;

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
import ru.gb.web_market.auth.integrations.AuthServiceIntegrationProperties;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableConfigurationProperties(AuthServiceIntegrationProperties.class)
@RequiredArgsConstructor
public class AppConfig {

    private final AuthServiceIntegrationProperties authServiceIntegrationProperties;

    @Bean
    public WebClient productServiceWebClient(){

        HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, authServiceIntegrationProperties.getConnectTimeout())
//                .responseTimeout(Duration.ofMillis(5000))
                .doOnConnected(connection ->
                        connection.addHandlerLast(new ReadTimeoutHandler(authServiceIntegrationProperties.getReadTimeout(), TimeUnit.MILLISECONDS))
                                .addHandlerLast(new WriteTimeoutHandler(authServiceIntegrationProperties.getWriteTimeout(), TimeUnit.MILLISECONDS)));

        return WebClient.builder()
                .baseUrl(authServiceIntegrationProperties.getUrl())
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();

    }

}
