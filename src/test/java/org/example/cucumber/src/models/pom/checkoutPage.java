package org.example.cucumber.src.models.pom;

import org.example.cucumber.src.models.constants.routes;
import org.openqa.selenium.WebDriver;

public class checkoutPage extends basePage {
    public checkoutPage(WebDriver driver) {
        super(driver, routes.checkout.path);
    }

}
