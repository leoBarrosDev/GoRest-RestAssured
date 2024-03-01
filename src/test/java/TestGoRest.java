import io.restassured.RestAssured;
import io.restassured.config.RestAssuredConfig;
import io.restassured.config.SSLConfig;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class TestGoRest {

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
                .time(lessThan(2000L));




    }
}
