package specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.with;
import static org.hamcrest.Matchers.*;

public class Specs {
    public static RequestSpecification request = with()
            .baseUri("https://reqres.in")
            .basePath("/api")
            .contentType(ContentType.JSON)
            .log().all();

    public static ResponseSpecification response = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .expectResponseTime(lessThan(3000L))
            .build();
}
