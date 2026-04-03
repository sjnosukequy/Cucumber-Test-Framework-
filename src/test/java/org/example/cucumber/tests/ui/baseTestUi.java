package org.example.cucumber.tests.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.example.cucumber.env.envManager;
import org.example.cucumber.src.models.pom.cartPage;
import org.example.cucumber.src.models.pom.cartTableRelated;
import org.example.cucumber.src.models.pom.checkoutPage;
import org.example.cucumber.src.models.pom.homePage;
import org.example.cucumber.utils.browserUtils;
import org.example.cucumber.utils.driverManager;
import org.example.cucumber.utils.prodStringUtils;
import org.example.cucumber.utils.waitUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class baseTestUi {
    int currentTab;
    String products = "";
    String deletedProducts = "";

    private void resetState() {
        this.products = "";
        this.deletedProducts = "";
    }

    @Given("I am on the home page")
    public void i_am_on_the_home_page() {
        currentTab = driverManager.openNewTab();
        WebDriver driver = driverManager.getDriver();
        homePage homePage = new homePage(driver);
        resetState();
        driver.get(homePage.fullUrl);
    }

    @And("I am logged in on the UI")
    public void i_am_logged_in_on_the_ui() {
        WebDriver driver = driverManager.getDriver();
        browserUtils utils = new browserUtils(driver);
        utils.ensureLogIn(driver.getCurrentUrl());
    }

    @And("I am not logged in on the UI")
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

    @Then("I have added {string} to the cart")
    public void i_have_added_to_the_cart(String products) {
        homePage homePage = new homePage(driverManager.getDriver());
        this.products = prodStringUtils.convertFormat(products);
        for (String product : this.products.split(";")) {
            String parseProduct = prodStringUtils.parseProd(product);
            System.out.println("Adding product to cart: " + parseProduct.trim());
            homePage.addProductToCart(parseProduct.trim());
            waitUtils.wait(2); // Wait for the cart to update after adding each product
        }
    }

    public cartTableRelated getCartTableRelatedPage(String pageType) {
        WebDriver driver = driverManager.getDriver();
        switch (pageType) {
            case "cart":
                return new cartPage(driver);
            case "checkout":
                return new checkoutPage(driver);
            default:
                throw new IllegalArgumentException("Invalid page type: " + pageType);
        }
    }

    @And("the cart product table should not be displayed - {string}")
    public void the_cart_product_table_should_not_be_displayed(String pageType) {
        cartTableRelated page = getCartTableRelatedPage(pageType);
        boolean isDisplayed = false;
        try {
            WebElement cartTable = page.getCartTable();
            isDisplayed = cartTable.isDisplayed();
        } catch (Exception e) {
        }
        assertEquals(false, isDisplayed, "Expected cart table to not be displayed, but it was found.");
    }

    @Then("the cart product table should be displayed - {string}")
    public void the_cart_product_table_should_be_displayed(String pageType) {
        // browserUtils.wait(20);
        cartTableRelated page = getCartTableRelatedPage(pageType);
        WebElement cartTable = page.getCartTable();
        assertEquals(true, cartTable.isDisplayed(), "Expected cart table to be displayed, but it was not found.");
    }

    @And("the cart row for {string} should be displayed - {string}")
    public void the_cart_row_for_should_be_displayed(String products, String pageType) {
        cartTableRelated page = getCartTableRelatedPage(pageType);

        for (String product : products.split(";")) {
            WebElement cartRow = page.getProductRow(product.trim());
            assertEquals(true, cartRow.isDisplayed(),
                    "Expected cart row for product '" + product.trim() + "' to be displayed, but it was not found.");
        }
    }

    @And("the product image, name, price, and quantity should be visible and correct - {string}")
    public void the_product_image_name_price_and_quantity_should_be_visible_and_correct(String pageType) {
        cartTableRelated page = getCartTableRelatedPage(pageType);

        for (String product : this.products.split(";")) {
            if (!this.deletedProducts.contains(product)) {
                String parseProduct = prodStringUtils.parseProd(product);
                WebElement cartRow = page.getProductRow(parseProduct.trim());
                WebElement image = page.getProductRowImage(cartRow);
                WebElement name = page.getProductRowName(cartRow);
                WebElement price = page.getProductRowPrice(cartRow);
                WebElement quantity = page.getProductRowQuantity(cartRow);

                assertEquals(true, image.isDisplayed(), "Expected product image to be displayed for product '"
                        + parseProduct.trim() + "', but it was not found.");
                assertEquals(parseProduct.trim(), name.getText().trim(),
                        "Expected product name to be: " + parseProduct.trim() + " but got: " + name.getText().trim());
                assertEquals(true, price.isDisplayed(), "Expected product price to be displayed for product '"
                        + parseProduct.trim() + "', but it was not found.");
                assertEquals(true, quantity.isDisplayed(), "Expected product quantity to be displayed for product '"
                        + parseProduct.trim() + "', but it was not found.");
            }
        }
    }

    @And("the line total should be displayed correctly as price multiplied by quantity - {string}")
    public void the_line_total_should_be_displayed_correctly_as_price_multiplied_by_quantity(String pageType) {
        cartTableRelated page = getCartTableRelatedPage(pageType);
        for (String product : this.products.split(";")) {
            if (!this.deletedProducts.contains(product)) {
                String parseProduct = prodStringUtils.parseProd(product);
                WebElement cartRow = page.getProductRow(parseProduct.trim());
                WebElement priceElement = page.getProductRowPrice(cartRow);
                WebElement quantityElement = page.getProductRowQuantity(cartRow);
                WebElement totalElement = page.getProductRowTotal(cartRow);

                String priceText = priceElement.getText().split(" ")[1];
                String quantityText = quantityElement.getText().trim();
                String totalText = totalElement.getText().split(" ")[1];

                double price = Double.parseDouble(priceText);
                int quantity = Integer.parseInt(quantityText);
                double expectedTotal = price * quantity;
                double actualTotal = Double.parseDouble(totalText);

                assertEquals(expectedTotal, actualTotal, 0.01,
                        "Expected line total to be price multiplied by quantity, but it was not correct for product '"
                                + parseProduct.trim() + "'.");
            }
        }
    }

    @And("the overall order total should be correctly displayed - checkout")
    public void the_overall_order_total_should_be_correctly_displayed() {
        checkoutPage page = new checkoutPage(driverManager.getDriver());
        WebElement totalPrice = page.getTotalPrice();
        double actualTotal = Double.parseDouble(totalPrice.getText().split(" ")[1]);
        double expectedTotal = 0.0;
        for (String product : this.products.split(";")) {
            if (!this.deletedProducts.contains(product)) {
                String parseProduct = prodStringUtils.parseProd(product);
                WebElement cartRow = page.getProductRow(parseProduct.trim());
                WebElement totalElement = page.getProductRowTotal(cartRow);
                String totalText = totalElement.getText().split(" ")[1];
                expectedTotal += Double.parseDouble(totalText);
            }
        }

        assertEquals(expectedTotal, actualTotal, 0.01,
                "Expected overall order total to be displayed correctly, but it was not.");
    }

    @And("the delete icon should be displayed for the item - {string}")
    public void the_delete_icon_should_be_displayed_for_the_item(String pageType) {
        cartTableRelated page = getCartTableRelatedPage(pageType);
        for (String product : this.products.split(";")) {
            String parseProduct = prodStringUtils.parseProd(product);
            WebElement cartRow = page.getProductRow(parseProduct.trim());
            WebElement deleteIcon = page.getProductRowDelete(cartRow);
            assertEquals(true, deleteIcon.isDisplayed());
        }
    }

    @When("I delete the product {string} from the cart - {string}")
    public void i_delete_the_product_from_the_cart(String deletedProduct, String pageType) {
        this.deletedProducts = prodStringUtils.convertFormat(deletedProduct);
        cartTableRelated page = getCartTableRelatedPage(pageType);
        for (String product : this.deletedProducts.split(";")) {
            String parseProduct = prodStringUtils.parseProd(product);
            WebElement cartRow = page.getProductRow(parseProduct.trim());
            WebElement deleteIcon = page.getProductRowDelete(cartRow);
            deleteIcon.click();
            waitUtils.wait(2); // Wait for the cart to update after deleting the product
        }
    }

    @Then("the deleted items should be removed from the cart - {string}")
    public void the_deleted_items_should_be_removed(String pageType) {
        cartTableRelated page = getCartTableRelatedPage(pageType);
        for (String product : this.deletedProducts.split(";")) {
            String parseProduct = prodStringUtils.parseProd(product);
            boolean isDeleted = false;
            try {
                page.getProductRow(parseProduct.trim());
            } catch (Exception e) {
                isDeleted = true; // If an exception occurs, it means the product was deleted
            }
            assertEquals(true, isDeleted,
                    "Expected product '" + parseProduct.trim()
                            + "' to be removed from the cart, but it was still found.");
        }
    }

    @And("the non-deleted items should remain displayed correctly - {string}")
    public void the_non_deleted_items_should_remain_displayed_correctly(String pageType) {
        cartTableRelated page = getCartTableRelatedPage(pageType);
        for (String product : this.products.split(";")) {
            if (!this.deletedProducts.contains(product)) {
                String parseProduct = prodStringUtils.parseProd(product);
                WebElement cartRow = page.getProductRow(parseProduct.trim());
                assertEquals(true, cartRow.isDisplayed(),
                        "Expected product '" + parseProduct.trim() + "' to remain in the cart, but it was not found.");
            }
        }
    }
}
