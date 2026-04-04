package org.example.cucumber.src.models.pom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class accountCreatedPage extends basePage {
    public accountCreatedPage(WebDriver driver) {
        super(driver, "/account_created");
    }
    
    String continueButtonLocater = "a[data-qa=\"continue-button\"]";
    public WebElement getContinueButton() {
        return driver.findElement(By.cssSelector(continueButtonLocater));
    }
}
