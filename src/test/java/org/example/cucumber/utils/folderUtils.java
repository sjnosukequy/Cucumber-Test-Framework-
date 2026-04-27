package org.example.cucumber.utils;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class folderUtils {
    static DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static String folderString = "logs/" + LocalDate.now().format(myFormatObj);
    public static Path folderPath = Path.of(folderString);

    public static void createFolder() {
        if (!folderPath.toFile().exists()) {
            folderPath.toFile().mkdirs();
            System.out.println("Created folder: " + folderString);
        }
    }
}
