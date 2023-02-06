package ru.gb.web_market.api;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import ru.gb.web_market.services.ProductServiceSOAP;
import ru.gb.web_market.soap.products.GetProductByCategoryRequest;
import ru.gb.web_market.soap.products.GetProductByCategoryResponse;

@Endpoint
@RequiredArgsConstructor
public class ProductControllerSOAP {
    private static final String NAMESPACE_URL = "http://www.web_market.ru/spring/wm/products";
    private final ProductServiceSOAP productServiceSOAP;


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


    @PayloadRoot(namespace = NAMESPACE_URL, localPart = "getProductByCategoryRequest")
    @ResponsePayload
    @Transactional
    public GetProductByCategoryResponse getProductByCategoryResponse(@RequestPayload GetProductByCategoryRequest request){
        GetProductByCategoryResponse response = new GetProductByCategoryResponse();
        productServiceSOAP.getProductByCategory(request).stream().forEach(response.getProducts()::add);
        return response;
    }
}
