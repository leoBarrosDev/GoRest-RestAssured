import io.restassured.RestAssured;
import io.restassured.config.RestAssuredConfig;
import io.restassured.config.SSLConfig;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
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

    @BeforeEach
    public void sslConfig() {
        RestAssured.config = RestAssuredConfig.config().sslConfig(
                new SSLConfig().relaxedHTTPSValidation()
        );
    }


    @Test
    @DisplayName("Deve listar todos os usuários cadastrados na base de dados")
    public void listUsers() {

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
        String newClient = "{\n" +
                "    \"name\": \"Novo Teste\",\n" +
                "    \"email\": \"lala@gmail.com\",\n" +
                "    \"gender\": \"male\",\n" +
                "    \"status\": \"active\"\n" +
                "}";

        given()
                .header("Authorization", "Bearer " + token)
                .body(newClient)
                .contentType(ContentType.JSON)
                .when()
                .post(BASE_URL + USER_RESOURCE)
                .then()
                .assertThat()
                .statusCode(200)
                .body("data", hasKey("id"))
                .body("data", hasKey("name"))
                .body("data", hasKey("email"))
                .body("data", hasKey("gender"))
                .body("data", hasKey("status"))
                .body("code", equalTo(201))
                .log().all();

    }

    @Test
    @DisplayName("Deve atualizar um usuário baseado no ID informado como parâmetro e considerando os dados passados no body")
    public void updateUser() {
        RestAssured.config = RestAssuredConfig.config().sslConfig(
                new SSLConfig().relaxedHTTPSValidation()
        );

        String newClient = "{\n" +
                "    \"name\": \"lele Teste\",\n" +
                "    \"email\": \"lelel123123e@gmail.com\",\n" +
                "    \"gender\": \"male\",\n" +
                "    \"status\": \"active\"\n" +
                "}";

        String newDataClient = "{\n" +
                "    \"name\": \"Leandro H. de B. Reis\",\n" +
                "    \"email\": \"leohbr12345@gmail.com\",\n" +
                "    \"gender\": \"Male\",\n" +
                "    \"status\": \"active\"\n" +
                "}";

        Response responseCreateUser = given()
                .header("Authorization", "Bearer " + token)
                .body(newClient)
                .contentType(ContentType.JSON)
                .when()
                .post(BASE_URL + USER_RESOURCE);

        Integer userId = responseCreateUser.path("data.id");


        given()
                .header("Authorization", "Bearer " + token)
                .body(newDataClient)
                .contentType(ContentType.JSON)
                .when()
                .put(BASE_URL + USER_RESOURCE + "/" + userId)
                .then()
                .statusCode(200)
                //.body(containsString(newDataClient))
                .body("code", equalTo(200))
                .log().all();
    }

    @Test
    @DisplayName("Deve excluir um usuário na base de dados baseado no id passado como parâmetro")
    public void deleteUser() {

        String newClient = "{\n" +
                "    \"name\": \"Novo Teste\",\n" +
                "    \"email\": \"la159la@gmail.com\",\n" +
                "    \"gender\": \"male\",\n" +
                "    \"status\": \"active\"\n" +
                "}";
        Response responseCreateUser = given()
                .header("Authorization", "Bearer " + token)
                .body(newClient)
                .contentType(ContentType.JSON)
                .when()
                .post(BASE_URL + USER_RESOURCE);

        Integer userId = responseCreateUser.path("data.id");


        given()
                .header("Authorization", "Bearer " + token)
                .when()
                .delete(BASE_URL + USER_RESOURCE + "/" + userId)
                .then()
                .statusCode(200)
                //.body("code", equalTo(204))
                .log().all();
    }
}
