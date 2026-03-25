package org.example.cucumber.tests.api.login;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.example.cucumber.src.api.login.verifyLoginApi;
import org.example.cucumber.utils.loggerUtils;

public class verifyLoginTest {
    Response response;
    @When("I send a POST request to {string} with form parameters {string} and {string}")
    public void i_send_a_post_request_to_with_form_parameters(String endpoint, String email, String password) {
        // Implement the logic to send a POST request with the given parameters
        response = verifyLoginApi.verifyApi(email, password);
    }

    @Then("the response status code should be {int}")
    public void the_response_status_code_should_be(Integer expectedStatusCode) {
        // Implement the logic to verify the response status code
        int actualStatusCode = response.jsonPath().getInt("responseCode");
        loggerUtils.logApiStatus(expectedStatusCode, actualStatusCode);
        assertEquals(actualStatusCode, expectedStatusCode);
    }
    
    @And("the response message should be {string}")
    public void the_response_message_should_be(String expectedMessage) {
        // Implement the logic to verify the response contains the expected message
        String actualMessage = response.jsonPath().getString("message");
        loggerUtils.logApiMessage(expectedMessage, actualMessage);
        assertEquals(actualMessage, expectedMessage);
    }
}
