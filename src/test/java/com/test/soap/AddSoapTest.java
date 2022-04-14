package com.test.soap;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;


import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class AddSoapTest {

    @Test
    public void addTwoNumberShouldSucceed() {
        RestAssured.baseURI = "http://www.dneonline.com";

        String soapStr = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                "  <soap:Body>\n" +
                "    <Add xmlns=\"http://tempuri.org/\">\n" +
                "      <intA>10</intA>\n" +
                "      <intB>20</intB>\n" +
                "    </Add>\n" +
                "  </soap:Body>\n" +
                "</soap:Envelope>";

        given()
                .contentType("text/xml")
                .accept(ContentType.XML)
                .body(soapStr)
                .log().uri()
                .when()
                .post("/calculator.asmx")
                .then()
                .statusCode(200)
                .log().body()
                .body("//AddResult.text()", equalTo("30"));
    }
}
