package ru.gb.web_market.core.integrations;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import ru.gb.web_market.api.dto.CartDto;
import ru.gb.web_market.api.dto.OrderFullDto;
import ru.gb.web_market.api.dto.ProductFullDto;
import ru.gb.web_market.api.exception.AppError;
import ru.gb.web_market.api.exception.ResourceNotFoundException;
import ru.gb.web_market.core.properties.AuthServiceIntegrationProperties;

@Component
@RequiredArgsConstructor
public class AuthServiceIntegration {

    private final WebClient authServiceWebClient;
    private final AuthServiceIntegrationProperties authServiceIntegrationProperties;

/*    @PostConstruct
    public void setAuthServiceWebClient(@Qualifier("auth") WebClient authServiceWebClient) {
        this.authServiceWebClient = authServiceWebClient;
    }*/


    public CartDto getCartDto(String username){
        return authServiceWebClient.get()
                .uri(authServiceIntegrationProperties.getUrlAuthService() + "/api/v1/cart")
                .header("username", username)
                .retrieve()
                .onStatus(
                        HttpStatusCode::is4xxClientError,
                        clientResponse -> clientResponse.bodyToMono(AppError.class).map(ae -> new ResourceNotFoundException(ae.getMessage()))
                )
                .bodyToMono(CartDto.class)
                .block();
    }

    public void clearCart(String username){
        authServiceWebClient.get()
                .uri(authServiceIntegrationProperties.getUrlAuthService() + "/api/v1/cart/clear")
                .header("username", username)
                .retrieve()
                .onStatus(
                        HttpStatusCode::is4xxClientError,
                        clientResponse -> clientResponse.bodyToMono(AppError.class).map(ae -> new ResourceNotFoundException(ae.getMessage()))
                )
                .toBodilessEntity()
                .block();
    }

}
