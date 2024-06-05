import helpers.user.*;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import pojo.user.UserChange;
import pojo.user.UserCreate;
import com.github.javafaker.Faker;

import java.time.LocalDateTime;

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
        token = check.createdUserSuccessfully(client.userCreate(user));

        UserChange userChange = new UserChange("Ivan" + faker.name().firstName() + "@yandex.ru", "Velo" + faker.name().lastName());
        Response responseUserChange = userChageAuto.userChengeAuto(token, userChange);
        Assert.assertTrue(check.userAutoChage(responseUserChange));
    }

    @Test
    @DisplayName("Изменение данных неавторизованного пользователя")
    public void changeNotAutoUser() {
        UserChange userChange = new UserChange("Velo" + faker.name().firstName() + LocalDateTime.now() + "@yandex.ru", "Ivan" + faker.name().lastName());
        Response responseUserChange = userChageAuto.userChengeNotAuto(userChange);
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