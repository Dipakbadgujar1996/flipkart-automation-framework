package com.flipkart.base;

import com.flipkart.utils.ConfigReader;
import com.flipkart.utils.DriverManager;
import com.flipkart.utils.TestListener;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;

// ✅ Add this annotation
@Listeners(TestListener.class)
public class BaseTest {

    @BeforeMethod
    public void setUp() {
        DriverManager.initDriver();
        DriverManager.openUrl(ConfigReader.getBaseUrl());
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public WebDriver getDriver() {
        return DriverManager.getDriver();
    }

    @AfterMethod
    public void tearDown() {
        DriverManager.quitDriver();
    }
}