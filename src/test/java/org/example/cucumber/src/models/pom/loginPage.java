package org.example.cucumber.src.models.pom;

import org.example.cucumber.src.models.constants.routes;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class loginPage extends basePage {

    public loginPage(WebDriver driver) {
        super(driver, routes.login.path);
    }

    String emailLocater = "input[data-qa=\"login-email\"]";
    String passwordLocater = "input[data-qa=\"login-password\"]";
    String loginButtonLocater = "button[data-qa=\"login-button\"]";
    String errorMessageLocater = "form[action=\"/login\"] p";
    String logOutLocater = "a[href=\"/logout\"]";

    public WebElement getEmailField() {
        return driver.findElement(By.cssSelector(emailLocater));
    }

    public WebElement getPasswordField() {
        return driver.findElement(By.cssSelector(passwordLocater));
    }

    public WebElement getLoginButton() {
        return driver.findElement(By.cssSelector(loginButtonLocater));
    }

    public WebElement getErrorMessage() {
        return driver.findElement(By.cssSelector(errorMessageLocater));
    }

    public WebElement getLogOutButton() {
        return driver.findElement(By.cssSelector(logOutLocater));
    }
}
