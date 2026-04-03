package org.example.cucumber.src.api.user;
import static io.restassured.RestAssured.given;

import org.example.cucumber.src.api.baseApi;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class getUserDetailsApi extends baseApi{
    public static Response getUserDetails(String email) {
        RequestSpecification request = given();
        request.queryParam("email", email);

        Response response = request
                .when()
                .get("/api/getUserDetailByEmail")
                .then()
                .extract().response();
        return response;
    }
}