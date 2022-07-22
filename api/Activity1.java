package activities;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import static org.hamcrest.Matchers.equalTo;

import static io.restassured.RestAssured.given;

public class Activity1 {

    String baseURI = "https://petstore.swagger.io/v2/pet";

    @Test(priority = 2)
    public void getRequestOnPetTest(){
        Response response = given()
                .contentType(ContentType.JSON)
                .pathParam("petId", "77232")
        .when()
                .get(baseURI + "/{petId}");
        response.then()
                .statusCode(200)
                .body("id", equalTo(77232))
                .body("name",equalTo("Riley"))
                .body("status",equalTo("alive"));



    }

    @Test(priority = 1)
    public void postRequestOnPetTest() throws IOException {
        String reqBody = "{"
                + "\"id\": 77232,"
                + "\"name\": \"Riley\","
                + " \"status\": \"alive\""
                + "}";

        Response response = given()
                .contentType(ContentType.JSON)
                .body(reqBody)
                .when()
                .post(baseURI);

       response.then()
               .statusCode(200)
               .body("id", equalTo(77232))
               .body("name",equalTo("Riley"))
               .body("status",equalTo("alive"));
    }

    @Test(priority = 3)
    public void deleteRequestOnPetTest(){
        Response response = given()
                .contentType(ContentType.JSON)
                .pathParam("petId", "77232")
                .when()
                .delete(baseURI + "/{petId}");

        response.then().statusCode(200)
                .body("message",equalTo("77232"));

    }
}
