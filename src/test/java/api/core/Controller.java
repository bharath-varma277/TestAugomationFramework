package api.core;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class Controller {

    public final String PATH = "https://petstore.swagger.io/v2/";

    public Response post(String path, Object payLoad) {
        System.out.println(PATH + path);
        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter writer = mapper.writer();
        String stringJson;
        try {
            stringJson = writer.writeValueAsString(payLoad);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        System.out.println(stringJson);
        return given().body(stringJson)
//                .auth().oauth2(token).header
                .contentType(ContentType.JSON)
                .when()
                .post(PATH + path).then()
                .extract().response();
    }

    public Response get(String path) {
        return given()
                .when()
                .get(path).then().extract().response();
    }
}
