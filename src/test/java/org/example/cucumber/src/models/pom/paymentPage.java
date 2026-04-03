package org.example.cucumber.src.models.pom;

import org.example.cucumber.src.models.constants.routes;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class paymentPage extends basePage{
    public paymentPage(WebDriver driver) {
        super(driver, routes.payment.path);
    }
    String cardNameInputLocater = "input[name=\"name_on_card\"]";
    String cardNumberInputLocater = "input[name=\"card_number\"]";
    String cvcInputLocater = "input[name=\"cvc\"]";
    String expiryMonthInputLocater = "input[name=\"expiry_month\"]";
    String expiryYearInputLocater = "input[name=\"expiry_year\"]";
    String submitButtonLocater = "button#submit";

    public WebElement getCardNameInput() {
        return driver.findElement(By.cssSelector(cardNameInputLocater));
    }

    public WebElement getCardNumberInput() {
        return driver.findElement(By.cssSelector(cardNumberInputLocater));
    }

    public WebElement getCvcInput() {
        return driver.findElement(By.cssSelector(cvcInputLocater));
    }

    public WebElement getExpiryMonthInput() {
        return driver.findElement(By.cssSelector(expiryMonthInputLocater));
    }

    public WebElement getExpiryYearInput() {
        return driver.findElement(By.cssSelector(expiryYearInputLocater));
    }

    public WebElement getSubmitButton() {
        return driver.findElement(By.cssSelector(submitButtonLocater));
    }
}
