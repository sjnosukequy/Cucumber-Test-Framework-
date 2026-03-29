package org.example.cucumber.tests.api.login;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.example.cucumber.src.api.login.verifyLoginApi;
// import org.example.cucumber.utils.loggerUtils;

public class verifyLoginTestApi {
    Response response;
    
    @When("I send a POST login request to {string} with form parameters {string} and {string}")
    public void i_send_a_post_request_to_with_form_parameters(String endpoint, String email, String password) {
        // Implement the logic to send a POST request with the given parameters
        response = verifyLoginApi.verifyApi(email, password);
    }

    @Then("the login response status code should be {int} and the login response message should be {string}")
    public void the_response_status_code_should_be(Integer expectedStatusCode, String expectedMessage) {
        // Implement the logic to verify the response status code
        int actualStatusCode = response.jsonPath().getInt("responseCode");
        String actualMessage = response.jsonPath().getString("message");

        // loggerUtils.logApiResponse(response.jsonPath().prettify());

        assertEquals(actualStatusCode, expectedStatusCode, "Expected status code does not match actual status code");
        assertEquals(actualMessage, expectedMessage, "Expected message does not match actual message");

    }
    
}
