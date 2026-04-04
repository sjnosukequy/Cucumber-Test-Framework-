package org.example.cucumber.tests.api.register;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.example.cucumber.src.api.deleteAcc.deleteAccApi;
import org.example.cucumber.src.api.register.createAccApi;
import org.example.cucumber.src.models.object.user;
import org.example.cucumber.utils.loggerUtils;

import static org.example.cucumber.src.models.object.user.toUser;

import io.cucumber.java.After;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

public class verifyRegisterTestApi {
    Response response;
    user userObj;

    @When("I send a POST register request to {string} with form parameters {string}")
    public void i_send_a_post_register_request_to_with_form_parameters(String endpoint, String user) {
        // Implement the logic to send a POST request with the given parameters
        userObj = toUser(user);
        response = createAccApi.createAcc(userObj);
    }

    @After("@api and @register and not @donotCleanup")
    public void cleanupTestUsers() {
        Response response = deleteAccApi.deleteAcc(userObj.email, userObj.password);
        int actualStatusCode = response.jsonPath().getInt("responseCode");
        if (actualStatusCode == 200) {
            loggerUtils.appendLine("Cleanup successful for user: " + userObj.email);
        } else {
            loggerUtils.appendLine("*error*Cleanup failed for user: " + userObj.email);
        }
        loggerUtils.newLine();
    }

    @Then("the register response status code should be {int} and the register response message should be {string}")
    public void the_register_response_status_code_should_be(Integer expectedStatusCode, String expectedMessage) {
        // Implement the logic to verify the response status code
        int actualStatusCode = response.jsonPath().getInt("responseCode");
        String actualMessage = response.jsonPath().getString("message");

        // loggerUtils.logApiResponse(response.jsonPath().prettify());

        assertEquals(expectedStatusCode, actualStatusCode, "Expected status code does not match actual status code.");
        assertEquals(expectedMessage, actualMessage, "Expected message does not match actual message.");
    }

}
