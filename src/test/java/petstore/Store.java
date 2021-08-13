package petstore;

import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

// 3 - Classe
public class Store {
    // 3.1 - Atributos
    String uri = "https://petstore.swagger.io/v2/store/order";

    // 3.2 - Métodos e Funções
    public String lerJson(String caminhoJson) throws IOException {
        return new String(Files.readAllBytes(Paths.get(caminhoJson)));
    }

    // Testes
    @Test
    public void realizarPedido() throws IOException {
        String jsonBody = lerJson("db/order1.json");

        given()
                .contentType("application/json")
                .log().all()
                .body(jsonBody)
        .when()
                .post(uri)
        .then()
                .log().all()
                .statusCode(200)
                .body("petId", is(1202031974))
                .body("id", notNullValue())
        ;
    }

}
