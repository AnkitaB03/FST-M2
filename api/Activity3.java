package activities;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.config.RestAssuredConfig;
import io.restassured.filter.Filter;
import io.restassured.http.*;
import io.restassured.mapper.ObjectMapper;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import io.restassured.specification.*;
import org.junit.runner.Request;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.security.KeyStore;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class Activity3 {

    RequestSpecification requestSpec;
    ResponseSpecification responseSpec;
    
    @BeforeClass
    public void setUp() {
        requestSpec = new RequestSpecBuilder()
                .setBaseUri("https://petstore.swagger.io/v2/pet")
                .setContentType("application/json")
                .build();

        responseSpec = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType("application/json")
                .expectBody("status",equalTo("alive"))
                .build();
    }

    @DataProvider
    public Object[][] petInfo(){
        Object[][] testData = new Object[][]{
                {1234,"Anki","alive"},
                {1235,"Ankita","alive"}
        };

        return testData;
    }

    @Test(priority = 1)
    public void addPet(){
        String reqBody = "{\"id\": 1234, \"name\": \"Anki\", \"status\": \"alive\"}";
        Response response = given().spec(requestSpec)
                .when().body(reqBody)
                .post();

         reqBody = "{\"id\": 1235, \"name\": \"Ankita\", \"status\": \"alive\"}";
         response = given().spec(requestSpec)
                .when().body(reqBody)
                .post();

         response.then().spec(responseSpec);

    }
    @Test(dataProvider = "petInfo",priority = 2)
    public void getPet(int id, String name, String status){
        Response response = given().spec(requestSpec)
                .pathParam("petId",id)
                .when().get("/{petId}");

        System.out.println(response.getBody().asPrettyString());
        response.then()
        .spec(responseSpec)
        .body("name",equalTo(name));


    }
    @Test(dataProvider = "petInfo",priority = 3)
    public void deletePet(int id,String name,String status){
        Response response = given().spec(requestSpec)
                .pathParam("petId",id)
                .when().delete("/{petId}");

        response.then().body("code",equalTo(200));

    }
}
