package helpers.ingredients;

import helpers.Client;
import io.qameta.allure.Step;
import io.restassured.response.Response;

public class IngredientsClient extends Client {
    public static final String INGREDIENTS_GET_PATH = "ingredients";

    @Step("Download ingredients")
    public Response DownloadIngredients() {
        return spec().get(INGREDIENTS_GET_PATH);
    }
}
