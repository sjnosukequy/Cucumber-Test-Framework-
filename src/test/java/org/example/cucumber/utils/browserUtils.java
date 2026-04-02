package org.example.cucumber.utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.example.cucumber.src.models.object.credential;
import org.example.cucumber.src.models.pom.homePage;
import org.example.cucumber.src.models.pom.loginPage;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.OutputType;

public class browserUtils {
    WebDriver driver;

    public browserUtils(WebDriver driver) {
        this.driver = driver;
    }

    public String getValidationMessage(WebElement element) {
        String message = (String) ((JavascriptExecutor) driver)
                .executeScript("return arguments[0].validationMessage;", element);
        return message;
    }

    public void ensureLogIn(String currentUrl) {
        loginPage page = new loginPage(driver);

        driver.get(page.fullUrl);
        waitUtils.wait(2);
        if (driver.getCurrentUrl().equals(page.homepageUrl)) {
            driver.get(currentUrl);
            return;
        }

        credential creds = accountRotation.get();
        System.out.println(String.format("Attempting login with user: %s", creds.getEmail()));

        page.getEmailField().sendKeys(creds.getEmail());
        page.getPasswordField().sendKeys(creds.getPassword());
        page.getLoginButton().click();
        waitUtils.wait(2);
        if (driver.getCurrentUrl().equals(page.homepageUrl)) {
            driver.get(currentUrl);
            return;
        }
        throw new RuntimeException(String.format("Login failed for user %s. Current URL after login attempt: %s:%s", creds.getEmail(), creds.getPassword()));
    }

    public void ensureLogOut(String currentUrl){
        homePage page = new homePage(driver);
        try{
            page.getLogoutHeader().click();
            waitUtils.wait(2);
            driver.get(currentUrl);
        }catch (Exception e){
            throw new RuntimeException("Error during logout: " + e.getMessage());
        }
    }

    public byte[] takeScreenshot() {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

}
