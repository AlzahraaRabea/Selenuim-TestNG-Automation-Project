package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * ConfigReader — loads config.properties once and provides key-value access.
 * Supports optional default values.
 */
public class ConfigReader {

    private static final Properties props = new Properties();
    private static final String CONFIG_PATH = "src/test/resources/config/config.properties";

    static {
        try (FileInputStream fis = new FileInputStream(CONFIG_PATH)) {
            props.load(fis);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config.properties from: " + CONFIG_PATH, e);
        }
    }

    private ConfigReader() {}

    public static String get(String key) {
        String value = props.getProperty(key);
        if (value == null) {
            throw new RuntimeException("Property '" + key + "' not found in config.properties");
        }
        return value.trim();
    }

    public static String get(String key, String defaultValue) {
        return props.getProperty(key, defaultValue).trim();
    }

    public static String getBaseUrl() {
        return get("base.url");
    }
}
