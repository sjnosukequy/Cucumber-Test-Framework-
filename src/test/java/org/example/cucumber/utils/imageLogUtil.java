package org.example.cucumber.utils;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class imageLogUtil {

    private static final String LOG_DIR = "logs";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");

    /**
     * Saves a PNG byte array into the logs folder with a unique filename.
     *
     * @param imageBytes byte[] of PNG image
     * @return Path of the saved file
     */
    public static Path savePng(byte[] imageBytes, String name) {
        try {
            Path logDir = Paths.get(LOG_DIR);

            // Ensure logs directory exists
            if (Files.notExists(logDir)) {
                Files.createDirectories(logDir);
            }

            // Generate unique filename
            String timestamp = LocalDateTime.now().format(FORMATTER);
            String fileName = String.format("%s_%s.png",
                    name.replaceAll("\\s+", "_"),
                    timestamp);

            Path filePath = logDir.resolve(fileName);

            // Write file
            Files.write(filePath, imageBytes, StandardOpenOption.CREATE_NEW);

            return filePath;

        } catch (IOException e) {
            throw new RuntimeException("Failed to save PNG to logs folder", e);
        }
    }
}