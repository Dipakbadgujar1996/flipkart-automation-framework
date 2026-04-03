package com.flipkart.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

    private static Properties properties;
    private static final String CONFIG_PATH = 
        "src/test/resources/config.properties";

    // Load properties file once when class is first used
    static {
        try {
            FileInputStream fis = new FileInputStream(CONFIG_PATH);
            properties = new Properties();
            properties.load(fis);
            fis.close();
        } catch (IOException e) {
            throw new RuntimeException("config.properties not found at: " 
                + CONFIG_PATH);
        }
    }

    // Generic getter
    public static String get(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            throw new RuntimeException("Key '" + key + "' not found in config.properties");
        }
        return value.trim();
    }

    // Specific getters for convenience
    public static String getBrowser() {
        return get("browser");
    }

    public static String getBaseUrl() {
        return get("base.url");
    }

    public static int getImplicitWait() {
        return Integer.parseInt(get("implicit.wait"));
    }

    public static int getExplicitWait() {
        return Integer.parseInt(get("explicit.wait"));
    }

    public static int getPageLoadTimeout() {
        return Integer.parseInt(get("page.load.timeout"));
    }

    public static boolean isHeadless() {
        return Boolean.parseBoolean(get("headless"));
    }

    public static int getBrowserWidth() {
        return Integer.parseInt(get("browser.width"));
    }

    public static int getBrowserHeight() {
        return Integer.parseInt(get("browser.height"));
    }
}