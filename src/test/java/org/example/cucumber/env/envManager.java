package org.example.cucumber.env;

import org.example.cucumber.properties.propertiesManager;

public class envManager {
    private static String baseURI;

    static {
        baseURI = propertiesManager.get("baseURI");
        // email = System.getenv("EMAIL");
        // password = System.getenv("PASSWORD");
    }
    public static String getBaseURI() {
        return baseURI;
    }
}
