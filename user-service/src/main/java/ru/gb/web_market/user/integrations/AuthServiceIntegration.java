package ru.gb.web_market.user.integrations;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import ru.gb.web_market.api.dto.CartDto;
import ru.gb.web_market.api.dto.OrderFullDto;
import ru.gb.web_market.api.dto.ProductFullDto;
import ru.gb.web_market.api.exception.AppError;
import ru.gb.web_market.api.exception.ResourceNotFoundException;

@Component
@RequiredArgsConstructor
public class AuthServiceIntegration {
    private final WebClient authServiceWebClient;

/*    public ProductFullDto getProductById(Long id){
        return authServiceWebClient.get() // "собираем запрос"
                .uri("/api/v1/products/" + id)
//                .header("username", username) //это если нужны headers, добавляем через .
//                .bodyValue(body) //если в тело надо включить объект - делается так. Естественно с get не работает, надо в post или где там еще...
                .retrieve() //"что делать с ответом"
//                .toBodilessEntity() //если ответ просто статус код без тела ответа
                .onStatus( //метод, который принимает некий предикат(когда надо сработать) и некую функцию(что в этом случае надо сделать), указать через ,
                        httpStatusCode -> httpStatusCode.value() == HttpStatus.NOT_FOUND.value(),
//                        clientResponse -> Mono.error(new ResourceNotFoundException("Товар не найден в продуктовом МС")) //так мы кидаем новую ошибку на статус
                        clientResponse -> clientResponse.bodyToMono(AppError.class).map(ae -> new ResourceNotFoundException(ae.getMessage())) // так мы достаем ошибку из ответа и используем текст из неё
                )
                .bodyToMono(ProductFullDto.class) // а вот так забираем из тела объект указанного класса
//                .bodyToFlux(ProductFullDto.class) // так мы указываем, что объект не один и надо последовательно получать все объекты. .block() в этом случае не работает
                .block(); //это блокирующая строка, которая указывает ждать ответа, то есть "синхронный режим", если её не добавить, то запрос будет "асинхронным"
    }*/

    public CartDto updateCart(CartDto cart){
        return authServiceWebClient.post()
                .uri("/api/v1/products/update_cart")
                .bodyValue(cart)
                .retrieve()
                .onStatus(
                        HttpStatusCode::is4xxClientError,
                        clientResponse -> clientResponse.bodyToMono(AppError.class).map(ae -> new ResourceNotFoundException(ae.getMessage()))
                )
                .bodyToMono(CartDto.class)
                .block();
    }

//    public OrderFullDto updateOrderList(OrderFullDto orderFullDto){
//        return authServiceWebClient.post()
//                .uri("/api/v1/products/update_order_list")
//                .bodyValue(orderFullDto)
//                .retrieve()
//                .onStatus(
//                        HttpStatusCode::is4xxClientError,
//                        clientResponse -> clientResponse.bodyToMono(AppError.class).map(ae -> new ResourceNotFoundException(ae.getMessage()))
//                )
//                .bodyToMono(OrderFullDto.class)
//                .block();
//    }





}
