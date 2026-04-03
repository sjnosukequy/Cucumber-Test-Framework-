package org.example.cucumber.tests.ui.checkout;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.example.cucumber.src.api.user.getUserDetailsApi;
import org.example.cucumber.src.models.constants.routes;
import org.example.cucumber.src.models.object.credential;
import org.example.cucumber.src.models.pom.cartPage;
import org.example.cucumber.src.models.pom.checkoutPage;
import org.example.cucumber.utils.accountRotation;
import org.example.cucumber.utils.browserUtils;
import org.example.cucumber.utils.driverManager;
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

    @After("@ui and @checkout")
    public void tearDown() {
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
                String.format("Expected delivery address full name to match profile data '%s', but got '%s'",
                        expectedFullName, displayedName));

        String displayedCompany = checkout.getAddressCompanyHelper(deliveryAddressSection).getText();
        String expectedCompany = jsonPath.getString("user.company");
        assertEquals(expectedCompany, displayedCompany,
                String.format("Expected delivery address company to match profile data '%s', but got '%s'",
                        expectedCompany, displayedCompany));

        String displayedAddress1 = checkout.getAddress1Helper(deliveryAddressSection).getText();
        String expectedAddress1 = jsonPath.getString("user.address1");
        assertEquals(expectedAddress1, displayedAddress1,
                String.format("Expected delivery address line 1 to match profile data '%s', but got '%s'",
                        expectedAddress1, displayedAddress1));

        String displayedAddress2 = checkout.getAddress2Helper(deliveryAddressSection).getText();
        String expectedAddress2 = jsonPath.getString("user.address2");
        assertEquals(expectedAddress2, displayedAddress2,
                String.format("Expected delivery address line 2 to match profile data '%s', but got '%s'",
                        expectedAddress2, displayedAddress2));

        String displayedCity = checkout.getAddressCityHelper(deliveryAddressSection).getText();
        String expectedCity = jsonPath.getString("user.city");
        String expectedState = jsonPath.getString("user.state");
        String expectedZipcode = jsonPath.getString("user.zipcode");
        String expectedCityStateZip = expectedCity + " " + expectedState + " " + expectedZipcode;

        assertEquals(expectedCityStateZip, displayedCity,
                String.format("Expected delivery address city to match profile data '%s', but got '%s'",
                        expectedCityStateZip,
                        displayedCity));

        String displayedCountry = checkout.getAddressCountryHelper(deliveryAddressSection).getText();
        String expectedCountry = jsonPath.getString("user.country");
        assertEquals(expectedCountry, displayedCountry,
                String.format("Expected delivery address country to match profile data '%s', but got '%s'",
                        expectedCountry, displayedCountry));
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
                String.format("Expected billing address full name to match profile data '%s', but got '%s'",
                        expectedFullName, displayedName));

        String displayedCompany = checkout.getAddressCompanyHelper(billingAddressSection).getText();
        String expectedCompany = jsonPath.getString("user.company");
        assertEquals(expectedCompany, displayedCompany,
                String.format("Expected billing address company to match profile data '%s', but got '%s'",
                        expectedCompany, displayedCompany));

        String displayedAddress1 = checkout.getAddress1Helper(billingAddressSection).getText();
        String expectedAddress1 = jsonPath.getString("user.address1");
        assertEquals(expectedAddress1, displayedAddress1,
                String.format("Expected billing address line 1 to match profile data '%s', but got '%s'",
                        expectedAddress1, displayedAddress1));

        String displayedAddress2 = checkout.getAddress2Helper(billingAddressSection).getText();
        String expectedAddress2 = jsonPath.getString("user.address2");
        assertEquals(expectedAddress2, displayedAddress2,
                String.format("Expected billing address line 2 to match profile data '%s', but got '%s'",
                        expectedAddress2, displayedAddress2));

        String displayedCity = checkout.getAddressCityHelper(billingAddressSection).getText();
        String expectedCity = jsonPath.getString("user.city");
        String expectedState = jsonPath.getString("user.state");
        String expectedZipcode = jsonPath.getString("user.zipcode");
        String expectedCityStateZip = expectedCity + " " + expectedState + " " + expectedZipcode;
        assertEquals(expectedCityStateZip, displayedCity,
                String.format("Expected billing address city to match profile data '%s', but got '%s'",
                        expectedCityStateZip,
                        displayedCity));

        String displayedCountry = checkout.getAddressCountryHelper(billingAddressSection).getText();
        String expectedCountry = jsonPath.getString("user.country");
        assertEquals(expectedCountry, displayedCountry,
                String.format("Expected billing address country to match profile data '%s', but got '%s'",
                        expectedCountry, displayedCountry));
    }
}
