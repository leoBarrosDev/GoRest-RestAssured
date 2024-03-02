import io.restassured.RestAssured;
import io.restassured.config.RestAssuredConfig;
import io.restassured.config.SSLConfig;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class TestGoRest {

    String token = "234ab9a18dec1470b5ebcedc27ef7eb2c1662c6f29968a4aac1db2b5f9c7d778";
    String BASE_URL = "https://gorest.co.in/public-api";
    String USER_RESOURCE = "/users";
    String POST_RESOURCE = "/posts";
    String COMMENT_RESOURCE = "/comments";
    String TODO_RESOURCE = "/todos";

    @Test
    @DisplayName("Deve listar todos os usuários cadastrados na base de dados")
    public void listUsers() {

        RestAssured.config = RestAssuredConfig.config().sslConfig(
                new SSLConfig().relaxedHTTPSValidation()
        );

        String response = "{}";

        given()
                .contentType(ContentType.JSON)
                .when()
                .get(BASE_URL + USER_RESOURCE)
                .then()
                .statusCode(200)
                .body("data", notNullValue())
                .body("data", not(empty()))
                //.time(lessThan(1000L))
                .body("data", everyItem(hasKey("id")))
                .body("data", everyItem(hasKey("name")))
                .body("data", everyItem(hasKey("email")))
                .body("data", everyItem(hasKey("gender")))
                .body("data", everyItem(hasKey("status")));

    }

    @Test
    @DisplayName("Deve cadastrar um usuário na base de dados")
    public void createUser() {
        RestAssured.config = RestAssuredConfig.config().sslConfig(
                new SSLConfig().relaxedHTTPSValidation()
        );
        String newClient = " \"name\": \"Viola Huels\",\n" +
                "        \"email\": \"Augustine_Zieme@yahoo.com\",\n" +
                "        \"gender\": \"female\",\n" +
                "        \"status\": \"active\"";

        String response = "{\n" +
                "    \"code\": 201,\n" +
                "    \"meta\": null,\n" +
                "    \"data\": {\n" +
                "        \"id\": 6754563,\n" +
                "        \"name\": \"Viola Huels\",\n" +
                "        \"email\": \"Augustine_Zieme@yahoo.com\",\n" +
                "        \"gender\": \"female\",\n" +
                "        \"status\": \"active\"\n" +
                "    }\n" +
                "}";
        given()
                .contentType(ContentType.JSON)
                //.header("Content-Type", "application/json")
                .header("Authorization", "Bearer" + token)
                .body(newClient)
                .when()
                .post(BASE_URL + USER_RESOURCE)
                .then()
                //.statusCode(200)
                .assertThat().body(containsString(response));

    }
}
