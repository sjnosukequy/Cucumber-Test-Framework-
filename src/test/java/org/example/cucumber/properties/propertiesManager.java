package org.example.cucumber.properties;

import java.util.Properties;
import io.github.cdimascio.dotenv.Dotenv;

public class propertiesManager {
    private static final Properties properties = new Properties();
    private static final Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();

    static {
        // 1. Load config.properties (The Defaults)
        try (var input = propertiesManager.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input != null) {
                properties.load(input);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String get(String key) {
        // Priority 1: Check System Environment (CI/CD pipelines)
        String value = System.getenv(key);

        // Priority 2: Check .env file (Local Developer Secrets)
        if (value == null) {
            value = dotenv.get(key);
        }

        // Priority 3: Check config.properties (Shared Defaults)
        if (value == null) {
            value = properties.getProperty(key);
        }

        // Priority 4: Fail safely
        if (value == null) {
            throw new RuntimeException("Configuration key '" + key + "' not found!");
        }

        return value;
    }
}
