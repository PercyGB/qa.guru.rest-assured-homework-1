import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class APITests {
    @Test
    void getUserWithIdTest(){
        given()
        .when()
                .get("https://reqres.in/api/users/2")
        .then()
                .statusCode(200)
                .body("data.first_name", equalTo("Janet"),
                        "data.last_name", equalTo("Weaver"));
    }

    @Test
    void getNotExistingUserTest(){
        given()
        .when()
                .get("https://reqres.in/api/users/200")
        .then()
                .statusCode(404);
    }

    @Test
    void createNewUserTest(){
        Response response = given()
                .contentType(ContentType.JSON)
                .body("{" +
                        "\"name\": \"John\",\n" +
                        "\"job\": \"java developer\"\n" +
                        "}")
        .when()
                .post("https://reqres.in/api/users")
        .then()
                .statusCode(201)
                .extract().response();

        System.out.println(response.asString());
    }

    @Test
    void updateUserTest(){
        given()
                .contentType(ContentType.JSON)
                .body("{" +
                        "\"name\": \"John\",\n" +
                        "\"job\": \"Python developer\"\n" +
                        "}")
        .when()
                .patch("https://reqres.in/api/users/3")
        .then()
                .statusCode(200)
                .log().body();
    }

    @Test
    void deleteExistingUserTest(){
        given()
        .when()
                .delete("https://reqres.in/api/users/4")
        .then()
                .statusCode(204);
    }
}
