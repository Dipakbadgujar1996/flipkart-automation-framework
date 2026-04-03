package com.flipkart.utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotUtils {

    private static final String SCREENSHOT_DIR = 
        "reports/screenshots/";

    public static String takeScreenshot(WebDriver driver, 
                                         String testName) {
        new File(SCREENSHOT_DIR).mkdirs();
        String timestamp = new SimpleDateFormat(
            "ddMMyyyy_HHmmss").format(new Date());
        String fileName = testName + "_" + timestamp + ".png";
        String filePath = SCREENSHOT_DIR + fileName;

        try {
            File screenshot = ((TakesScreenshot) driver)
                .getScreenshotAs(OutputType.FILE);
            Files.copy(screenshot.toPath(),
                Paths.get(filePath));
            return filePath;
        } catch (IOException e) {
            System.err.println("Screenshot failed: " 
                + e.getMessage());
            return null;
        }
    }

    public static String takeScreenshotAsBase64(
            WebDriver driver) {
        try {
            return ((TakesScreenshot) driver)
                .getScreenshotAs(OutputType.BASE64);
        } catch (Exception e) {
            System.err.println("Base64 screenshot failed: " 
                + e.getMessage());
            return null;
        }
    }
}