package com.flipkart.utils;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {

    @Override
    public void onStart(ITestContext context) {
        ExtentReportManager.initReport();
        System.out.println("Suite Started: " 
            + context.getName());
    }

    @Override
    public void onFinish(ITestContext context) {
        ExtentReportManager.flushReport();
        System.out.println("Report: " +
            "reports/FlipkartTestReport.html");
    }

    @Override
    public void onTestStart(ITestResult result) {
        String description = result.getMethod()
            .getDescription();
        ExtentReportManager.createTest(
            result.getName(), description);
        ExtentReportManager.getTest()
            .log(Status.INFO, "Test Started: " 
                + result.getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        ExtentReportManager.getTest()
            .log(Status.PASS, "✅ Passed: " 
                + result.getName());
        ExtentReportManager.removeTest();
    }

    @Override
    public void onTestFailure(ITestResult result) {
        ExtentReportManager.getTest()
            .log(Status.FAIL, "❌ Failed: " 
                + result.getName());
        ExtentReportManager.getTest()
            .log(Status.FAIL, "Reason: " 
                + result.getThrowable());
        try {
            String base64 = ScreenshotUtils
                .takeScreenshotAsBase64(
                    DriverManager.getDriver());
            if (base64 != null) {
                ExtentReportManager.getTest()
                    .fail("Screenshot:",
                        MediaEntityBuilder
                        .createScreenCaptureFromBase64String(
                            base64).build());
            }
        } catch (Exception e) {
            System.err.println("Screenshot error: " 
                + e.getMessage());
        }
        ExtentReportManager.removeTest();
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        ExtentReportManager.getTest()
            .log(Status.SKIP, "⚠️ Skipped: " 
                + result.getName());
        ExtentReportManager.removeTest();
    }
}