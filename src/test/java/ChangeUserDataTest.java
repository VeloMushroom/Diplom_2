import helpers.user.*;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import pojo.user.UserChange;
import pojo.user.UserCreate;
import com.github.javafaker.Faker;

public class ChangeUserDataTest {

    private final UserChecks check = new UserChecks();
    private final UserChangeClient userChageAuto = new UserChangeClient();
    private String token;
    static Faker faker = new Faker();

    @Test
    @DisplayName("Изменение данных авторизованного пользователя")
    public void changeAutoUser() {
        final UserCreateClient client = new UserCreateClient();
        final UserCreate user = UserCreate.random();
        token = check.createdSuccessfully(client.userCreate(user));

        UserChange userChange = new UserChange(faker.pokemon().name(), faker.chuckNorris().fact());
        Response responseUserChange = userChageAuto.userChengeAuto(token, userChange);
        Assert.assertTrue(check.userAutoChage(responseUserChange));
    }

    @Test
    @DisplayName("Изменение данных неавторизованного пользователя")
    public void changeNotAutoUser() {
        UserChange userChange = new UserChange(faker.app().name(), faker.app().version());
        Response responseUserChange = userChageAuto.userChengeNotAuto();
        Assert.assertEquals("You should be authorised", check.userNotAutoChage(responseUserChange));
    }

    @After
    public void after() {
        if (token != null) {
            UserDeleteClient deleteClient = new UserDeleteClient();
            deleteClient.userDelete(token);
        }
    }
}