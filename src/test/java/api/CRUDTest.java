package api;

import api.core.Controller;
import api.core.User;
import api.core.UserResponse;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CRUDTest {

    @Test
    public void post() {
//        System.setProperty("javax.net.debug", "ssl");
        User user = User.builder().id(59).userName("Bharath").
                firstName("Vanaparthi").lastName("Varma")
                .email("bvarma@gmail.com").password("12345")
                .phone("7897854")
                .userStatus(1).build();
        Controller controller = new Controller();
        Response response = controller.post("user", user);
        UserResponse userResponse = response.as(UserResponse.class);
        System.out.println(userResponse);
        JsonPath jsonPath = response.jsonPath();
        System.out.println("" + jsonPath.get("code"));
        Assert.assertEquals(String.valueOf(200), jsonPath.get("code"));
        System.out.println(response);
    }

    @Test
    public void petStorePostMethod()
    {
        RestAssured.baseURI="https://petstore.swagger.io/#/";
        JSONObject petStore = new JSONObject();
        RequestSpecification httpRequestSpecification = RestAssured.given();
        petStore.put("Id","567");
        petStore.put("petId","56789");
        petStore.put("quantity","2");
        petStore.put("shipDate","2021-11-22T11:45:30.393Z");
        petStore.put("status","complete");
        httpRequestSpecification.header("Content-Type", "application/json");

        Response response = httpRequestSpecification.post("/store/order");
        System.out.println(response.getStatusLine());

        httpRequestSpecification.body(petStore.toString());

        System.out.println(petStore.toString());

    }
}
