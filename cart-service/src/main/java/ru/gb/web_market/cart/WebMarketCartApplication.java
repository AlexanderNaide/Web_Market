package ru.gb.web_market.cart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WebMarketCartApplication {

    /**
     * На будущее:
     *
     * 1. На фронте добавить в корзине кнопку "Оформить заказ"
     * 2. С фронта добавить адрес и телефон покупателя
     * 3. Заказ оформлять может только вошедший пользователь (установить защиту)
     * 4. В ордер-сервисе получать текущую корзину и преобразовать в заказ
     * 5. Сохранять заказ с позициями заказа в БД
     * 6. На фронте можно добавить окно показа заказов юзера
     */

    public static void main(String[] args) {
        SpringApplication.run(WebMarketCartApplication.class, args);
    }

}
