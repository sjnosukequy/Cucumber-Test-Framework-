package org.example.cucumber.tests.ui.login;

import org.example.cucumber.utils.browserUtils;
import org.example.cucumber.utils.driverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import io.cucumber.java.After;
import io.cucumber.java.en.And;
// import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

// import static org.example.cucumber.utils.loggerUtils.LOGGER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.example.cucumber.src.models.pom.loginPage;
import org.example.cucumber.utils.loggerUtils;

public class verifyLoginTestUi {
    @After("@ui and @login")
    public void tearDown() {
        try {
            WebDriver driver = driverManager.getDriver();
            loginPage page = new loginPage(driver);
            page.getLogoutHeader().click();
        } catch (Exception e) {
        }
    }

    @And("I am on the login page")
    public void i_am_on_the_login_page() {
        WebDriver driver = driverManager.getDriver();
        loginPage page = new loginPage(driver);
        driver.get(page.fullUrl);
    }

    @When("I enter {string} into the login Email Address field")
    public void i_enter_email_into_the_login_email_address_field(String email) {
        loginPage page = new loginPage(driverManager.getDriver());
        page.getEmailField().sendKeys(email);
    }

    @And("I enter {string} into the login Password field")
    public void i_enter_password_into_the_login_password_field(String password) {
        loginPage page = new loginPage(driverManager.getDriver());
        page.getPasswordField().sendKeys(password);
    }

    @And("I click the Login button")
    public void i_click_the_login_button() {
        loginPage page = new loginPage(driverManager.getDriver());
        page.getLoginButton().click();
    }

    @Then("the login should be successful")
    public void the_login_should_be_successful() {
        loginPage page = new loginPage(driverManager.getDriver());
        boolean isLoginSuccessful = true;
        try {
            WebElement errorMessage = page.getErrorMessage();
            if (errorMessage.isDisplayed()) {
                loggerUtils.appendLine("Logging failed with message: " + errorMessage.getText());
                isLoginSuccessful = false;
            }
        } catch (Exception e) {

        }

        assertEquals(true, isLoginSuccessful, "Expected login to be successful, but an error message was displayed.");
    }

    @Then("an error message should be displayed")
    public void an_error_message_should_be_displayed() {
        boolean isLoginSuccessful = true;
        loginPage page = new loginPage(driverManager.getDriver());
        try {
            WebElement errorMessage = page.getErrorMessage();
            if (errorMessage.isDisplayed()) {
                loggerUtils.appendLine("Logging failed with message: " + errorMessage.getText());
                isLoginSuccessful = false;
            }
        } catch (Exception e) {

        }
        assertEquals(false, isLoginSuccessful, "Expected an error message to be displayed, but none was found.");
    }

    @Then("an error message should be displayed at login password field {string}")
    public void an_error_message_should_be_displayed_at_login_the_password_field(String message) {
        WebDriver driver = driverManager.getDriver();
        loginPage page = new loginPage(driver);
        browserUtils browserUtils = new browserUtils(driver);

        String validationMessage = browserUtils.getValidationMessage(page.getPasswordField());
        // loggerUtils.appendLine("Validation message at password field: " +
        // validationMessage);
        assertEquals(message, validationMessage, "Expected validation message at password field to be: " + message);
    }

    @Then("an error message should be displayed at login email field {string}")
    public void an_error_message_should_be_displayed_at_login_the_email_field(String message) {
        WebDriver driver = driverManager.getDriver();
        loginPage page = new loginPage(driver);
        browserUtils browserUtils = new browserUtils(driver);

        String validationMessage = browserUtils.getValidationMessage(page.getEmailField());
        // loggerUtils.appendLine("Validation message at email field: " +
        // validationMessage);
        if (!validationMessage.contains(message)) {
            throw new AssertionError(
                    "Expected validation message to contain: " + message + " but got: " + validationMessage);
        }
    }

    @And("I should be redirected to the homepage")
    public void i_should_be_redirected_to_the_homepage() {
        WebDriver driver = driverManager.getDriver();
        loginPage page = new loginPage(driver);

        String currentUrl = driver.getCurrentUrl();
        loggerUtils.appendLine("Current URL after login: " + currentUrl);
        assertEquals(currentUrl, page.homepageUrl, "Expected to be redirected to homepage URL: " + page.homepageUrl);
        // Implement the logic to verify that the user is redirected to the homepage
        // This could involve checking the URL, a specific element on the homepage, etc.
    }

}
