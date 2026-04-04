package org.example.cucumber.src.models.pom;

import org.example.cucumber.src.models.constants.routes;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class registerPage extends basePage {
    public registerPage(WebDriver driver) {
        super(driver, routes.signup.path);
    }

    String inputNameLocater = "input[data-qa=\"signup-name\"]";
    String inputEmailLocater = "input[data-qa=\"signup-email\"]";
    String buttonSignupLocater = "button[data-qa=\"signup-button\"]";
    String inputPasswordLocater = "input[data-qa=\"password\"]";
    String inputFNameLocater = "#first_name";
    String inputLNameLocater = "#last_name";
    String inputAdd1Locater = "#address1";
    String inputAdd2Locater = "#address2";
    String inputStateLocater = "#state";
    String inputCityLocater = "#city";
    String inputZipcodeLocater = "#zipcode";
    String inputMobileLocater = "#mobile_number";
    String validationErrorLocater = "form[action=\"/signup\"] p";
    String buttonCreateAccountLocater = "button[data-qa=\"create-account\"]";

    public WebElement getNameField() {
        return driver.findElement(By.cssSelector(inputNameLocater));
    }

    public WebElement getEmailField() {
        return driver.findElement(By.cssSelector(inputEmailLocater));
    }

    public WebElement getSignupButton() {
        return driver.findElement(By.cssSelector(buttonSignupLocater));
    }

    public WebElement getPasswordField() {
        return driver.findElement(By.cssSelector(inputPasswordLocater));
    }

    public WebElement getFNameField() {
        return driver.findElement(By.cssSelector(inputFNameLocater));
    }

    public WebElement getLNameField() {
        return driver.findElement(By.cssSelector(inputLNameLocater));
    }

    public WebElement getAdd1Field() {
        return driver.findElement(By.cssSelector(inputAdd1Locater));
    }

    public WebElement getAdd2Field() {
        return driver.findElement(By.cssSelector(inputAdd2Locater));
    }

    public WebElement getStateField() {
        return driver.findElement(By.cssSelector(inputStateLocater));
    }

    public WebElement getCityField() {
        return driver.findElement(By.cssSelector(inputCityLocater));
    }

    public WebElement getZipcodeField() {
        return driver.findElement(By.cssSelector(inputZipcodeLocater));
    }

    public WebElement getMobileField() {
        return driver.findElement(By.cssSelector(inputMobileLocater));
    }

    public WebElement getValidationError() {
        return driver.findElement(By.cssSelector(validationErrorLocater));
    }

    public WebElement getCreateAccountButton() {
        return driver.findElement(By.cssSelector(buttonCreateAccountLocater));
    }
}
