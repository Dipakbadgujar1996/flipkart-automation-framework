package com.flipkart.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportManager {

    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> extentTest = 
        new ThreadLocal<>();

    public static void initReport() {
        ExtentSparkReporter sparkReporter =
            new ExtentSparkReporter(
                "reports/FlipkartTestReport.html");
        sparkReporter.config().setTheme(Theme.DARK);
        sparkReporter.config()
            .setDocumentTitle("Flipkart Automation Report");
        sparkReporter.config()
            .setReportName("Flipkart Test Results");
        sparkReporter.config()
            .setTimeStampFormat("dd-MM-yyyy HH:mm:ss");

        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
        extent.setSystemInfo("Project", "Flipkart Automation");
        extent.setSystemInfo("Browser", 
            ConfigReader.getBrowser());
        extent.setSystemInfo("Environment", "Production");
        extent.setSystemInfo("Base URL", 
            ConfigReader.getBaseUrl());
    }

    public static void createTest(String testName, 
                                   String description) {
        ExtentTest test = extent.createTest(
            testName, description);
        extentTest.set(test);
    }

    public static ExtentTest getTest() {
        return extentTest.get();
    }

    public static void flushReport() {
        if (extent != null) {
            extent.flush();
        }
    }

    public static void removeTest() {
        extentTest.remove();
    }
}