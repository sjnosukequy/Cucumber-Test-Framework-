package org.example.cucumber.tests.ui.register;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;

import org.example.cucumber.src.api.deleteAcc.deleteAccApi;
import org.example.cucumber.src.models.pom.accountCreatedPage;
import org.example.cucumber.src.models.pom.registerPage;
import org.example.cucumber.utils.browserUtils;
import org.example.cucumber.utils.driverManager;
import org.example.cucumber.utils.emptyUtils;
import org.example.cucumber.utils.waitUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import io.cucumber.java.After;
import io.cucumber.java.en.And;

public class verifyRegisterTestUi {
    String email;
    String password;
    HashMap<String, String> validationMessage = new HashMap<>();

    @After("@ui and @registration")
    public void tearDown() {
        WebDriver driver = driverManager.getDriver();
        browserUtils browserUtils = new browserUtils(driver);
        validationMessage.clear();

        try {
            browserUtils.ensureLogOut(driver.getCurrentUrl());
        } catch (Exception e) {
        } finally {
            System.out.println("Teardown completed, user logged out if possible.");
        }
    }

    @After("@ui and @registration and not @donotCleanup")
    public void deleteAccount() {
        try {
            deleteAccApi.deleteAcc(email, password);
        } catch (Exception e) {
        } finally {
            System.out.println(String.format("Teardown completed, user deleted %s if possible.", email));
            email = null;
            password = null;
        }
    }

    @And("I enter {string} into the signup Name field")
    public void i_enter_into_the_signup_name_field(String name) {
        WebDriver driver = driverManager.getDriver();
        browserUtils browserUtils = new browserUtils(driver);
        registerPage page = new registerPage(driver);
        WebElement nameField = page.getNameField();
        nameField.sendKeys(name);
        String message = browserUtils.getValidationMessage(nameField);
        if (!emptyUtils.isEmpty(message)) {
            validationMessage.put("name", message);
        }
    }

    @And("I enter {string} into the signup Email field")
    public void i_enter_into_the_signup_email_field(String email) {
        this.email = email;
        WebDriver driver = driverManager.getDriver();
        browserUtils browserUtils = new browserUtils(driver);
        registerPage page = new registerPage(driver);
        WebElement emailField = page.getEmailField();
        emailField.sendKeys(email);
        String message = browserUtils.getValidationMessage(emailField);
        if (!emptyUtils.isEmpty(message)) {
            validationMessage.put("email", message);
        }
    }

    @And("I click the Signup button")
    public void i_click_the_signup_button() {
        WebDriver driver = driverManager.getDriver();
        registerPage page = new registerPage(driver);
        page.getSignupButton().click();
        waitUtils.wait(2);
    }

    @And("a signup email error message should be displayed")
    public void a_signup_email_error_message_should_be_displayed() {
        WebDriver driver = driverManager.getDriver();
        registerPage page = new registerPage(driver);
        try {
            String expectedMessage = "Email Address already exist!";
            WebElement errorMessage = page.getValidationError();
            assertEquals(true, errorMessage.isDisplayed(),
                    "Expected an error message to be displayed, but it was not found.");
            assertEquals(expectedMessage, errorMessage.getText(), "Expected error message text does not match actual.");
        } catch (Exception e) {
            throw new AssertionError("Expected an error message to be displayed, but it was not found.");
        }
    }

    @And("validation messages should be displayed for register email field")
    public void validation_messages_should_be_displayed_for_register_email_field() {
        assertEquals(true, !validationMessage.isEmpty(),
                "Expected validation messages for required register fields, but no messages were found "
                        + validationMessage);
    }

    @And("I enter {string} into the signup Password field")
    public void i_enter_into_the_signup_password_field(String password) {
        this.password = password;
        WebDriver driver = driverManager.getDriver();
        browserUtils browserUtils = new browserUtils(driver);
        registerPage page = new registerPage(driver);
        WebElement passwordField = page.getPasswordField();
        passwordField.sendKeys(password);
        String message = browserUtils.getValidationMessage(passwordField);
        if (!emptyUtils.isEmpty(message)) {
            validationMessage.put("password", message);
        }
    }

    @And("I enter {string} into the signup First Name field")
    public void i_enter_into_the_signup_first_name_field(String firstName) {
        WebDriver driver = driverManager.getDriver();
        browserUtils browserUtils = new browserUtils(driver);
        registerPage page = new registerPage(driver);
        WebElement firstNameField = page.getFNameField();
        firstNameField.sendKeys(firstName);
        String message = browserUtils.getValidationMessage(firstNameField);
        if (!emptyUtils.isEmpty(message)) {
            validationMessage.put("firstName", message);
        }
    }

    @And("I enter {string} into the signup Last Name field")
    public void i_enter_into_the_signup_last_name_field(String lastName) {
        WebDriver driver = driverManager.getDriver();
        browserUtils browserUtils = new browserUtils(driver);
        registerPage page = new registerPage(driver);
        WebElement lastNameField = page.getLNameField();
        lastNameField.sendKeys(lastName);
        String message = browserUtils.getValidationMessage(lastNameField);
        if (!emptyUtils.isEmpty(message)) {
            validationMessage.put("lastName", message);
        }
    }

    @And("I enter {string} into the signup Address Line 1 field")
    public void i_enter_into_the_signup_address_line_1_field(String address1) {
        WebDriver driver = driverManager.getDriver();
        browserUtils browserUtils = new browserUtils(driver);
        registerPage page = new registerPage(driver);
        WebElement address1Field = page.getAdd1Field();
        address1Field.sendKeys(address1);
        String message = browserUtils.getValidationMessage(address1Field);
        if (!emptyUtils.isEmpty(message)) {
            validationMessage.put("address1", message);
        }
    }

    @And("I enter {string} into the signup Address Line 2 field")
    public void i_enter_into_the_signup_address_line_2_field(String address2) {
        WebDriver driver = driverManager.getDriver();
        browserUtils browserUtils = new browserUtils(driver);
        registerPage page = new registerPage(driver);
        WebElement address2Field = page.getAdd2Field();
        address2Field.sendKeys(address2);
        String message = browserUtils.getValidationMessage(address2Field);
        if (!emptyUtils.isEmpty(message)) {
            validationMessage.put("address2", message);
        }
    }

    @And("I enter {string} into the signup State field")
    public void i_enter_into_the_signup_state_field(String state) {
        WebDriver driver = driverManager.getDriver();
        browserUtils browserUtils = new browserUtils(driver);
        registerPage page = new registerPage(driver);
        WebElement stateField = page.getStateField();
        stateField.sendKeys(state);
        String message = browserUtils.getValidationMessage(stateField);
        if (!emptyUtils.isEmpty(message)) {
            validationMessage.put("state", message);
        }
    }

    @And("I enter {string} into the signup City field")
    public void i_enter_into_the_signup_city_field(String city) {
        WebDriver driver = driverManager.getDriver();
        browserUtils browserUtils = new browserUtils(driver);
        registerPage page = new registerPage(driver);
        WebElement cityField = page.getCityField();
        cityField.sendKeys(city);
        String message = browserUtils.getValidationMessage(cityField);
        if (!emptyUtils.isEmpty(message)) {
            validationMessage.put("city", message);
        }
    }

    @And("I enter {string} into the signup Zipcode field")
    public void i_enter_into_the_signup_zipcode_field(String zipcode) {
        WebDriver driver = driverManager.getDriver();
        browserUtils browserUtils = new browserUtils(driver);
        registerPage page = new registerPage(driver);
        WebElement zipcodeField = page.getZipcodeField();
        zipcodeField.sendKeys(zipcode);
        String message = browserUtils.getValidationMessage(zipcodeField);
        if (!emptyUtils.isEmpty(message)) {
            validationMessage.put("zipcode", message);
        }
    }

    @And("I enter {string} into the signup Mobile Number field")
    public void i_enter_into_the_signup_mobile_number_field(String mobile) {
        WebDriver driver = driverManager.getDriver();
        browserUtils browserUtils = new browserUtils(driver);
        registerPage page = new registerPage(driver);
        WebElement mobileField = page.getMobileField();
        mobileField.sendKeys(mobile);
        String message = browserUtils.getValidationMessage(mobileField);
        if (!emptyUtils.isEmpty(message)) {
            validationMessage.put("mobile", message);
        }
    }

    @And("I click the Create Account button")
    public void i_click_the_create_account_button() {
        WebDriver driver = driverManager.getDriver();
        registerPage page = new registerPage(driver);
        WebElement createAccountButton = page.getCreateAccountButton();
        createAccountButton.click();
    }

    @And("I click the continue button on the account created page")
    public void i_click_the_continue_button_on_the_account_created_page() {
        WebDriver driver = driverManager.getDriver();
        accountCreatedPage page = new accountCreatedPage(driver);
        WebElement continueButton = page.getContinueButton();
        continueButton.click();
    }
}
