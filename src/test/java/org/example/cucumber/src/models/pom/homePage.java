package org.example.cucumber.src.models.pom;

import org.example.cucumber.src.models.constants.routes;
import org.example.cucumber.utils.waitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class homePage extends basePage {
    public homePage(WebDriver driver) {
        super(driver, routes.homepage.path);
    }

    public void addProductToCart(String productName) {
        WebElement productNameElement = driver.findElement(By.xpath("//p[text()='" + productName + "']"));
        WebElement parElement = productNameElement.findElement(By.xpath("./.."));
        WebElement addToCartButton = parElement.findElement(By.cssSelector("a[data-product-id]"));
        System.out.println("Found add to cart button: " + addToCartButton);
        addToCartButton.click();
        waitUtils.wait(1);
        continueFromModal();
        waitUtils.wait(1);
    }

    public void continueFromModal() {
        WebElement continueButton = driver.findElement(By.cssSelector("#cartModal button"));
        continueButton.click();
    }

    public void openCartFromModal() {
        WebElement viewCart = driver.findElement(By.cssSelector("#cartModal a[href='/view_cart']"));
        viewCart.click();
    }
}
