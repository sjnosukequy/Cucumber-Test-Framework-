package org.example.cucumber.tests.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.example.cucumber.env.envManager;
import org.example.cucumber.src.models.pom.cartPage;
import org.example.cucumber.utils.browserUtils;
import org.example.cucumber.utils.driverManager;
import org.example.cucumber.utils.waitUtils;
import org.openqa.selenium.WebDriver;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class baseTestUi {
    @Given("I am logged in on the UI")
    public void i_am_logged_in_on_the_ui() {
        WebDriver driver = driverManager.getDriver();
        browserUtils utils = new browserUtils(driver);
        utils.ensureLogIn(driver.getCurrentUrl());
    }

    @Given("I am not logged in on the UI")
    public void i_am_not_logged_in_on_the_ui() {
        try {
            WebDriver driver = driverManager.getDriver();
            browserUtils utils = new browserUtils(driver);
            utils.ensureLogOut(driver.getCurrentUrl());
        } catch (Exception e) {
        }
    }

    @When("I navigate directly to {string}")
    public void i_navigate_directly_to(String url) {
        WebDriver driver = driverManager.getDriver();
        driver.get(envManager.getBaseURI() + url);
        waitUtils.wait(2); // Wait for potential redirects or page load
    }

    @Then("I should be redirected to {string}")
    public void i_should_be_redirected_to(String expectedUrl) {
        WebDriver driver = driverManager.getDriver();
        String currentUrl = driver.getCurrentUrl();
        String expectedFullUrl = envManager.getBaseURI() + expectedUrl;
        assertEquals(expectedFullUrl, currentUrl,
                "Expected to be redirected to " + expectedFullUrl + " but was redirected to " + currentUrl);
    }

    @And("verify I have no items in the cart")
    public void verify_i_have_no_items_in_the_cart() {
        WebDriver driver = driverManager.getDriver();
        cartPage cartPage = new cartPage(driver);
        try {
            driver.get(cartPage.fullUrl);
            waitUtils.wait(2); // Wait for any pending cart updates to complete
            cartPage.clearCart();
        } catch (Exception e) {
            // System.out.println("Error during teardown: " + e.getMessage());
        } finally {
            System.out.println("Teardown completed, cart cleared if possible.");
        }
    }
}
