package org.example.cucumber.utils;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class loggerUtils {
    public static final Logger LOGGER = LogManager.getLogger(loggerUtils.class);

    public static void newLine() {
        LOGGER.info("{}", "=".repeat(70));
    }

    public static void logApiStatus(int expectedStatusCode, int actualStatusCode) {
        LOGGER.info("Expected Status Code: {}", expectedStatusCode);
        LOGGER.info("Received Status Code: {}", actualStatusCode);
    }

    public static void logApiStatus(int actualStatusCode) {
        LOGGER.info("Received Status Code: {}", actualStatusCode);
    }

    public static void logApiMessage(String expectedMessage, String actualMessage) {
        LOGGER.info("Expected Message: {}", expectedMessage);
        LOGGER.info("Received Message: {}", actualMessage);
    }

    public static void logApiMessage(String actualMessage) {
        LOGGER.info("Received Message: {}", actualMessage);
    }

    public static void logApiReponse(String response) {
        LOGGER.info("API Response: ");
        for (String line : response.split("\n")) {
            LOGGER.info(line);
        }
    }
}
