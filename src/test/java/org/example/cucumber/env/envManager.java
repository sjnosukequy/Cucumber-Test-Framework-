package org.example.cucumber.env;

import org.example.cucumber.properties.propertiesManager;

public class envManager {
    private static String baseURI;
    private static int browser_timeout;

    static {
        baseURI = propertiesManager.get("baseURI");
        browser_timeout = Integer.parseInt(propertiesManager.get("browser_timeout"));
        // email = System.getenv("EMAIL");
        // password = System.getenv("PASSWORD");
    }
    public static String getBaseURI() {
        return baseURI;
    }

    public static int getBrowserTimeout() {
        return browser_timeout;
    }
}
