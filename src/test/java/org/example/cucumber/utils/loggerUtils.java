package org.example.cucumber.utils;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class loggerUtils {
    public static final Logger LOGGER = LogManager.getLogger(loggerUtils.class);
    private static final ThreadLocal<StringBuilder> LOG_BUFFER = ThreadLocal.withInitial(StringBuilder::new);

    public static void startScenarioLog() {
        LOG_BUFFER.set(new StringBuilder());
    }

    public static void appendLine(String message) {
        LOG_BUFFER.get().append(message).append(System.lineSeparator());
    }

    public static void append(String message) {
        LOG_BUFFER.get().append(message);
    }

    public static void newLine() {
        appendLine("=".repeat(70));
    }

    public static void logApiStatus(int expectedStatusCode, int actualStatusCode) {
        appendLine("Expected Status Code: " + expectedStatusCode);
        appendLine("Received Status Code: " + actualStatusCode);
    }

    public static void logApiStatus(int actualStatusCode) {
        appendLine("Received Status Code: " + actualStatusCode);
    }

    public static void logApiMessage(String expectedMessage, String actualMessage) {
        appendLine("Expected Message: " + expectedMessage);
        appendLine("Received Message: " + actualMessage);
    }

    public static void logApiMessage(String actualMessage) {
        appendLine("Received Message: " + actualMessage);
    }

    public static void logApiResponse(String response) {
        appendLine("API Response:");
        if (response != null) {
            for (String line : response.split("\\R")) {
                appendLine(line);
            }
        }
    }

    public static void flushToLogger() {
        String content = LOG_BUFFER.get().toString();
        if (!content.isBlank()) {
            for (String line : content.split(System.lineSeparator())) {
                if(line.contains("*error*")) {
                    LOGGER.error(line.replace("*error*", ""));
                } else {
                    LOGGER.info(line);
                }
            }
        }
    }

    public static void clear() {
        LOG_BUFFER.remove();
    }
}
