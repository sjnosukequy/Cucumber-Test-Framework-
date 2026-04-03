package org.example.cucumber.env;

import org.example.cucumber.properties.propertiesManager;

public class envManager {
    private static String baseURI;
    private static int browser_timeout;
    private static String browser_type;
    private static boolean browser_headless;
    private static boolean browser_window_maximize;
    private static boolean use_custom_driver_path;
    private static boolean log_images_on_failure;
    
    static {
        baseURI = propertiesManager.get("baseURI");
        browser_timeout = Integer.parseInt(propertiesManager.get("browser_timeout"));
        browser_type = propertiesManager.get("browser_type");
        browser_headless = Boolean.parseBoolean(propertiesManager.get("browser_headless"));
        browser_window_maximize = Boolean.parseBoolean(propertiesManager.get("browser_window_maximize"));
        use_custom_driver_path = Boolean.parseBoolean(propertiesManager.get("use_custom_driver_path"));
        log_images_on_failure = Boolean.parseBoolean(propertiesManager.get("log_images_on_failure"));
        // email = System.getenv("EMAIL");
        // password = System.getenv("PASSWORD");
    }

    public static String getBaseURI() {
        return baseURI;
    }

    public static int getBrowserTimeout() {
        return browser_timeout;
    }

    public static String getBrowserType() {
        return browser_type;
    }

    public static boolean isBrowserHeadless() {
        return browser_headless;
    }

    public static boolean isBrowserWindowMaximize() {
        return browser_window_maximize;
    }

    public static boolean isUseCustomDriverPath() {
        return use_custom_driver_path;
    }

    public static boolean isLogImagesOnFailure() {
        return log_images_on_failure;
    }

}
