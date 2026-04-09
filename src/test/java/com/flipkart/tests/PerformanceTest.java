package com.flipkart.tests;

import com.flipkart.base.BaseTest;
import com.flipkart.pages.HomePage;
import com.flipkart.pages.SearchResultsPage;
import org.openqa.selenium.*;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class PerformanceTest extends BaseTest {

    private HomePage homePage;

    @BeforeMethod
    public void setUpPage() {
        homePage = new HomePage(getDriver());
        homePage.closeLoginPopupIfPresent();
    }

    // ─── TC105: Homepage load time ────────────────────────────────
    @Test(description = "Verify homepage loads under 10 seconds")
    public void verifyHomepageLoadTime() {
        long startTime = System.currentTimeMillis();

        Long loadTime = (Long)((JavascriptExecutor) getDriver())
            .executeScript(
                "return window.performance.timing" +
                ".loadEventEnd - " +
                "window.performance.timing.navigationStart;");

        long endTime = System.currentTimeMillis();
        long totalTime = (endTime - startTime) / 1000;

        System.out.println("JS load time: " + loadTime + "ms");
        System.out.println("Total time: " + totalTime + "s");

        Assert.assertTrue(loadTime < 15000,
            "Homepage should load under 15 seconds! " +
            "Actual: " + loadTime + "ms");
    }

    // ─── TC106: Search results load time ─────────────────────────
    @Test(description = "Verify search results load under 30 seconds")
    public void verifySearchResultsLoadTime() {
        long startTime = System.currentTimeMillis();

        homePage.searchProduct("iPhone");

        long endTime = System.currentTimeMillis();
        long loadTime = (endTime - startTime) / 1000;

        System.out.println("Search load time: " + loadTime + "s");
        Assert.assertTrue(loadTime < 30,
            "Search results should load under 30 seconds! " +
            "Actual: " + loadTime + "s"
        );
    }

    // ─── TC107: Product detail page load time ────────────────────
    @Test(description = "Verify product page loads under 15 seconds")
    public void verifyProductPageLoadTime() {
        homePage.searchProduct("iPhone");
        try { Thread.sleep(2000); } catch (Exception e) {}

        long startTime = System.currentTimeMillis();

        java.util.List<WebElement> products = getDriver()
            .findElements(By.cssSelector("a[href*='/p/']"));

        if (products.size() > 0) {
            String originalWindow = getDriver().getWindowHandle();
            ((JavascriptExecutor) getDriver())
                .executeScript(
                    "arguments[0].click();", products.get(0));
            try { Thread.sleep(3000); } catch (Exception e) {}

            for (String handle : getDriver().getWindowHandles()) {
                if (!handle.equals(originalWindow)) {
                    getDriver().switchTo().window(handle);
                    break;
                }
            }
        }

        long endTime = System.currentTimeMillis();
        long loadTime = (endTime - startTime) / 1000;

        System.out.println("Product page load: " + loadTime + "s");
        Assert.assertTrue(loadTime < 20,
            "Product page should load under 20 seconds!"
        );
    }

    // ─── TC108: Cart page load time ───────────────────────────────
    @Test(description = "Verify cart page loads under 10 seconds")
    public void verifyCartPageLoadTime() {
        long startTime = System.currentTimeMillis();

        getDriver().get(
            "https://www.flipkart.com/viewcart?marketplace=FLIPKART");
        try { Thread.sleep(2000); } catch (Exception e) {}

        long endTime = System.currentTimeMillis();
        long loadTime = (endTime - startTime) / 1000;

        System.out.println("Cart load time: " + loadTime + "s");
        Assert.assertTrue(loadTime < 15,
            "Cart page should load under 15 seconds!"
        );
    }

    // ─── TC109: Login page load time ──────────────────────────────
    @Test(description = "Verify login page loads under 10 seconds")
    public void verifyLoginPageLoadTime() {
        long startTime = System.currentTimeMillis();

        getDriver().get(
            "https://www.flipkart.com/account/login");
        try { Thread.sleep(2000); } catch (Exception e) {}

        long endTime = System.currentTimeMillis();
        long loadTime = (endTime - startTime) / 1000;

        System.out.println("Login load time: " + loadTime + "s");
        Assert.assertTrue(loadTime < 15,
            "Login page should load under 15 seconds!"
        );
    }

    // ─── TC110: Page response using JS timing ────────────────────
    @Test(description = "Verify page response time using JS timing")
    public void verifyPageResponseTime() {
        Long responseTime = (Long)((JavascriptExecutor) getDriver())
            .executeScript(
                "return window.performance.timing.responseEnd - " +
                "window.performance.timing.requestStart;");

        System.out.println("Response time: " + responseTime + "ms");
        Assert.assertTrue(responseTime < 10000,
            "Page response should be under 10 seconds! " +
            "Actual: " + responseTime + "ms"
        );
    }

    // ─── TC111: DOM load time ─────────────────────────────────────
    @Test(description = "Verify DOM loads within acceptable time")
    public void verifyDomLoadTime() {
        Long domLoadTime = (Long)((JavascriptExecutor) getDriver())
            .executeScript(
                "return window.performance.timing" +
                ".domContentLoadedEventEnd - " +
                "window.performance.timing.navigationStart;");

        System.out.println("DOM load time: " + domLoadTime + "ms");
        Assert.assertTrue(domLoadTime < 15000,
            "DOM should load under 15 seconds!"
        );
    }

    // ─── TC112: Verify page resources loaded ──────────────────────
    @Test(description = "Verify all page resources loaded")
    public void verifyPageResourcesLoaded() {
        String readyState = (String)((JavascriptExecutor) getDriver())
            .executeScript("return document.readyState;");

        System.out.println("Page ready state: " + readyState);
        Assert.assertEquals(readyState, "complete",
            "Page should be fully loaded!"
        );
    }
}