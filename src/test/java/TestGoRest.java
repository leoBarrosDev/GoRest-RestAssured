import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class TestGoRest {

    private final String BASE_URL = "https://gorest.co.in/public-api";
    private final String USER_RESOURCE = "/users";
    private final String POST_RESOURCE = "/posts";
    private final String COMMENT_RESOURCE = "/comments";
    private final String TODO_RESOURCE = "/todos";

    @Test
    @DisplayName("Deve listar todos os usuários cadastrados na base de dados")
    public void listUsers(){
        given()
                .when()
                .then();
    }
}
