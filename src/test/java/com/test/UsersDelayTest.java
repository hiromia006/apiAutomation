package com.test;

import com.test.pojo.UsersDelayData;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;

public class UsersDelayTest extends BaseApiTest {

    @Test
    public void testUsersDelay() {
        List<UsersDelayData> usersDelayDatas = given()
                .spec(requestSpecification())
                .log().uri()
                .when()
                .get("/users?delay=3")
                .then()
                .statusCode(200)
//                .log().body()
                .extract().jsonPath().getList("data", UsersDelayData.class);

        for (UsersDelayData usersDelayData : usersDelayDatas) {
//            System.out.println(usersDelayData.getEmail());
            Assert.assertTrue(usersDelayData.getEmail().contains("@") && usersDelayData.getEmail().endsWith("in"));
        }

        List<String> stringList= Arrays.asList("sakdljsa", "salkdjsla");

        for (String s:stringList){
            System.out.println(s);
        }

//        for (int i=0;i<usersDelayDatas.size();i++){
//            System.out.println(usersDelayDatas.get(i).getEmail());
//        }
    }
}
