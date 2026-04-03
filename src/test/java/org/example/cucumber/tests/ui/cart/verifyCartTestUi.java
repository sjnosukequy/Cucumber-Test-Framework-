package org.example.cucumber.tests.ui.cart;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.example.cucumber.src.models.pom.cartPage;
import org.example.cucumber.src.models.pom.homePage;
import org.example.cucumber.utils.browserUtils;
import org.example.cucumber.utils.driverManager;
import org.example.cucumber.utils.waitUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class verifyCartTestUi {
    @After("@ui and @cart")
    public void tearDown() {
        WebDriver driver = driverManager.getDriver();
        browserUtils browserUtils = new browserUtils(driver);
        cartPage page = new cartPage(driver);
        try {
            driver.get(page.fullUrl);
            waitUtils.wait(2); // Wait for any pending cart updates to complete
            page.clearCart();
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

    @When("I open the Cart page from the header")
    public void i_open_the_cart_page_from_the_header() {
        WebDriver driver = driverManager.getDriver();
        cartPage page = new cartPage(driver);
        page.getCartHeader().click();
    }

    @Then("the Cart page should open")
    public void the_cart_page_should_open() {
        WebDriver driver = driverManager.getDriver();
        cartPage page = new cartPage(driver);
        String currentUrl = driver.getCurrentUrl();
        assertEquals(page.fullUrl, currentUrl, "Expected URL: " + page.fullUrl + " but got: " + currentUrl);
    }

    @And("the cart checkout button should be hidden")
    public void the_cart_checkout_button_should_be_hidden() {
        WebDriver driver = driverManager.getDriver();
        cartPage page = new cartPage(driver);
        boolean isDisplayed = false;
        try {
            WebElement checkoutButton = page.getCheckoutButton();
            isDisplayed = checkoutButton.isDisplayed();
        } catch (Exception e) {
        }
        assertEquals(false, isDisplayed, "Expected checkout button to be hidden, but it was found.");
    }

    @And("an empty cart message with a continue shopping prompt should be displayed")
    public void an_empty_cart_message_with_a_continue_shopping_prompt_should_be_displayed() {
        WebDriver driver = driverManager.getDriver();
        cartPage page = new cartPage(driver);
        WebElement emptyCartMessage = page.getEmptyCartMessage();
        assertEquals(true, emptyCartMessage.isDisplayed(),
                "Expected empty cart message to be displayed, but it was not found.");
    }

    @When("I click the continue shopping prompt")
    public void i_click_the_continue_shopping_prompt() {
        WebDriver driver = driverManager.getDriver();
        cartPage page = new cartPage(driver);
        page.getContinueShoppingPrompt().click();
    }

    @When("I open the Cart page from the modal")
    public void i_open_the_cart_page_from_the_modal() {
        WebDriver driver = driverManager.getDriver();
        homePage homePage = new homePage(driver);
        homePage.openCartFromModal();
    }

    @And("the Proceed To Checkout button should be visible")
    public void the_proceed_to_checkout_button_should_be_visible() {
        WebDriver driver = driverManager.getDriver();
        cartPage page = new cartPage(driver);
        WebElement checkoutButton = page.getCheckoutButton();
        assertEquals(true, checkoutButton.isDisplayed(),
                "Expected Proceed To Checkout button to be visible, but it was not found.");
    }

    @When("I click the checkout button")
    public void i_click_the_checkout_button() {
        WebDriver driver = driverManager.getDriver();
        cartPage page = new cartPage(driver);
        WebElement checkoutButton = page.getCheckoutButton();
        checkoutButton.click();
        waitUtils.wait(2); // Wait for the navigation to the checkout page
    }

    @Then("I should be prompted to login or register")
    public void i_should_be_prompted_to_login_or_register() {
        WebDriver driver = driverManager.getDriver();
        cartPage page = new cartPage(driver);

        WebElement loginPrompt = page.getLoginPrompt();
        String promptText = loginPrompt.getText().toLowerCase();
        assertEquals(true, loginPrompt.isDisplayed(),
                "Expected login or register prompt to be visible, but it was not found.");
        assertEquals(true, promptText.contains("login") || promptText.contains("register"),
                "Expected login or register prompt to contain 'login' or 'register', but it was: " + promptText);
    }
}