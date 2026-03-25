package org.example.cucumber.tests;

import io.cucumber.java.After;
import io.cucumber.java.Before;

import io.cucumber.java.Scenario;

import static org.example.cucumber.utils.loggerUtils.LOGGER;
import io.cucumber.java.Status;
import org.example.cucumber.utils.loggerUtils;

public class globalHooks {

    @Before
    public void LogScenario(Scenario scenario) {
        LOGGER.info("Scenario: {}", scenario.getName());
    }

    @After
    public void LogResult(Scenario scenario) {
        Status status = scenario.getStatus();
        switch (status) {
            case PASSED:
                LOGGER.info("STATUS: {}", status);
                break;
            case FAILED:
                LOGGER.error("STATUS: {}", status);
                break;
            case SKIPPED:
                LOGGER.warn("STATUS: {}", status);
                break;
            default:
                LOGGER.warn("STATUS: {}", status);
                break;
        }
        loggerUtils.newLine();
    }
}
