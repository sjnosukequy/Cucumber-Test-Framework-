package org.example.cucumber.utils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.example.cucumber.env.envManager;

import io.qameta.allure.Allure;

import org.junit.jupiter.api.Assertions;

public class uiAssert extends Assertions {

    private static void captureAndLog(String fileName) {
        browserUtils utils = new browserUtils(driverManager.getDriver());
        byte[] screenshot = utils.takeScreenshot();
        if (envManager.isLogImagesOnFailure())
            imageLogUtil.savePng(screenshot, fileName.replaceAll("\\s+", "_"));

        String name = fileName.replaceAll("\\s+", "_");
        InputStream screenShotStream = new ByteArrayInputStream(screenshot);
        Allure.addAttachment(name, "image/png", screenShotStream, ".png");
    }

    public static void assertEquals(Object expected, Object actual) {
        try {
            Assertions.assertEquals(expected, actual);
        } catch (AssertionError e) {
            String message = e.getMessage() != null ? e.getMessage() : "assertEquals failure";
            captureAndLog(message);
            Assertions.assertEquals(expected, actual);
        }
    }

    public static void assertEquals(Object expected, Object actual, String message) {
        try {
            Assertions.assertEquals(expected, actual, message);
        } catch (AssertionError e) {
            captureAndLog(message);
            Assertions.assertEquals(expected, actual, message);
        }
    }

}
