package org.example.cucumber.src.models.pom;

import org.openqa.selenium.WebElement;

public interface cartTableRelated {
    public WebElement getCartTable();

    public WebElement getProductRow(String productName);

    public WebElement getProductRowImage(WebElement productRow);

    public WebElement getProductRowName(WebElement productRow);

    public WebElement getProductRowPrice(WebElement productRow);

    public WebElement getProductRowQuantity(WebElement productRow);

    public WebElement getProductRowTotal(WebElement productRow);

    public WebElement getProductRowDelete(WebElement productRow);

    public void clearCart();
}