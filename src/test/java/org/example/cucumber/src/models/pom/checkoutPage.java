package org.example.cucumber.src.models.pom;

import org.example.cucumber.src.models.constants.routes;
import org.example.cucumber.utils.waitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class checkoutPage extends basePage implements cartTableRelated {
    public checkoutPage(WebDriver driver) {
        super(driver, routes.checkout.path);
    }

    String addressDeliverySectionLocater = "#address_delivery";
    String addressBillingSectionLocater = "#address_invoice";
    String addressNameHelperLocater = "li:nth-child(2)";
    String addressCompanyHelperLocater = "li:nth-child(3)";
    String address1HelperLocater = "li:nth-child(4)";
    String address2HelperLocater = "li:nth-child(5)";
    String addressCityHelperLocater = "li:nth-child(6)";
    String addressCountryHelperLocater = "li:nth-child(7)";
    String addressPhoneHelperLocater = "li:nth-child(8)";
    String orderTableLocater = "#cart_info table";
    String orderMessageLocater = "#ordermsg textarea";
    String placeOrderButtonLocater = "a[href=\"/payment\"]";

    public WebElement getAddressNameHelper(WebElement addressSection) {
        return addressSection.findElement(By.cssSelector(addressNameHelperLocater));
    }
    public WebElement getAddressCompanyHelper(WebElement addressSection) {
        return addressSection.findElement(By.cssSelector(addressCompanyHelperLocater));
    }
    public WebElement getAddress1Helper(WebElement addressSection) {
        return addressSection.findElement(By.cssSelector(address1HelperLocater));
    }
    public WebElement getAddress2Helper(WebElement addressSection) {
        return addressSection.findElement(By.cssSelector(address2HelperLocater));
    }
    public WebElement getAddressCityHelper(WebElement addressSection) {
        return addressSection.findElement(By.cssSelector(addressCityHelperLocater));
    }
    public WebElement getAddressCountryHelper(WebElement addressSection) {
        return addressSection.findElement(By.cssSelector(addressCountryHelperLocater));
    }
    public WebElement getAddressPhoneHelper(WebElement addressSection) {
        return addressSection.findElement(By.cssSelector(addressPhoneHelperLocater));
    }

    public WebElement getAddressDeliverySection() {
        return driver.findElement(By.cssSelector(addressDeliverySectionLocater));
    }

    public WebElement getAddressBillingSection() {
        return driver.findElement(By.cssSelector(addressBillingSectionLocater));
    }

    public WebElement getCartTable() {
        return driver.findElement(By.cssSelector(orderTableLocater));
    }

    public WebElement getOrderMessage() {
        return driver.findElement(By.cssSelector(orderMessageLocater));
    }

    public WebElement getPlaceOrderButton() {
        return driver.findElement(By.cssSelector(placeOrderButtonLocater));
    }

    String productRowImageLocater = "img";
    String productRowNameLocater = "td.cart_description a";
    String productRowPriceLocater = "td.cart_price p";
    String productRowQuantityLocater = "td.cart_quantity button";
    String productRowTotalLocater = "td.cart_total p";
    String productRowDeleteLocater = "td.cart_delete a";

    public WebElement getProductRow(String productName) {
        WebElement productNameElement = driver.findElement(By.xpath("//a[text()='" + productName + "']"));
        WebElement productRow = productNameElement.findElement(By.xpath("./ancestor::tr"));
        return productRow;
    }

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
