// 1 - Pacote
package petstore;

// 2 - Bibliotecas

import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.contains;

// 3 - Classe
public class Pet {
    // 3.1 - Atributos
    String uri = "https://petstore.swagger.io/v2/pet"; // endereço da entidade Pet


    // 3.2 - Métodos e Funções
    public String lerJson(String caminhoJson) throws IOException {

        return new String(Files.readAllBytes(Paths.get(caminhoJson)));
    }

    // Incluir - Create - Post
    @Test (priority = 1)// Identifica o método ou função como um teste para o TestNG
    public void incluirPet() throws IOException {
        String jsonBody = lerJson("db/pet1.json");

        // Sintaxe Gherkin
        // Dado - Quando - Então
        // Given - When - Then

        given() // Dado
                .contentType("application/json") // comum em API REST - antigas era "text/xml"
                .log().all()
                .body(jsonBody)
        .when() // Quando
                .post(uri)
        .then() // Então
                .log().all()
                .statusCode(200)
                .body("name", is("Leassy"))
                .body("status", is("available"))
                .body("category.name", is("Dog"))
                .body("tags.name", contains("STA")) // por se tratar de um array precisamos utilizar o contains
        ;
    }
    @Test (priority = 2)
    public void consultarPet(){
        String petId = "1202031974";

        given()
                .contentType("application/json")
                .log().all()
        .when()
                .get(uri + "/" + petId)
        .then()
                .log().all()
                .statusCode(200)
                .body("id", is(1202031974))
                .body("name", is("Leassy"))
                .body("status", is("available"))
        ;
    }

    @Test (priority = 3)
    public void alterarPet() throws IOException {
        String jsonBody = lerJson("db/pet2.json");

        given()
                .contentType("application/json")
                .log().all()
                .body(jsonBody)
        .when()
                .put(uri)
        .then()
                .log().all()
                .statusCode(200)
                .body("id", is(1202031974))
                .body("status", is("donated"))
        ;
    }

    @Test (priority = 4)
    public void apagarPet(){
        String petId = "1202031974";

        given()
                .contentType("application/json")
                .log().all()
        .when()
                .delete(uri + "/" + petId)
        .then()
                .log().all()
                .statusCode(200)
                .body("message", is("1202031974"))
        ;
    }
}
