package com.flipkart.pages;

import com.flipkart.utils.ConfigReader;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class HomePage {

    private WebDriver driver;
    private WebDriverWait wait;

    // ─── Locators ─────────────────────────────────────────────────

    @FindBy(css = "img[src*='rukminim']")
    private WebElement flipkartLogo;

    @FindBy(css = "form[action='/search'] input")
    private WebElement searchBox;

    @FindBy(css = "button[aria-label*='Search']")
    private WebElement searchButton;

    @FindBy(xpath = "//a[contains(text(),'Login')]")
    private WebElement loginButton;

    @FindBy(css = "span[role='button'].b3wTlE")
    private WebElement closeLoginPopup;

    @FindBy(css = "a[href*='/p/']")
    private List<WebElement> navCategories;

    @FindBy(css = "img[src*='rukminim']")
    private List<WebElement> bannerImages;

    @FindBy(css = "a[href*='/p/']")
    private List<WebElement> dealCards;

    @FindBy(css = "footer")
    private WebElement footer;

    @FindBy(css = "ul.LbQbAE li")
    private List<WebElement> suggestions;

    // ─── Constructor ──────────────────────────────────────────────

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver,
            Duration.ofSeconds(ConfigReader.getExplicitWait()));
        PageFactory.initElements(driver, this);
    }

    // ─── Actions ──────────────────────────────────────────────────

    public void closeLoginPopupIfPresent() {
        try {
            System.out.println("Checking for login popup...");
            Thread.sleep(3000);

            // Strategy 1 — exact locator
            try {
                WebElement closeBtn = driver.findElement(
                    By.cssSelector("span[role='button'].b3wTlE"));
                ((JavascriptExecutor) driver)
                    .executeScript("arguments[0].click();", closeBtn);
                System.out.println("Popup closed via Strategy 1!");
                Thread.sleep(1000);
                return;
            } catch (Exception e1) {
                System.out.println("Strategy 1 failed");
            }

            // Strategy 2 — by xpath text
            try {
                WebElement closeBtn = driver.findElement(
                    By.xpath("//span[@role='button' and text()='✕']"));
                ((JavascriptExecutor) driver)
                    .executeScript("arguments[0].click();", closeBtn);
                System.out.println("Popup closed via Strategy 2!");
                Thread.sleep(1000);
                return;
            } catch (Exception e2) {
                System.out.println("Strategy 2 failed");
            }

            // Strategy 3 — Escape key
            try {
                driver.findElement(By.tagName("body"))
                    .sendKeys(Keys.ESCAPE);
                System.out.println("Popup closed via Escape!");
                Thread.sleep(1000);
                return;
            } catch (Exception e3) {
                System.out.println("Strategy 3 failed");
            }

            System.out.println("No popup found — continuing...");

        } catch (Exception e) {
            System.out.println("Popup error: " + e.getMessage());
        }
    }

    public boolean isHomePageLoaded() {
        try {
            wait.until(ExpectedConditions.visibilityOf(searchBox));
            return searchBox.isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    public boolean isLogoVisible() {
        try {
            List<WebElement> logos = driver.findElements(
                By.cssSelector("img[src*='rukminim']"));
            System.out.println("Logo images found: " + logos.size());
            return logos.size() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isSearchBarVisible() {
        try {
            return searchBox.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isLoginButtonVisible() {
        try {
            wait.until(ExpectedConditions.visibilityOf(loginButton));
            return loginButton.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean areNavCategoriesVisible() {
        try {
            List<WebElement> links = driver.findElements(
                By.cssSelector("a[href*='/p/']"));
            System.out.println("Nav links found: " + links.size());
            return links.size() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    public int getNavCategoryCount() {
        return navCategories.size();
    }

    public boolean isBannerVisible() {
        try {
            ((JavascriptExecutor) driver)
                .executeScript("window.scrollTo(0, 0)");
            Thread.sleep(1000);
            List<WebElement> images = driver.findElements(
                By.cssSelector("img[src*='rukminim']"));
            System.out.println("Banner images found: " + images.size());
            return images.size() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean areDealsVisible() {
        try {
            ((JavascriptExecutor) driver)
                .executeScript("window.scrollTo(0, 400)");
            Thread.sleep(2000);
            List<WebElement> cards = driver.findElements(
                By.cssSelector("a[href*='/p/']"));
            System.out.println("Deal cards found: " + cards.size());
            return cards.size() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    public int getDealsCount() {
        return dealCards.size();
    }

    public boolean isFooterVisible() {
        try {
            ((JavascriptExecutor) driver)
                .executeScript(
                    "arguments[0].scrollIntoView(true);", footer);
            return footer.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getPageTitle() {
        return driver.getTitle();
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public void clickLogo() {
        try {
            wait.until(ExpectedConditions
                .elementToBeClickable(flipkartLogo));
            flipkartLogo.click();
        } catch (Exception e) {
            System.out.println("Logo click failed: " + e.getMessage());
        }
    }

    public void clickLoginButton() {
        try {
            wait.until(ExpectedConditions
                .elementToBeClickable(loginButton));
            loginButton.click();
        } catch (Exception e) {
            System.out.println("Login click failed: " + e.getMessage());
        }
    }

    public SearchResultsPage searchProduct(String keyword) {
        wait.until(ExpectedConditions
            .elementToBeClickable(searchBox));
        searchBox.clear();
        searchBox.sendKeys(keyword);
        searchButton.click();
        return new SearchResultsPage(driver);
    }

    public SearchResultsPage searchProductUsingEnter(String keyword) {
        wait.until(ExpectedConditions
            .elementToBeClickable(searchBox));
        searchBox.clear();
        searchBox.sendKeys(keyword);
        searchBox.sendKeys(Keys.ENTER);
        return new SearchResultsPage(driver);
    }

    public List<WebElement> getSearchSuggestions(String keyword) {
        wait.until(ExpectedConditions
            .elementToBeClickable(searchBox));
        searchBox.clear();
        searchBox.sendKeys(keyword);
        try {
            wait.until(ExpectedConditions
                .visibilityOfAllElements(suggestions));
        } catch (Exception e) {
            System.out.println("Suggestions not found");
        }
        return suggestions;
    }

    public void scrollToBottom() {
        ((JavascriptExecutor) driver)
            .executeScript(
                "window.scrollTo(0, document.body.scrollHeight)");
    }

    public void scrollToTop() {
        ((JavascriptExecutor) driver)
            .executeScript("window.scrollTo(0, 0)");
    }
}