package activities;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class Activity2 {

    String baseURI = "https://petstore.swagger.io/v2/user";

    @Test(priority = 2)
    public void getRequestOnPetTest(){
        File file = new File("src/test/resources/userGetResponse.json");
        Response response = given()
                .contentType(ContentType.JSON)
                .pathParam("username", "ankitab")
                .when()
                .get(baseURI + "/{username}");

        String resBody = response.getBody().asPrettyString();

        try {
            file.createNewFile();
            FileWriter writer = new FileWriter(file.getPath());
            writer.write(resBody);
            writer.close();
        } catch (IOException excp) {
            excp.printStackTrace();
        }

        response.then()
                .statusCode(200)
                .body("id", equalTo(7654))
                .body("username",equalTo("ankitab"))
                .body("firstName",equalTo("ankita"))
                .body("lastName",equalTo("bhargava"))
                .body("email",equalTo("ankitabhargava@mail.com"))
                .body("password",equalTo("password123"))
                .body("phone",equalTo("9812763451"));




    }

    @Test(priority = 1)
    public void postRequestOnPetTest() throws IOException {
        File file = new File("src/test/resources/input.json");
        FileInputStream inputjson = new FileInputStream(file);
        byte[] bytes = new byte[(int) file.length()];
        inputjson.read(bytes);
        String reqBody = new String(bytes);

        Response response = given()
                .contentType(ContentType.JSON)
                .body(reqBody)
                .when()
                .post(baseURI);

        inputjson.close();

        response.then()
                .statusCode(200)
                .body("message", equalTo("7654"));
    }

    @Test(priority = 3)
    public void deleteRequestOnPetTest(){
        Response response = given()
                .contentType(ContentType.JSON)
                .pathParam("username", "ankitab")
                .when()
                .delete(baseURI + "/{username}");

        response.then().statusCode(200)
                .body("message",equalTo("ankitab"));

    }
}
