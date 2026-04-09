package com.flipkart.utils;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.ISuite;
import org.testng.ISuiteListener;

public class TestListener implements ITestListener, ISuiteListener {

    // ─── Suite Start — runs ONCE for entire suite ─────────────────
    @Override
    public void onStart(ISuite suite) {
        ExtentReportManager.initReport();
        System.out.println("Suite Started: " + suite.getName());
    }

    // ─── Suite End — runs ONCE after all tests ────────────────────
    @Override
    public void onFinish(ISuite suite) {
        ExtentReportManager.flushReport();
        System.out.println("Suite Finished! Report generated.");
    }

    // ─── These are ITestListener methods ─────────────────────────
    @Override
    public void onStart(ITestContext context) {
        System.out.println("Test group started: "
            + context.getName());
    }

    @Override
    public void onFinish(ITestContext context) {
        System.out.println("Test group finished: "
            + context.getName());
    }

    @Override
    public void onTestStart(ITestResult result) {
        String description = result.getMethod().getDescription();
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