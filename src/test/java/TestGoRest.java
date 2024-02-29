import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class TestGoRest {

    private final String BASE_URL = "https://gorest.co.in/public-api";

    @Test
    @DisplayName("Deve listar todos os usuários cadastrados na base de dados")
    public void listUsers(){
        given()
                .when()
                .then();
    }
}
