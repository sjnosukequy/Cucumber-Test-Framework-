package org.example.cucumber.src.models.pom;

import org.example.cucumber.utils.waitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class cartPage extends basePage {

    public cartPage(WebDriver driver) {
        super(driver, "/view_cart");
    }

    public String emptyCartMessageLocater = "#empty_cart";
    public String cartTableLocater = "#cart_info_table";
    public String checkoutButtonLocater = "a.check_out";
    public String continueShoppingPromptLocater = "#empty_cart a[href=\"/products\"]";

    public WebElement getEmptyCartMessage() {
        return driver.findElement(By.cssSelector(emptyCartMessageLocater));
    }

    public WebElement getCartTable() {
        return driver.findElement(By.cssSelector(cartTableLocater));
    }

    public WebElement getCheckoutButton() {
        return driver.findElement(By.cssSelector(checkoutButtonLocater));
    }

    public WebElement getContinueShoppingPrompt() {
        return driver.findElement(By.cssSelector(continueShoppingPromptLocater));
    }

    public WebElement getProductRow(String productName) {
        WebElement productNameElement = driver.findElement(By.xpath("//a[text()='" + productName + "']"));
        WebElement productRow = productNameElement.findElement(By.xpath("./ancestor::tr"));
        return productRow;
    }

    String productRowImageLocater = "img";
    String productRowNameLocater = "td.cart_description a";
    String productRowPriceLocater = "td.cart_price p";
    String productRowQuantityLocater = "td.cart_quantity button";
    String productRowTotalLocater = "td.cart_total p";
    String productRowDeleteLocater = "td.cart_delete a";

    public WebElement getProductRowImage(WebElement productRow) {
        return productRow.findElement(By.cssSelector(productRowImageLocater));
    }

    public WebElement getProductRowName(WebElement productRow) {
        return productRow.findElement(By.cssSelector(productRowNameLocater));
    }

    public WebElement getProductRowPrice(WebElement productRow) {
        return productRow.findElement(By.cssSelector(productRowPriceLocater));
    }

    public WebElement getProductRowQuantity(WebElement productRow) {
        return productRow.findElement(By.cssSelector(productRowQuantityLocater));
    }

    public WebElement getProductRowTotal(WebElement productRow) {
        return productRow.findElement(By.cssSelector(productRowTotalLocater));
    }

    public WebElement getProductRowDelete(WebElement productRow) {
        return productRow.findElement(By.cssSelector(productRowDeleteLocater));
    }

    public void clearCart() {
        try {
            WebElement cartRow = driver.findElement(By.cssSelector("#cart_info_table tbody tr"));
            while (cartRow != null) {
                System.out.println("Deleting product: " + cartRow);
                WebElement deleteButton = getProductRowDelete(cartRow);
                deleteButton.click();
                waitUtils.wait(1);
                cartRow = driver.findElement(By.cssSelector("#cart_info_table tbody tr"));
            }
        } catch (Exception e) {
            System.out.println("Cart is already empty.");
        }
    }

}
