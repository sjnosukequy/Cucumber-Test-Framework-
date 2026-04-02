package org.example.cucumber.tests.ui.checkout;

import org.example.cucumber.src.models.pom.cartPage;
import org.example.cucumber.src.models.pom.checkoutPage;
import org.example.cucumber.src.models.pom.homePage;
import org.example.cucumber.utils.browserUtils;
import org.example.cucumber.utils.driverManager;
import org.example.cucumber.utils.waitUtils;
import org.openqa.selenium.WebDriver;

import io.cucumber.java.After;
import io.cucumber.java.en.Given;

public class verifyCheckoutTestUi {
    int currentTab;
    WebDriver driver;
    browserUtils browserUtils;
    homePage homePage;
    checkoutPage checkoutPage;
    cartPage cartPage;
    String products = "";

    @After("@ui and @checkout")
    public void tearDown() {
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

    @Given("I am at the homepage for checkout testing")
    public void iAmAtTheHomepageForCheckoutTesting() {
        currentTab = driverManager.openNewTab();
        driver = driverManager.getDriver();
        browserUtils = new browserUtils(driver);
        homePage = new homePage(driver);
        checkoutPage = new checkoutPage(driver);
        cartPage = new cartPage(driver);
        driver.get(homePage.homepageUrl);
    }
}
