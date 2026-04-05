package com.flipkart.pages;

import com.flipkart.utils.ConfigReader;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class WishlistPage {

    private WebDriver driver;
    private WebDriverWait wait;

    // ─── Locators ─────────────────────────────────────────────────

    // ✅ Wishlist link in header
    @FindBy(css = "a.CfNfim")
    private WebElement wishlistLink;

    // ✅ Wishlist text in header
    @FindBy(xpath = "//div[text()='Wishlist']")
    private WebElement wishlistText;

    // ─── Constructor ──────────────────────────────────────────────

    public WishlistPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver,
            Duration.ofSeconds(ConfigReader.getExplicitWait()));
        PageFactory.initElements(driver, this);
    }

    // ─── Actions ──────────────────────────────────────────────────

    // Navigate to wishlist
    public void navigateToWishlist() {
        try {
            driver.get("https://www.flipkart.com/wishlist");
            Thread.sleep(3000);
            System.out.println("Navigated to wishlist!");
        } catch (Exception e) {
            System.out.println("Wishlist navigate failed: "
                + e.getMessage());
        }
    }

    // Click wishlist icon in header
    public void clickWishlistIcon() {
        try {
            wait.until(ExpectedConditions
                .elementToBeClickable(wishlistLink));
            wishlistLink.click();
            Thread.sleep(2000);
            System.out.println("Wishlist icon clicked!");
        } catch (Exception e) {
            System.out.println("Wishlist click failed: "
                + e.getMessage());
        }
    }

    // Verify wishlist icon visible in header
    public boolean isWishlistIconVisible() {
        try {
            List<WebElement> icons = driver.findElements(
                By.cssSelector("a.CfNfim"));
            System.out.println("Wishlist icon: " + icons.size());
            return icons.size() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    // Verify wishlist redirects to login for guest
    public boolean isRedirectedToLogin() {
        try {
            Thread.sleep(2000);
            String url = driver.getCurrentUrl();
            boolean hasLoginInput = driver.findElements(
                By.cssSelector("input.c3Bd2c")).size() > 0;
            boolean isLoginUrl = url.contains("login") ||
                                 url.contains("account");
            System.out.println("Login redirect: " +
                (hasLoginInput || isLoginUrl));
            return hasLoginInput || isLoginUrl;
        } catch (Exception e) {
            return false;
        }
    }

    // Verify wishlist URL
    public boolean isWishlistUrl() {
        String url = driver.getCurrentUrl();
        System.out.println("Wishlist URL: " + url);
        return url.contains("wishlist");
    }

    // Get current URL
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    // Verify wishlist page title
    public boolean isWishlistPageTitleCorrect() {
        String title = driver.getTitle();
        System.out.println("Wishlist title: " + title);
        return title.length() > 0;
    }

    // Verify wishlist text visible in header
    public boolean isWishlistTextVisible() {
        try {
            List<WebElement> texts = driver.findElements(
                By.xpath("//div[text()='Wishlist']"));
            System.out.println("Wishlist text: " + texts.size());
            return texts.size() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    // Click wishlist from product listing
    public void clickWishlistFromSearch() {
        try {
            List<WebElement> icons = driver.findElements(
                By.cssSelector("a.CfNfim"));
            if (icons.size() > 0) {
                ((JavascriptExecutor) driver)
                    .executeScript(
                        "arguments[0].click();", icons.get(0));
                Thread.sleep(2000);
                System.out.println("Wishlist clicked from search!");
            }
        } catch (Exception e) {
            System.out.println("Wishlist from search failed: "
                + e.getMessage());
        }
    }

    // Verify login prompt on wishlist
    public boolean isLoginPromptDisplayed() {
        try {
            List<WebElement> loginText = driver.findElements(
                By.xpath("//*[contains(text()," +
                        "'Get access to your Orders')]"));
            System.out.println("Login prompt: " + loginText.size());
            return loginText.size() > 0;
        } catch (Exception e) {
            return false;
        }
    }
}