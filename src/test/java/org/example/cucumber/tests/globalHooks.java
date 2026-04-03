package org.example.cucumber.tests;

import io.cucumber.java.After;
import io.cucumber.java.AfterAll;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeStep;
import io.cucumber.java.Scenario;
import io.cucumber.java.Step;

import org.example.cucumber.env.envManager;
import org.example.cucumber.utils.browserUtils;
import org.example.cucumber.utils.driverManager;
import org.example.cucumber.utils.imageLogUtil;

import io.cucumber.java.Status;
import org.example.cucumber.utils.loggerUtils;

public class globalHooks {

    @Before
    public void LogScenario(Scenario scenario) {
        loggerUtils.startScenarioLog();
        loggerUtils.appendLine("Scenario: " + scenario.getName() + " (" + scenario.getId() + ")");
    }

    @After(order = 0)
    public void LogResult(Scenario scenario) {
        Status status = scenario.getStatus();
        switch (status) {
            case FAILED:
                loggerUtils.appendLine("*error*STATUS: " + status);
                break;

            default:
                loggerUtils.appendLine("STATUS: " + status);
                break;
        }
        loggerUtils.newLine();

        loggerUtils.flushToLogger();
        loggerUtils.clear();
    }

    @BeforeStep
    public void beforeStep(Scenario scenario, Step step) {
        loggerUtils.appendLine("About to run: " + step.getKeyword() + step.getText());
        loggerUtils.appendLine("Step is on line: " + step.getLine());
    }

    @AfterStep
    public void afterStep(Scenario scenario, Step step) {
        loggerUtils.appendLine("Finished: " + step.getText());
    }

    @After(order = 100, value = "@ui")
    public void resetBrowser(Scenario scenario) {
        try {
            driverManager.resetForNextScenario();
            System.out.println("Reset browser: " + " | Thread=" + Thread.currentThread().threadId());
        } catch (Exception e) {
            System.out.println("Skip browser reset: " + e.getMessage());
        }
    }

    @After(order = 10100, value = "@ui")
    public void takeScreenShot(Scenario scenario) {
        try {
            if (envManager.isLogImagesOnFailure() && scenario.isFailed()) {
                browserUtils utils = new browserUtils(driverManager.getDriver());
                byte[] screenshot = utils.takeScreenshot();
                imageLogUtil.savePng(screenshot, scenario.getName().replaceAll("\\s+", "_"));
                scenario.attach(screenshot, "image/png", scenario.getName().replaceAll("\\s+", "_"));
            }
        } catch (Exception e) {
            System.out.println("Skip final screenshot: " + e.getMessage());
        }
    }

    @AfterAll()
    public static void tearDown() {
        try {
            driverManager.quitAll();
            System.out.println("All drivers quit.");
        } catch (Exception e) {
            System.out.println("Skip final browser cleanup: " + e.getMessage());
        }
    }
}
