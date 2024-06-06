package helpers.order;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import java.net.HttpURLConnection;
import java.util.ArrayList;

public class OrderChecks {

    @Step("Check order created successfully")
    public boolean createdOrderSuccessfully(Response createOrderResponse) {
        return createOrderResponse.then().log().all()
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_OK)
                .extract()
                .path("success");
    }

    @Step("Check order created not ingredients")
    public boolean createdOrderNotIng(Response createOrderResponse) {
        return createOrderResponse.then().log().all()
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_BAD_REQUEST)
                .extract()
                .path("success");
    }

    @Step("Check order created bad hesh")
    public void createdOrderBadHesh(Response createOrderResponse) {
        createOrderResponse.then().log().all()
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_INTERNAL_ERROR);
    }

    @Step("Check order download authorization user")
    public ArrayList<String> downloadOrderAutoUser(Response response) {
        return response.then().log().all()
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_OK)
                .extract().path("orders._id");
    }

    @Step("Check order download not authorization user")
    public boolean downloadOrderNotAutoUser(Response response) {
        return response.then().log().all()
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_UNAUTHORIZED)
                .extract().path("success");
    }
}
