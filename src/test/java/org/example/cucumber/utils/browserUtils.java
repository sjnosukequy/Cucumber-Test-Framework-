package org.example.cucumber.utils;

import java.time.Duration;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.example.cucumber.src.models.object.credential;
import org.example.cucumber.src.models.pom.loginPage;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.OutputType;

public class browserUtils {
    WebDriver driver;

    public browserUtils(WebDriver driver) {
        this.driver = driver;
    }

    public void wait(int seconds) {
        Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(seconds));
        wait.until(d -> {
            return true;
        });
    }

    public String getValidationMessage(WebElement element) {
        String message = (String) ((JavascriptExecutor) driver)
                .executeScript("return arguments[0].validationMessage;", element);
        return message;
    }

    public boolean ensureLogIn(String currentUrl) {
        loginPage page = new loginPage(driver);

        driver.get(page.fullUrl);
        wait(5);
        if (driver.getCurrentUrl().equals(page.homepageUrl)) {
            driver.get(currentUrl);
            return true;
        }

        credential creds = accountRotation.get();

        page.getEmailField().sendKeys(creds.getEmail());
        page.getPasswordField().sendKeys(creds.getPassword());
        page.getLoginButton().click();
        wait(5);
        if (driver.getCurrentUrl().equals(page.homepageUrl)) {
            driver.get(currentUrl);
            return true;
        }
        return false;
    }

    public byte[] takeScreenshot() {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

}
