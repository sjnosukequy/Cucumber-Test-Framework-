package org.example.cucumber.utils;

import java.io.File;
import java.nio.file.Path;

import org.example.cucumber.env.envManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
// import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.edge.EdgeDriver;
// import org.openqa.selenium.edge.EdgeDriverService;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class browserManger {
    public WebDriver getBrowserDriver() {
        String browser = envManager.getBrowserType();
        Boolean headless = envManager.isBrowserHeadless();
        Boolean maximize = envManager.isBrowserWindowMaximize();
        if (browser.equalsIgnoreCase("chrome")) {
            File extensionPath = new File("src/test/resources/drivers/chrome/ublock.crx");

            ChromeOptions options = new ChromeOptions();
            if (headless) {
                options.addArguments("--headless=new");
            }
            if (maximize) {
                options.addArguments("--start-maximized");
            }
            options.addExtensions(extensionPath);

            return new ChromeDriver(options);

        } else if (browser.equalsIgnoreCase("edge")) {
            File extensionPath = new File("src/test/resources/drivers/edge/ublock.crx");

            EdgeOptions options = new EdgeOptions();
            if (headless) {
                options.addArguments("--headless=new");
            }
            if (maximize) {
                options.addArguments("--start-maximized");
            }
            options.addExtensions(extensionPath);

            return new EdgeDriver(options);
            
        } else if (browser.equalsIgnoreCase("firefox")) {
            Path extensionPath = new File("src/test/resources/drivers/firefox/ublock.xpi").toPath();

            FirefoxOptions options = new FirefoxOptions();
            if (headless) {
                options.addArguments("--headless");
            }
            if (maximize) {
                options.addArguments("--start-maximized");
            }

            FirefoxDriver driver = new FirefoxDriver(options);
            driver.installExtension(extensionPath);
            return driver;

        } else {
            throw new IllegalArgumentException("Unsupported browser: " + browser);
        }
    }
}