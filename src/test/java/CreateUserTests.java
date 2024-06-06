import helpers.user.UserChecks;
import helpers.user.UserCreateClient;
import helpers.user.UserDeleteClient;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import pojo.user.UserCreate;

public class CreateUserTests {

    private final UserCreateClient client = new UserCreateClient();
    private final UserChecks check = new UserChecks();
    private String token;

    @Test
    @DisplayName("Создание уникального пользователя")
    public void createUniqueUserTest() {
        UserCreate user = UserCreate.random();
        ValidatableResponse createUserResponse = client.userCreate(user);

        token = check.createdUserSuccessfully(createUserResponse);
        Assert.assertNotNull(token);
    }

    @Test
    @DisplayName("Создание уже зарегистрированого пользователя")
    public void createDublicateUserTest() {
        UserCreate user = UserCreate.random();
        ValidatableResponse createUserResponse = client.userCreate(user);
        token = check.createdUserSuccessfully(createUserResponse);

        String email = user.getEmail();
        UserCreate userDublicate = new UserCreate(email, "Password", "Name");

        ValidatableResponse createDublicateResponse = client.userCreate(userDublicate);
        String messageExists = check.createdExistsUser(createDublicateResponse);

        Assert.assertEquals("User already exists", messageExists);
    }

    @Test
    @DisplayName("Создание пользователя без имени")
    public void createNotNameUserTest() {
        UserCreate user = new UserCreate("email", "password", null);
        ValidatableResponse createResponse = client.userCreate(user);
        String notNameUserMessage = check.createdNotNameUser(createResponse);
        Assert.assertEquals("Email, password and name are required fields", notNameUserMessage);
    }

    @After
    public void after() {
        if (token != null) {
            UserDeleteClient deleteClient = new UserDeleteClient();
            deleteClient.userDelete(token);
        }
    }
}
