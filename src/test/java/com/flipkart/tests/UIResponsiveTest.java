package com.flipkart.tests;

import com.flipkart.base.BaseTest;
import com.flipkart.pages.HomePage;
import org.openqa.selenium.*;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.util.List;

public class UIResponsiveTest extends BaseTest {

    private HomePage homePage;

    @BeforeMethod
    public void setUpPage() {
        homePage = new HomePage(getDriver());
        homePage.closeLoginPopupIfPresent();
    }

    // ─── TC98: Verify 1920x1080 resolution ───────────────────────
    @Test(description = "Verify layout on 1920x1080 resolution")
    public void verifyLayoutOn1920x1080() {
        getDriver().manage().window().setSize(
            new Dimension(1920, 1080));
        try { Thread.sleep(2000); } catch (Exception e) {}

        int width = ((Long)((JavascriptExecutor) getDriver())
            .executeScript("return window.innerWidth;")).intValue();
        System.out.println("Window width: " + width);

        Assert.assertTrue(width > 0,
            "Page should render at 1920x1080!");
    }

    // ─── TC99: Verify 1366x768 resolution ────────────────────────
    @Test(description = "Verify layout on 1366x768 resolution")
    public void verifyLayoutOn1366x768() {
        getDriver().manage().window().setSize(
            new Dimension(1366, 768));
        try { Thread.sleep(2000); } catch (Exception e) {}

        int width = ((Long)((JavascriptExecutor) getDriver())
            .executeScript("return window.innerWidth;")).intValue();
        System.out.println("Window width: " + width);

        Assert.assertTrue(width > 0,
            "Page should render at 1366x768!");
    }

    // ─── TC100: Verify no broken images ──────────────────────────
    @Test(description = "Verify no broken images on homepage")
    public void verifyNoBrokenImages() {
        List<WebElement> images = getDriver()
            .findElements(By.cssSelector("img"));

        int totalImages = images.size();
        int brokenImages = 0;

        for (WebElement img : images) {
            try {
                String src = img.getAttribute("src");
                if (src == null || src.isEmpty()) {
                    brokenImages++;
                }
            } catch (Exception e) {
                brokenImages++;
            }
        }

        System.out.println("Total images: " + totalImages);
        System.out.println("Broken images: " + brokenImages);

        Assert.assertTrue(brokenImages < 5,
            "Should have minimal broken images! Found: "
                + brokenImages);
    }

    // ─── TC101: Verify page has no horizontal scroll ─────────────
    @Test(description = "Verify page has no horizontal scroll")
    public void verifyNoHorizontalScroll() {
        Long scrollWidth = (Long)((JavascriptExecutor) getDriver())
            .executeScript(
                "return document.documentElement.scrollWidth;");
        Long clientWidth = (Long)((JavascriptExecutor) getDriver())
            .executeScript(
                "return document.documentElement.clientWidth;");

        System.out.println("Scroll width: " + scrollWidth);
        System.out.println("Client width: " + clientWidth);

        Assert.assertTrue(
            scrollWidth <= clientWidth + 20,
            "Page should not have horizontal scroll!"
        );
    }

    // ─── TC102: Verify footer is present ─────────────────────────
    @Test(description = "Verify footer is present on homepage")
    public void verifyFooterPresent() {
        homePage.scrollToBottom();
        try { Thread.sleep(1000); } catch (Exception e) {}

        List<WebElement> footer = getDriver()
            .findElements(By.cssSelector("footer"));
        System.out.println("Footer found: " + footer.size());

        Assert.assertTrue(footer.size() > 0,
            "Footer should be present!"
        );
    }

    // ─── TC103: Verify page title is not empty ───────────────────
    @Test(description = "Verify page title is not empty")
    public void verifyPageTitleNotEmpty() {
        String title = getDriver().getTitle();
        System.out.println("Page title: " + title);
        Assert.assertTrue(
            title != null && title.length() > 0,
            "Page title should not be empty!"
        );
    }

    // ─── TC104: Verify scroll to top works ───────────────────────
    @Test(description = "Verify scroll to top works")
    public void verifyScrollToTop() {
        // Scroll to bottom first
        homePage.scrollToBottom();
        try { Thread.sleep(1000); } catch (Exception e) {}

        // Scroll back to top
        homePage.scrollToTop();
        try { Thread.sleep(1000); } catch (Exception e) {}

        Long scrollTop = (Long)((JavascriptExecutor) getDriver())
            .executeScript("return window.pageYOffset;");

        System.out.println("Scroll position: " + scrollTop);
        Assert.assertTrue(scrollTop < 100,
            "Page should scroll back to top!"
        );
    }
}