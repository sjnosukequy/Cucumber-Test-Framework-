package org.example.cucumber.src.api.register;

import static io.restassured.RestAssured.given;

import java.util.Map;

import org.example.cucumber.src.api.baseApi;
import org.example.cucumber.src.models.object.user;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class createAccApi extends baseApi {
    static public Response createAcc(user user) {
        RequestSpecification request = given();
        Map<String, String> userMap = user.toMap();

        for (Map.Entry<String, String> entry : userMap.entrySet()) {
            request.multiPart(entry.getKey(), entry.getValue());

            // if (!emptyUtils.isEmpty(entry.getValue())) {
            //     request.multiPart(entry.getKey(), entry.getValue());
            // }
        }

        Response response = request
                .when()
                .post("/api/createAccount")
                .then()
                .extract().response();
        return response;
    }
}
