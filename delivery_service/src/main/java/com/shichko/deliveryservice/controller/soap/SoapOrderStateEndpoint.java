package com.shichko.deliveryservice.controller.soap;

import com.shichko.deliveryservice.model.service.OrderService;
import com.shichko.deliveryservice.util.RsaCipherUtil;
import https.github_com.vladislav2147.delivery_app.GetOrderStateRequest;
import https.github_com.vladislav2147.delivery_app.GetOrderStateResponse;
import https.github_com.vladislav2147.delivery_app.OrderState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class SoapOrderStateEndpoint {
    private static final String NAMESPACE_URI = "https://github.com/Vladislav2147/Delivery_App";

    @Autowired
    private OrderService service;


    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getOrderStateRequest")
    @ResponsePayload
    public GetOrderStateResponse getOrderState(@RequestPayload GetOrderStateRequest request) {
        GetOrderStateResponse response = new GetOrderStateResponse();
        int id = RsaCipherUtil.decrypt(request.getId());
        service.findById(id).ifPresent(order -> {
            OrderState state = new OrderState();
            state.setId(order.getId());
            state.setAddress(order.getAddress());
            state.setCourier(order.getCourier().getFirstName() + " " + order.getCourier().getSecondName());
            state.setState(order.getState().name());
            response.setOrderState(state);
        });

        return response;
    }
}
