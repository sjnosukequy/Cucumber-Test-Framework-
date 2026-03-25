package org.example.cucumber.src.api.login;

import static io.restassured.RestAssured.given;

import org.example.cucumber.src.api.baseApi;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.example.cucumber.utils.emptyUtils;

public class verifyLoginApi extends baseApi {
    static public Response verifyApi(String email, String password) {
        RequestSpecification request = given();
        if (!emptyUtils.isEmpty(email)) {
            request.multiPart("email", email);
        }
        if (!emptyUtils.isEmpty(password)) {
            request.multiPart("password", password);
        }

        Response response = request
                .when()
                .post("/api/verifyLogin")
                .then()
                .extract().response();
        return response;
    }
}
