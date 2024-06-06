package helpers.order;

import helpers.Client;
import io.qameta.allure.Step;
import io.restassured.response.Response;

public class OrderDownloadClient extends Client {
    public static final String ORDER_DOWNLOAD_PATH = "orders";

    @Step("Create order authorization user")
    public Response orderAutoDownload(String token) {
        return spec().auth().oauth2(token.replace("Bearer ",""))
                .get(ORDER_DOWNLOAD_PATH);
    }

    @Step("Download order not authorization user")
    public Response orderNotAutoDownload() {
        return spec().get(ORDER_DOWNLOAD_PATH);
    }
}
