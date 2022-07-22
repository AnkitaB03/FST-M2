package liveProject;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class GitHubProject {

    RequestSpecification requestSpec;
    int id;
    String sshKey = "ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABAQCQXgbyDyk2CKG2c9uuTXqhKIgjECr0M+RPiWfzy8oqPD0QybhDLdz45o+PzPw/8aH7erxQxzKOBCxQHQClbqdh8d0F5+YfyZ1MAMv7TpiR7IfZRyukkchbsm5M6GHa8W+39ia351LOPCT6m1mAYrMYPRi92Um8e89eDRFfu0z/1JiqG/YWCZZSbcLo9D1yi+hfgG8oEB5vHOvyKKwDrv660ZpcYswV8ubUBnBSt4YQBUfOEB4bv8E2cT+JXgeYxUgqZKO0HCBo8Fyf5cR+Bn8BtlZopFUgQ870aRDPVDoyNKQLdiYGuLc8EOKbtJRYP65r9D+PMlRSCHLfrOES/omP";
    String access_token = "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";

    @BeforeClass
    public void setUp(){
        requestSpec = new RequestSpecBuilder()
                .setBaseUri("https://api.github.com")
                .addHeader("Content-Type","application/json")
                .addHeader("Authorization","Bearer " +access_token)
                .build();
    }

    @Test(priority = 1)
    public void addSSHKey(){
        Map<String,Object> reqBody = new HashMap<String,Object>();
        reqBody.put("title", "TestAPIKey");
        reqBody.put("key",sshKey);
        Response response = given().spec(requestSpec)
                .body(reqBody)
                .when().post("/user/keys");
        System.out.println(response.getBody().asPrettyString());
        id = response.then().extract().path("id");
        response.then().statusCode(201);

    }

    @Test(priority = 2)
    public void getSSHKey(){
        Response response = given().spec(requestSpec)
                .pathParams("keyId",id)
                .when().get("/user/keys/{keyId}");
        Reporter.log(response.prettyPrint());
        response.then().statusCode(200);

    }

    @Test(priority = 3)
    public void deleteSSHKey(){
        Response response = given().spec(requestSpec)
                .pathParams("keyId",id)
                .when().delete("/user/keys/{keyId}");
        Reporter.log(response.prettyPrint());
        response.then().statusCode(204);

    }

}
