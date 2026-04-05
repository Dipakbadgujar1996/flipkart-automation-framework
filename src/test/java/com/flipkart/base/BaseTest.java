package com.flipkart.base;

import com.flipkart.utils.ConfigReader;
import com.flipkart.utils.DriverManager;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;

public class BaseTest {

    // ─── Setup — Runs Before Every Test ──────────────────────────
	@BeforeMethod
	public void setUp() {
	    try {
	        DriverManager.initDriver();
	        DriverManager.openUrl(ConfigReader.getBaseUrl());
	        Thread.sleep(6000);
	    } catch (Exception e) {
	        System.out.println("Setup failed: " + e.getMessage());
	        // Retry once
	        try {
	            DriverManager.quitDriver();
	            DriverManager.initDriver();
	            DriverManager.openUrl(ConfigReader.getBaseUrl());
	            Thread.sleep(3000);
	        } catch (Exception e2) {
	            System.out.println("Retry also failed: " + e2.getMessage());
	        }
	    }
	}

    // ─── Get Driver — Used by all test classes ────────────────────
    public WebDriver getDriver() {
        return DriverManager.getDriver();
    }

    // ─── Teardown — Runs After Every Test ────────────────────────
    @AfterMethod
    public void tearDown() {
        DriverManager.quitDriver();
    }
}