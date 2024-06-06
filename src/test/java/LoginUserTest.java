import helpers.user.UserChecks;
import helpers.user.UserCreateClient;
import helpers.user.UserDeleteClient;
import helpers.user.UserLoginClient;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import pojo.user.UserCreate;
import pojo.user.UserLogin;

public class LoginUserTest {

    private String token;
    private final UserCreateClient client = new UserCreateClient();
    private final UserChecks check = new UserChecks();
    private final UserLoginClient clientLogin = new UserLoginClient();

    @Test
    @DisplayName("Логин уникального пользователя")
    public void loginUniqueUserTest() {
        UserCreate user = UserCreate.random();
        ValidatableResponse createUserResponse = client.userCreate(user);
        token = check.createdUserSuccessfully(createUserResponse);

        String userEmail = user.getEmail();
        String userPassword = user.getPassword();
        UserLogin userLogin = new UserLogin(userEmail, userPassword);

        ValidatableResponse loginResponse = clientLogin.userLogin(userLogin);
        Assert.assertTrue(check.loginSuccessfully(loginResponse));
    }

    @Test
    @DisplayName("Не правильный логин пользователя")
    public void faleLoginUniqueUserTest() {
        UserLogin userLogin = new UserLogin("null", "null");
        ValidatableResponse loginResponse = clientLogin.userLogin(userLogin);

        Assert.assertFalse(check.loginFailed(loginResponse));
    }

    @After
    public void after() {
        if (token != null) {
            UserDeleteClient deleteClient = new UserDeleteClient();
            deleteClient.userDelete(token);
        }
    }
}