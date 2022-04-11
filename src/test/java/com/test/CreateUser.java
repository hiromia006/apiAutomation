package com.test;

import com.test.pojo.UserData;
import com.thedeanda.lorem.LoremIpsum;
import io.restassured.http.ContentType;
import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class CreateUser extends BaseApiTest {

    @Test
    public void createUserShouldSucceed() {
        Map<String, Object> stringObjectMa = new HashMap<>();
        stringObjectMa.put("name", LoremIpsum.getInstance().getTitle(1));
        stringObjectMa.put("job", "leader");
        System.out.println(stringObjectMa.toString());
        //Ctrl+D

//        String json = "{\n" +
//                "    \"name\": \"morpheus\",\n" +
//                "    \"job\": \"leader\"\n" +
//                "}";

        given()
                .spec(requestSpecification())
                .body(stringObjectMa.toString())
                .log().uri()
                .when()
                .post("/users")
                .then()
                .statusCode(201)
                .log().body()
                .body("name", equalTo("morpheus"))
                .body("job", equalTo("leader"));
    }

    @Test
    public void patchUserShouldSucceed() {
        String name = LoremIpsum.getInstance().getTitle(1);
        Map<String, Object> stringObjectMa = new HashMap<>();
        stringObjectMa.put("name", name);
        stringObjectMa.put("job", "leader");
        System.out.println(stringObjectMa.toString());
//        Ctrl+D

//        String json = "{\n" +
//                "    \"name\": \"morpheus\",\n" +
//                "    \"job\": \"zion resident\"\n" +
//                "}";

        JSONObject jsonObject = new JSONObject(stringObjectMa);

        given()
                .header("Content-type", "application/json")
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(jsonObject.toJSONString())
                .log().uri()
                .log().body()
                .when()
                .patch("/users/2")
                .then()
                .statusCode(200)
                .log().body()
                .body("name", equalTo(name))
                .body("job", equalTo("leader"));

    }


    @Test
    public void patchUserShouldSucceed2() {
        String name = LoremIpsum.getInstance().getTitle(1);

        given()
                .header("Content-type", "application/json")
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(new UserData(name, "leader"))
                .log().uri()
                .log().body()
                .when()
                .patch("/users/2")
                .then()
                .statusCode(200)
                .log().body()
                .body("name", equalTo(name))
                .body("job", equalTo("leader"));

    }


    @Test
    public void patchUserShouldSucceed3() {
        String name = LoremIpsum.getInstance().getTitle(1);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", name);
        jsonObject.put("job", "leader");

        given()
                .header("Content-type", "application/json")
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(jsonObject.toJSONString())
                .log().uri()
                .log().body()
                .when()
                .patch("/users/2")
                .then()
                .statusCode(200)
                .log().body()
                .body("name", equalTo(name))
                .body("job", equalTo("leader"));
    }

    @Test
    public void deleteUser() {
        given()
                .log().uri()
                .when()
                .delete("/users/2")
                .then()
                .statusCode(204);
    }
}
