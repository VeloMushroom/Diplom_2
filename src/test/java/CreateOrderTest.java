import helpers.ingredients.IngredientsClient;
import helpers.order.OrderChecks;
import helpers.order.OrderCreateClient;
import helpers.user.UserChecks;
import helpers.user.UserCreateClient;
import helpers.user.UserDeleteClient;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import pojo.order.OrderCreate;
import pojo.user.UserCreate;
import java.util.ArrayList;
import java.util.Random;

public class CreateOrderTest {

    private final OrderCreateClient clientOrder = new OrderCreateClient();
    private final UserCreateClient clientUser = new UserCreateClient();
    private final UserChecks checkUser = new UserChecks();
    private final OrderChecks checkOrder = new OrderChecks();
    private final IngredientsClient clientIngredients = new IngredientsClient();
    public final ArrayList<String> ingredients = new ArrayList<>();
    private String token;
    Random random = new Random();

    @Test
    @DisplayName("Создание заказа авторизованым пользователем")
    public void createOrderAutoTest() {
        UserCreate user = UserCreate.random();
        ValidatableResponse createUserResponse = clientUser.userCreate(user);
        token = checkUser.createdUserSuccessfully(createUserResponse);

        Response donwloadIngredients = clientIngredients.DownloadIngredients();
        ArrayList<String> listHeshIngredients = donwloadIngredients.path("data._id");

        ingredients.add(listHeshIngredients.get(random.nextInt(listHeshIngredients.size())));
        ingredients.add(listHeshIngredients.get(random.nextInt(listHeshIngredients.size())));
        OrderCreate orderCreate = new OrderCreate(ingredients);

        Response createOrderResponse = clientOrder.orderAutoCreate(token, orderCreate);
        Assert.assertTrue(checkOrder.createdOrderSuccessfully(createOrderResponse));
    }

    @Test
    @DisplayName("Создание заказа авторизованым пользователем без ингридиентов")
    public void createOrderAutoNotIngTest() {
        UserCreate user = UserCreate.random();
        ValidatableResponse createUserResponse = clientUser.userCreate(user);
        token = checkUser.createdUserSuccessfully(createUserResponse);

        OrderCreate orderCreate = new OrderCreate(ingredients);
        Response createOrderResponse = clientOrder.orderAutoCreate(token, orderCreate);
        Assert.assertFalse(checkOrder.createdOrderNotIng(createOrderResponse));
    }

    @Test
    @DisplayName("Создание заказа авторизованым пользователем с неправельным хешем")
    public void createOrderAutoBadHeshTest() {
        UserCreate user = UserCreate.random();
        ValidatableResponse createUserResponse = clientUser.userCreate(user);
        token = checkUser.createdUserSuccessfully(createUserResponse);

        ingredients.add("Привет");
        ingredients.add("Пока");

        OrderCreate orderCreate = new OrderCreate(ingredients);
        Response createOrderResponse = clientOrder.orderAutoCreate(token, orderCreate);
        checkOrder.createdOrderBadHesh(createOrderResponse);
    }

    @Test
    @DisplayName("Создание заказа не авторизованым пользователем")
    public void createOrderNotAutoTest() {
        Response donwloadIngredients = clientIngredients.DownloadIngredients();
        ArrayList<String> listHeshIngredients = donwloadIngredients.path("data._id");

        ingredients.add(listHeshIngredients.get(random.nextInt(listHeshIngredients.size())));
        ingredients.add(listHeshIngredients.get(random.nextInt(listHeshIngredients.size())));
        OrderCreate orderCreate = new OrderCreate(ingredients);

        Response createOrderResponse = clientOrder.orderNotAutoCreate(orderCreate);
        Assert.assertTrue(checkOrder.createdOrderSuccessfully(createOrderResponse));
    }

    @After
    public void after() {
        if (token != null) {
            UserDeleteClient deleteClient = new UserDeleteClient();
            deleteClient.userDelete(token);
        }
    }
}