package org.example.cucumber.tests.ui.cart;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.example.cucumber.src.models.pom.cartPage;
import org.example.cucumber.src.models.pom.homePage;
import org.example.cucumber.utils.browserUtils;
import org.example.cucumber.utils.driverManager;
import org.example.cucumber.utils.loggerUtils;
import org.example.cucumber.utils.waitUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class verifyCartTestUi {
    int currentTab;
    WebDriver driver;
    cartPage page;
    homePage homePage;
    browserUtils browserUtils;
    String products = "";
    String deletedProducts = "";

    @Given("I am at the homepage for cart testing")
    public void i_am_at_the_homepage_for_cart_testing() {
        currentTab = driverManager.openNewTab();
        driver = driverManager.getDriver();
        page = new cartPage(driver);
        homePage = new homePage(driver);
        browserUtils = new browserUtils(driver);
        driver.get(page.homepageUrl);
    }

    @When("I open the Cart page from the header")
    public void i_open_the_cart_page_from_the_header() {
        page.getCartHeader().click();
    }

    @Then("the Cart page should open")
    public void the_cart_page_should_open() {
        String currentUrl = driver.getCurrentUrl();
        if (!currentUrl.equals(page.fullUrl)) {
            throw new AssertionError("Expected URL: " + page.fullUrl + " but got: " + currentUrl);
        }
    }

    @And("the cart product table should not be displayed")
    public void the_cart_product_table_should_not_be_displayed() {
        boolean isDisplayed = false;
        try {
            WebElement cartTable = page.getCartTable();
            loggerUtils.appendLine("Cart table style: " + cartTable.getAttribute("style"));
            isDisplayed = cartTable.isDisplayed();
        } catch (Exception e) {
        }
        assertEquals(false, isDisplayed, "Expected cart table to not be displayed, but it was found.");
    }

    @And("the checkout button should be hidden")
    public void the_checkout_button_should_be_hidden() {
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
        WebElement emptyCartMessage = page.getEmptyCartMessage();
        assertEquals(true, emptyCartMessage.isDisplayed(),
                "Expected empty cart message to be displayed, but it was not found.");
    }

    @When("I click the continue shopping prompt")
    public void i_click_the_continue_shopping_prompt() {
        page.getContinueShoppingPrompt().click();
    }

    @Then("I should be navigated to the product page")
    public void i_should_be_navigated_to_the_product_page() {
        String currentUrl = driver.getCurrentUrl();
        loggerUtils.appendLine(currentUrl);
        if (!currentUrl.equals(page.homepageUrl + "products")) {
            throw new AssertionError("Expected URL: " + page.homepageUrl + "products" + " but got: " + currentUrl);
        }
    }

    @Then("I have added {string} to the cart")
    public void i_have_added_to_the_cart(String products) {
        this.products = products;
        for (String product : products.split(";")) {
            System.out.println("Adding product to cart: " + product.trim());
            homePage.addProductToCart(product.trim());
            waitUtils.wait(2); // Wait for the cart to update after adding each product
        }
    }

    @Then("the cart product table should be displayed")
    public void the_cart_product_table_should_be_displayed() {
        // browserUtils.wait(20);
        WebElement cartTable = page.getCartTable();
        assertEquals(true, cartTable.isDisplayed(), "Expected cart table to be displayed, but it was not found.");
    }

    @When("I open the Cart page from the modal")
    public void i_open_the_cart_page_from_the_modal() {
        homePage.openCartFromModal();
    }

    @And("the cart row for {string} should be displayed")
    public void the_cart_row_for_should_be_displayed(String products) {
        for (String product : products.split(";")) {
            WebElement cartRow = page.getProductRow(product.trim());
            assertEquals(true, cartRow.isDisplayed(),
                    "Expected cart row for product '" + product.trim() + "' to be displayed, but it was not found.");
        }
    }

    @And("the product image, name, price, and quantity should be visible and correct")
    public void the_product_image_name_price_and_quantity_should_be_visible_and_correct() {

        for (String product : products.split(";")) {
            if (!deletedProducts.contains(product)) {
                WebElement cartRow = page.getProductRow(product.trim());
                WebElement image = page.getProductRowImage(cartRow);
                WebElement name = page.getProductRowName(cartRow);
                WebElement price = page.getProductRowPrice(cartRow);
                WebElement quantity = page.getProductRowQuantity(cartRow);

                assertEquals(true, image.isDisplayed(), "Expected product image to be displayed for product '"
                        + product.trim() + "', but it was not found.");
                assertEquals(product.trim(), name.getText().trim(),
                        "Expected product name to be: " + product.trim() + " but got: " + name.getText().trim());
                assertEquals(true, price.isDisplayed(), "Expected product price to be displayed for product '"
                        + product.trim() + "', but it was not found.");
                assertEquals(true, quantity.isDisplayed(), "Expected product quantity to be displayed for product '"
                        + product.trim() + "', but it was not found.");
            }
        }
    }

    @And("the line total should be displayed correctly as price multiplied by quantity")
    public void the_line_total_should_be_displayed_correctly_as_price_multiplied_by_quantity() {
        for (String product : products.split(";")) {
            if (!deletedProducts.contains(product)) {
                WebElement cartRow = page.getProductRow(product.trim());
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
                                + product.trim() + "'.");
            }
        }
    }

    @And("the delete icon should be displayed for the item")
    public void the_delete_icon_should_be_displayed_for_the_item() {
        for (String product : products.split(";")) {
            WebElement cartRow = page.getProductRow(product.trim());
            WebElement deleteIcon = page.getProductRowDelete(cartRow);
            assertEquals(true, deleteIcon.isDisplayed());
        }
    }

    @And("the Proceed To Checkout button should be visible")
    public void the_proceed_to_checkout_button_should_be_visible() {
        WebElement checkoutButton = page.getCheckoutButton();
        assertEquals(true, checkoutButton.isDisplayed(),
                "Expected Proceed To Checkout button to be visible, but it was not found.");
    }

    @When("I delete the product {string} from the cart")
    public void i_delete_the_product_from_the_cart(String deletedProduct) {
        deletedProducts = deletedProduct;
        for (String product : deletedProducts.split(";")) {
            WebElement cartRow = page.getProductRow(product.trim());
            WebElement deleteIcon = page.getProductRowDelete(cartRow);
            deleteIcon.click();
            waitUtils.wait(2); // Wait for the cart to update after deleting the product
        }
    }

    @Then("the deleted items should be removed")
    public void the_deleted_items_should_be_removed() {
        for (String product : deletedProducts.split(";")) {
            boolean isDeleted = false;
            try {
                page.getProductRow(product.trim());
            } catch (Exception e) {
                isDeleted = true; // If an exception occurs, it means the product was deleted
            }
            assertEquals(true, isDeleted,
                    "Expected product '" + product.trim() + "' to be removed from the cart, but it was still found.");
        }
    }

    @And("the non-deleted items should remain displayed correctly")
    public void the_non_deleted_items_should_remain_displayed_correctly() {

        for (String product : products.split(";")) {
            if (!deletedProducts.contains(product)) {
                WebElement cartRow = page.getProductRow(product.trim());
                assertEquals(true, cartRow.isDisplayed(),
                        "Expected product '" + product.trim() + "' to remain in the cart, but it was not found.");
            }
        }
    }

}
