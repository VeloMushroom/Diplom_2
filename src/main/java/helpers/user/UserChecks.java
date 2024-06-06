package helpers.user;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import java.net.HttpURLConnection;

public class UserChecks {

    @Step("Check user created successfully")
    public String createdUserSuccessfully(ValidatableResponse createUserResponse) {
        return createUserResponse
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_OK)
                .extract()
                .path("accessToken");
    }

    @Step("Check user exists")
    public String createdExistsUser(ValidatableResponse existUserMessage) {
        return existUserMessage
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_FORBIDDEN)
                .extract()
                .path("message");
    }

    @Step("Check user not name")
    public String createdNotNameUser(ValidatableResponse notNameUserMessage) {
        return notNameUserMessage
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_FORBIDDEN)
                .extract()
                .path("message");
    }

    @Step("Check user login successfully")
    public boolean loginSuccessfully(ValidatableResponse loginResponse) {
        return loginResponse
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_OK)
                .extract()
                .path("success");
    }

    @Step("Check user login faild")
    public boolean loginFailed(ValidatableResponse loginResponse) {
        return loginResponse
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_UNAUTHORIZED)
                .extract()
                .path("success");
    }

    @Step("Check user authorization")
    public boolean userAutoChage(Response response) {
        return response.then().log().all()
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_OK)
                .extract()
                .path("success");
    }

    @Step("Check user not authorization")
    public String userNotAutoChage(Response response) {
        return response.then().log().all()
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_UNAUTHORIZED)
                .extract()
                .path("message");
    }
}
