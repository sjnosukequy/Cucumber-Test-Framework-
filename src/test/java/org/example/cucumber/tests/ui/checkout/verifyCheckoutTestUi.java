package org.example.cucumber.tests.ui.checkout;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;

import org.example.cucumber.src.api.user.getUserDetailsApi;
import org.example.cucumber.src.models.constants.routes;
import org.example.cucumber.src.models.object.credential;
import org.example.cucumber.src.models.pom.cartPage;
import org.example.cucumber.src.models.pom.checkoutPage;
import org.example.cucumber.src.models.pom.paymentPage;
import org.example.cucumber.utils.accountRotation;
import org.example.cucumber.utils.browserUtils;
import org.example.cucumber.utils.driverManager;
import org.example.cucumber.utils.emptyUtils;
import org.example.cucumber.utils.waitUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class verifyCheckoutTestUi {
    HashMap<String, String> validationMessage = new HashMap<>();

    @After("@ui and @checkout")
    public void tearDown() {
        validationMessage.clear();
        WebDriver driver = driverManager.getDriver();
        browserUtils browserUtils = new browserUtils(driver);
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

        try {
            browserUtils.ensureLogOut(driver.getCurrentUrl());
        } catch (Exception e) {
        } finally {
            System.out.println("Teardown completed, user logged out if possible.");
        }
    }

    @Then("I am on the checkout page")
    public void iAmOnTheCheckoutPage() {
        WebDriver driver = driverManager.getDriver();
        checkoutPage page = new checkoutPage(driver);
        driver.get(page.fullUrl);
    }

    @Then("the checkout page should load successfully")
    public void theCheckoutPageShouldLoadSuccessfully() {
        WebDriver driver = driverManager.getDriver();
        checkoutPage checkoutPage = new checkoutPage(driver);
        String currentUrl = driver.getCurrentUrl();
        assertEquals(currentUrl, checkoutPage.baseUrl + routes.checkout.path,
                "Expected to navigate to checkout page, but navigated to: " + currentUrl);
    }

    @And("the Delivery Address section should be visible")
    public void theDeliveryAddressSectionShouldBeVisible() {
        WebDriver driver = driverManager.getDriver();
        checkoutPage checkoutPage = new checkoutPage(driver);
        WebElement deliveryAddressSection = checkoutPage.getAddressDeliverySection();
        assertEquals(true, deliveryAddressSection.isDisplayed(), "Expected Delivery Address section to be visible");
    }

    @And("the Billing Address section should be visible")
    public void theBillingAddressSectionShouldBeVisible() {
        WebDriver driver = driverManager.getDriver();
        checkoutPage checkoutPage = new checkoutPage(driver);
        WebElement billingAddressSection = checkoutPage.getAddressBillingSection();
        assertEquals(true, billingAddressSection.isDisplayed(), "Expected Billing Address section to be visible");
    }

    @And("the comment area should be visible")
    public void theCommentAreaShouldBeVisible() {
        WebDriver driver = driverManager.getDriver();
        checkoutPage checkoutPage = new checkoutPage(driver);
        WebElement commentArea = checkoutPage.getOrderMessage();
        assertEquals(true, commentArea.isDisplayed(), "Expected comment area to be visible");
    }

    @And("the order summary should be visible")
    public void theOrderSummaryShouldBeVisible() {
        WebDriver driver = driverManager.getDriver();
        checkoutPage checkoutPage = new checkoutPage(driver);
        WebElement orderSummary = checkoutPage.getCartTable();
        assertEquals(true, orderSummary.isDisplayed(), "Expected order summary to be visible");
    }

    @And("the Place Order button should be visible")
    public void thePlaceOrderButtonShouldBeVisible() {
        WebDriver driver = driverManager.getDriver();
        checkoutPage checkoutPage = new checkoutPage(driver);
        WebElement placeOrderButton = checkoutPage.getPlaceOrderButton();
        assertEquals(true, placeOrderButton.isDisplayed(), "Expected Place Order button to be visible");
    }

    @When("the Delivery Address section should the correct display profile data")
    public void theDeliveryAddressSectionShouldDisplayProfileData() {
        WebDriver driver = driverManager.getDriver();
        checkoutPage checkout = new checkoutPage(driver);
        credential currentCredential = accountRotation.getCurrent();
        Response response = getUserDetailsApi.getUserDetails(currentCredential.email);
        JsonPath jsonPath = response.jsonPath();
        // System.out.println("Raw API response: " + jsonPath.prettify());
        WebElement deliveryAddressSection = checkout.getAddressDeliverySection();

        String displayedName = checkout.getAddressNameHelper(deliveryAddressSection).getText();
        String expectedFName = jsonPath.getString("user.first_name");
        String expectedLName = jsonPath.getString("user.last_name");
        String expectedFullName = expectedFName + " " + expectedLName;
        assertEquals(true, displayedName.contains(expectedFullName),
                "Expected delivery address full name to match profile data");

        String displayedCompany = checkout.getAddressCompanyHelper(deliveryAddressSection).getText();
        String expectedCompany = jsonPath.getString("user.company");
        assertEquals(expectedCompany, displayedCompany, "Expected delivery address company to match profile data");

        String displayedAddress1 = checkout.getAddress1Helper(deliveryAddressSection).getText();
        String expectedAddress1 = jsonPath.getString("user.address1");
        assertEquals(expectedAddress1, displayedAddress1, "Expected delivery address line 1 to match profile data");

        String displayedAddress2 = checkout.getAddress2Helper(deliveryAddressSection).getText();
        String expectedAddress2 = jsonPath.getString("user.address2");
        assertEquals(expectedAddress2, displayedAddress2, "Expected delivery address line 2 to match profile data");

        String displayedCity = checkout.getAddressCityHelper(deliveryAddressSection).getText();
        String expectedCity = jsonPath.getString("user.city");
        String expectedState = jsonPath.getString("user.state");
        String expectedZipcode = jsonPath.getString("user.zipcode");
        String expectedCityStateZip = expectedCity + " " + expectedState + " " + expectedZipcode;

        assertEquals(expectedCityStateZip, displayedCity,
                "Expected delivery address city/state/zip to match profile data");

        String displayedCountry = checkout.getAddressCountryHelper(deliveryAddressSection).getText();
        String expectedCountry = jsonPath.getString("user.country");
        assertEquals(expectedCountry, displayedCountry, "Delivery address country does not match profile data");
    }

    @And("the Billing Address section should the correct display profile data")
    public void theBillingAddressSectionShouldDisplayProfileData() {
        WebDriver driver = driverManager.getDriver();
        checkoutPage checkout = new checkoutPage(driver);
        credential currentCredential = accountRotation.getCurrent();
        Response response = getUserDetailsApi.getUserDetails(currentCredential.email);
        JsonPath jsonPath = response.jsonPath();
        WebElement billingAddressSection = checkout.getAddressBillingSection();

        String displayedName = checkout.getAddressNameHelper(billingAddressSection).getText();
        String expectedFName = jsonPath.getString("user.first_name");
        String expectedLName = jsonPath.getString("user.last_name");
        String expectedFullName = expectedFName + " " + expectedLName;
        assertEquals(true, displayedName.contains(expectedFullName),
                "Billing address full name does not match profile data");

        String displayedCompany = checkout.getAddressCompanyHelper(billingAddressSection).getText();
        String expectedCompany = jsonPath.getString("user.company");
        assertEquals(expectedCompany, displayedCompany, "Billing address company does not match profile data");

        String displayedAddress1 = checkout.getAddress1Helper(billingAddressSection).getText();
        String expectedAddress1 = jsonPath.getString("user.address1");
        assertEquals(expectedAddress1, displayedAddress1, "Billing address line 1 does not match profile data");

        String displayedAddress2 = checkout.getAddress2Helper(billingAddressSection).getText();
        String expectedAddress2 = jsonPath.getString("user.address2");
        assertEquals(expectedAddress2, displayedAddress2, "Billing address line 2 does not match profile data");

        String displayedCity = checkout.getAddressCityHelper(billingAddressSection).getText();
        String expectedCity = jsonPath.getString("user.city");
        String expectedState = jsonPath.getString("user.state");
        String expectedZipcode = jsonPath.getString("user.zipcode");
        String expectedCityStateZip = expectedCity + " " + expectedState + " " + expectedZipcode;
        assertEquals(expectedCityStateZip, displayedCity, "Billing address city/state/zip does not match profile data");

        String displayedCountry = checkout.getAddressCountryHelper(billingAddressSection).getText();
        String expectedCountry = jsonPath.getString("user.country");
        assertEquals(expectedCountry, displayedCountry, "Billing address country does not match profile data");
    }

    @When("I enter {string} into the order comment field it should appear correctly in the text box")
    public void iEnterIntoTheOrderCommentFieldItShouldAppearCorrectlyInTheTextBox(String comment) {
        WebDriver driver = driverManager.getDriver();
        browserUtils browserUtils = new browserUtils(driver);
        checkoutPage checkout = new checkoutPage(driver);
        WebElement commentArea = checkout.getOrderMessage();
        commentArea.clear();
        waitUtils.wait(1); // Wait for the text to be entered into the box
        commentArea.sendKeys(comment);
        waitUtils.wait(1); // Wait for the text to be entered into the box
        String actualComment = browserUtils.getTextValue(commentArea);
        waitUtils.wait(1); // Wait for the text to be entered into the box
        assertEquals(comment, actualComment,
                "The comment entered into the order comment field does not match the expected comment");
    }

    @And("I click the Place Order button")
    public void iClickThePlaceOrderButton() {
        WebDriver driver = driverManager.getDriver();
        checkoutPage checkout = new checkoutPage(driver);
        WebElement placeOrderButton = checkout.getPlaceOrderButton();
        placeOrderButton.click();
        waitUtils.wait(2); // Wait for the page to navigate to the payment details page
    }

    @And("I enter {string} into the Name on Card field")
    public void iEnterIntoTheNameOnCardField(String nameOnCard) {
        WebDriver driver = driverManager.getDriver();
        paymentPage payment = new paymentPage(driver);
        WebElement nameOnCardField = payment.getCardNameInput();
        browserUtils browserUtils = new browserUtils(driver);
        nameOnCardField.clear();
        waitUtils.wait(1); // Wait for the text to be entered into the box
        nameOnCardField.sendKeys(nameOnCard);
        waitUtils.wait(1); // Wait for the text to be entered into the box
        String actualNameOnCard = browserUtils.getTextValue(nameOnCardField);
        waitUtils.wait(1); // Wait for the text to be entered into the box
        assertEquals(nameOnCard, actualNameOnCard,
                "The name entered into the Name on Card field does not match the expected name");
        String message = browserUtils.getValidationMessage(nameOnCardField);
        if (!emptyUtils.isEmpty(message)) {
            validationMessage.put("nameOnCard", message);
        }
    }

    @And("I enter {string} into the Card Number field")
    public void iEnterIntoTheCardNumberField(String cardNumber) {
        WebDriver driver = driverManager.getDriver();
        paymentPage payment = new paymentPage(driver);
        WebElement cardNumberField = payment.getCardNumberInput();
        browserUtils browserUtils = new browserUtils(driver);
        cardNumberField.clear();
        waitUtils.wait(1); // Wait for the text to be entered into the box
        cardNumberField.sendKeys(cardNumber);
        waitUtils.wait(1); // Wait for the text to be entered into the box
        String actualCardNumber = browserUtils.getTextValue(cardNumberField);
        waitUtils.wait(1); // Wait for the text to be entered into the box
        assertEquals(cardNumber, actualCardNumber,
                "The card number entered into the Card Number field does not match the expected card number");
        String message = browserUtils.getValidationMessage(cardNumberField);
        if (!emptyUtils.isEmpty(message)) {
            validationMessage.put("cardNumber", message);
        }
    }

    @And("I enter {string} into the CVC field")
    public void iEnterIntoTheCVCField(String cvc) {
        WebDriver driver = driverManager.getDriver();
        paymentPage payment = new paymentPage(driver);
        WebElement cvcField = payment.getCvcInput();
        browserUtils browserUtils = new browserUtils(driver);
        cvcField.clear();
        waitUtils.wait(1); // Wait for the text to be entered into the box
        cvcField.sendKeys(cvc);
        waitUtils.wait(1); // Wait for the text to be entered into the box
        String actualCvc = browserUtils.getTextValue(cvcField);
        waitUtils.wait(1); // Wait for the text to be entered into the box
        assertEquals(cvc, actualCvc,
                "The CVC entered into the CVC field does not match the expected CVC");
        String message = browserUtils.getValidationMessage(cvcField);
        if (!emptyUtils.isEmpty(message)) {
            validationMessage.put("cvc", message);
        }
    }

    @And("I enter {string} into the Expiration Month field")
    public void iEnterIntoTheExpirationMonthField(String expirationMonth) {
        WebDriver driver = driverManager.getDriver();
        paymentPage payment = new paymentPage(driver);
        WebElement expirationMonthField = payment.getExpiryMonthInput();
        browserUtils browserUtils = new browserUtils(driver);
        expirationMonthField.clear();
        waitUtils.wait(1); // Wait for the text to be entered into the box
        expirationMonthField.sendKeys(expirationMonth);
        waitUtils.wait(1); // Wait for the text to be entered into the box
        String actualExpirationMonth = browserUtils.getTextValue(expirationMonthField);
        waitUtils.wait(1); // Wait for the text to be entered into the box
        assertEquals(expirationMonth, actualExpirationMonth,
                "The expiration month entered into the Expiration Month field does not match the expected expiration month");
        String message = browserUtils.getValidationMessage(expirationMonthField);
        if (!emptyUtils.isEmpty(message)) {
            validationMessage.put("expirationMonth", message);
        }
    }

    @And("I enter {string} into the Expiration Year field")
    public void iEnterIntoTheExpirationYearField(String expirationYear) {
        WebDriver driver = driverManager.getDriver();
        paymentPage payment = new paymentPage(driver);
        WebElement expirationYearField = payment.getExpiryYearInput();
        browserUtils browserUtils = new browserUtils(driver);
        expirationYearField.clear();
        waitUtils.wait(1); // Wait for the text to be entered into the box
        expirationYearField.sendKeys(expirationYear);
        waitUtils.wait(1); // Wait for the text to be entered into the box
        String actualExpirationYear = browserUtils.getTextValue(expirationYearField);
        waitUtils.wait(1); // Wait for the text to be entered into the box
        assertEquals(expirationYear, actualExpirationYear,
                "The expiration year entered into the Expiration Year field does not match the expected expiration year");
        String message = browserUtils.getValidationMessage(expirationYearField);
        if (!emptyUtils.isEmpty(message)) {
            validationMessage.put("expirationYear", message);
        }
    }

    @And("I click the Pay and Confirm Order button")
    public void iClickThePayAndConfirmOrderButton() {
        WebDriver driver = driverManager.getDriver();
        paymentPage payment = new paymentPage(driver);
        WebElement payAndConfirmOrderButton = payment.getSubmitButton();
        payAndConfirmOrderButton.click();
        waitUtils.wait(2); // Wait for the page to navigate to the invoice page
    }

    @And("I should be redirected to the invoice page")
    public void iShouldBeRedirectedToTheInvoicePage() {
        WebDriver driver = driverManager.getDriver();
        String currentUrl = driver.getCurrentUrl();
        assertEquals(true, currentUrl.contains(routes.paymentDone.path),
                "Expected to navigate to invoice page, but navigated to: " + currentUrl);
    }

    @Then("validation messages should be displayed for required payment fields")
    public void validationMessagesShouldBeDisplayedForRequiredPaymentFields() {
        assertEquals(true, !validationMessage.isEmpty(),
                "Expected validation messages for required payment fields, but no messages were found " + validationMessage);
    }

}
