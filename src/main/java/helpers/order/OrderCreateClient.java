package helpers.order;

import helpers.Client;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import pojo.order.OrderCreate;

public class OrderCreateClient extends Client {
    public static final String ORDER_CREATE_PATH = "orders";

    @Step("Create order authorization user")
    public Response orderAutoCreate(String token, OrderCreate orderCreate) {
        return spec().auth().oauth2(token.replace("Bearer ","")).body(orderCreate)
                .post(ORDER_CREATE_PATH);
    }

    @Step("Create order not authorization user")
    public Response orderNotAutoCreate(OrderCreate orderCreate) {
        return spec().body(orderCreate).post(ORDER_CREATE_PATH);

    }


}
