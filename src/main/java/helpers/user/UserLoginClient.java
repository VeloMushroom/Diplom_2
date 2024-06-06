package helpers.user;

import helpers.Client;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import pojo.user.UserLogin;

public class UserLoginClient extends Client {
    public static final String USER_LOGIN_PATH = "auth/login";

    @Step("Authorization user")
    public ValidatableResponse userLogin (UserLogin userLogin) {
                return spec()
                .body(userLogin)
                .when()
                .post(USER_LOGIN_PATH)
                .then().log().all();
    }
}
