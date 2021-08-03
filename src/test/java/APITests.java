import io.restassured.RestAssured;
import io.restassured.response.Response;
import models.UserData;
import org.junit.jupiter.api.Test;
import specs.Specs;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class APITests {
    @Test
    void checkSingleUserData(){
        // @formatter:off
        Specs.request
            .given()
            .when()
                .get("/users/2")
            .then()
                .spec(Specs.response)
                .body("data.first_name", equalTo("Janet"),
                        "data.last_name", equalTo("Weaver"));
        // @formatter:on
    }

    @Test
    void checkSingleUserDataWithModel(){
        // @formatter:off
        models.UserData data = Specs.request
                .when()
                    .get("/users/2")
                .then()
                    .spec(Specs.response)
                    .extract().as(models.UserData.class);
        // @formatter:on

        assertEquals("Janet", data.getData().getFirstName());
        assertEquals("Weaver", data.getData().getLastName());
    }

    @Test
    void checkSingleUserWithLombokModel(){
        // @formatter:off
        lombok.UserData data = Specs.request
                .when()
                    .get("/users/2")
                .then()
                    .spec(Specs.response)
                    .extract().as(lombok.UserData.class);
        // @formatter:on

        assertEquals("Janet", data.getUser().getFirstName());
        assertEquals("Weaver", data.getUser().getLastName());

    }

    @Test
    void findResourcesWithMaxAndMinYear(){
        Response response = get("https://reqres.in/api/unknown");
        Integer resourceIdWithHigherYear = response.path("data.max { it.year }.id");
        Integer resourceIdWithLowerYear = response.path("data.min { it.year }.id");

        assertEquals(6, resourceIdWithHigherYear);
        assertEquals(1, resourceIdWithLowerYear);
    }

    @Test
    void checkNameAndColorOfTheResourceWithCertainId(){
        Response response = get("https://reqres.in/api/unknown");
        String resourceName = response.path("data.find { it.id == 3}.name");
        String resourceColor = response.path("data.find { it.id == 3}.color");

        assertEquals("true red", resourceName);
        assertEquals("#BF1932", resourceColor);
    }

    @Test
    void getNotExistingUser(){
        // @formatter:off
        Specs.request
            .when()
                .get("/users/200")
            .then()
                .statusCode(404);
        // @formatter:on
    }

    @Test
    void createNewUser(){
        // @formatter:off
        Response response =
                given()
                    .body("{" +
                        "\"name\": \"John\",\n" +
                        "\"job\": \"java developer\"\n" +
                        "}")
                .when()
                    .post("https://reqres.in/api/users")
                .then()
                    .statusCode(201)
                    .extract().response();
        // @formatter:on
    }

    @Test
    void updateUser(){
        // @formatter:off
        Specs.request
                .given()
                    .body("{" +
                        "\"name\": \"John\",\n" +
                        "\"job\": \"Python developer\"\n" +
                        "}")
                .when()
                    .patch("/users/3")
                .then()
                    .statusCode(200)
                    .body("name", is("John"), "job", is("Python developer"));
        // @formatter:on
    }

    @Test
    void deleteExistingUserTest(){
        // @formatter:off
        given()
        .when()
                .delete("https://reqres.in/api/users/4")
        .then()
                .statusCode(204);
        // @formatter:on
    }
}
