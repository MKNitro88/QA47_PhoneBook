package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesReader {
    public static String getProperty(String fileName, String key) {
        Properties properties = new Properties();
        try (FileInputStream fileInputStream =
                     new FileInputStream("src/test/resources/properties" + File.separator + fileName)) {
            properties.load(fileInputStream);
            return properties.getProperty(key);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read property file: " + fileName, e);

        }

    }
}