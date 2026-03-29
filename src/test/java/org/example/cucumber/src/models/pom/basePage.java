package org.example.cucumber.src.models.pom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.example.cucumber.env.envManager;

public class basePage {
    public WebDriver driver;
    public String fullUrl;
    public String url;
    public String homepageUrl = envManager.getBaseURI() + "/";

    public basePage(WebDriver driver, String url) {
        this.driver = driver;
        this.url = url;
        this.fullUrl = envManager.getBaseURI() + url;
    }

    public String cartHeaderLocater = "a[href=\"/view_cart\"]";
    public String logoutHeaderLocater = "a[href=\"/logout\"]";

    public WebElement getCartHeader() {
        return driver.findElement(By.cssSelector(cartHeaderLocater));
    }

    public WebElement getLogoutHeader() {
        return driver.findElement(By.cssSelector(logoutHeaderLocater));
    }
}
