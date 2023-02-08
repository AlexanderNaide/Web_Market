package ru.gb.web_market.soap.controllers;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import ru.gb.web_market.soap.services.CategoriesServiceSOAP;
import ru.gb.web_market.soap.services.ProductServiceSOAP;
import ru.gb.web_market.soap.categories.GetCategoriesByParentCategoryRequest;
import ru.gb.web_market.soap.categories.GetCategoriesByParentCategoryResponse;
import ru.gb.web_market.soap.products.GetProductByCategoryRequest;
import ru.gb.web_market.soap.products.GetProductByCategoryResponse;

@Endpoint
@RequiredArgsConstructor
public class ProductControllerSOAP {
    private static final String NAMESPACE_PRODUCT_URL = "http://www.web_market.ru/spring/wm/products";
    private static final String NAMESPACE_CATEGORY_URL = "http://www.web_market.ru/spring/wm/categories";
    private final ProductServiceSOAP productServiceSOAP;
    private final CategoriesServiceSOAP categoriesServiceSOAP;


    /**
     * Запрос товаров по категории и номеру страницы
     */

    /*
    POST http://localhost:8080/ws
    Header -> Content-Type: text/xml

        <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:pr="http://www.web_market.ru/spring/wm/products">
            <soapenv:Header/>
            <soapenv:Body>
                <pr:getProductByCategoryRequest>
                    <pr:category>Перфораторы</pr:category>
                    <pr:page>1</pr:page>
                </pr:getProductByCategoryRequest>
            </soapenv:Body>
        </soapenv:Envelope>
     */


    @PayloadRoot(namespace = NAMESPACE_PRODUCT_URL, localPart = "getProductByCategoryRequest")
    @ResponsePayload
    @Transactional
    public GetProductByCategoryResponse getProductByCategoryResponse(@RequestPayload GetProductByCategoryRequest request){
        GetProductByCategoryResponse response = new GetProductByCategoryResponse();
        productServiceSOAP.getProductByCategory(request).forEach(response.getProducts()::add);
        return response;
    }


    /*
    POST http://localhost:8080/ws
    Header -> Content-Type: text/xml

        <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:cat="http://www.web_market.ru/spring/wm/categories">
            <soapenv:Header/>
            <soapenv:Body>
                <cat:getCategoriesByParentCategoryRequest>
                    <cat:title>Электроинструмент</cat:title>
                </cat:getCategoriesByParentCategoryRequest>
            </soapenv:Body>
        </soapenv:Envelope>
    */

    /**
     * Запрос вложенных категорий у родительской категории
     */

    @PayloadRoot(namespace = NAMESPACE_CATEGORY_URL, localPart = "getCategoriesByParentCategoryRequest")
    @ResponsePayload
    @Transactional
    public GetCategoriesByParentCategoryResponse getCategoriesByParentCategoryRequest(@RequestPayload GetCategoriesByParentCategoryRequest request){
        GetCategoriesByParentCategoryResponse response = new GetCategoriesByParentCategoryResponse();
        categoriesServiceSOAP.findAllByParentCategory(request).forEach(response.getCategories()::add);
        return response;
    }
}
