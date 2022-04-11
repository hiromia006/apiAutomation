package com.test;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class SingleUser extends BaseApiTest {
    @Test
    public void testSingleUser() {
        given()
                .header("Content-type", "application/json")
                .accept(ContentType.JSON)
                .when()
                .get("/users/2")
                .then()
                .statusCode(200)
                .log().body()
                .body("data.id", equalTo(2))
                .body("data.first_name", equalTo("Janet"));
    }

    @Test
    public void testSingleUser2() {
        Response response = given()
                .header("Content-type", "application/json")
                .accept(ContentType.JSON)
                .log().uri()
                .when()
                .get("/users?page=2");

        String first_name = response.jsonPath().getString("data[1].first_name");
        String last_name = response.jsonPath().getString("data[1].last_name");
        int userId = response.jsonPath().getInt("data[1].id");
        System.out.println("userId " + userId);

        given()
                .header("Content-type", "application/json")
                .accept(ContentType.JSON)
                .when()
                .get("/users/{userId}", userId)
                .then()
                .statusCode(200)
                .log().body()
                .body("data.id", equalTo(userId))
                .body("data.first_name", equalTo(first_name))
                .body("data.last_name", equalTo(last_name));
    }

    @Test
    public void testSingleUserShouldFail() {
        given()
                .header("Content-type", "application/json")
                .accept(ContentType.JSON)
                .when()
                .get("/users/23")
                .then()
                .statusCode(404)
                .log().body();
    }
}
