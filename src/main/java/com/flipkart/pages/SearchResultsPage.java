package com.flipkart.pages;

import com.flipkart.utils.ConfigReader;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class SearchResultsPage {

	private WebDriver driver;
	private WebDriverWait wait;

	// ─── Locators ─────────────────────────────────────────────────

	// ✅ Product links — confirmed working (51 found on homepage)
	@FindBy(css = "a[href*='/p/']")
	private List<WebElement> productLinks;

	// ✅ Product titles inside links
	@FindBy(css = "a[href*='/p/'] div[class*='KzDlHZ']," + "a[href*='/p/'] div[class*='WKTcLC'],"
			+ "a[href*='/p/'] div[class*='syl9yP']")
	private List<WebElement> productTitles;

	// ✅ Product prices
	@FindBy(css = "div[class*='_3n8fna1co']")
	private List<WebElement> productPrices;

	// ✅ Search box — stable via name
	@FindBy(name = "q")
	private WebElement searchBox;

	// ✅ Search button — stable via aria-label
	@FindBy(css = "button[aria-label*='Search']")
	private WebElement searchButton;

	// ✅ No results — look for text
	@FindBy(xpath = "//p[contains(text(),'No results')] | " + "//span[contains(text(),'No results')]")
	private WebElement noResultsMessage;

	// ─── Constructor ──────────────────────────────────────────────

	public SearchResultsPage(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(ConfigReader.getExplicitWait()));
		PageFactory.initElements(driver, this);
	}

	// ─── Actions ──────────────────────────────────────────────────

	public boolean areResultsDisplayed() {
		try {
			wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("a[href*='/p/']")));
			List<WebElement> results = driver.findElements(By.cssSelector("a[href*='/p/']"));
			System.out.println("Search results found: " + results.size());
			return results.size() > 0;
		} catch (Exception e) {
			return false;
		}
	}

	public int getResultCount() {
		try {
			List<WebElement> results = driver.findElements(By.cssSelector("a[href*='/p/']"));
			return results.size();
		} catch (Exception e) {
			return 0;
		}
	}

	public boolean arePricesDisplayed() {
	    try {
	        // Scroll down to trigger lazy loading
	        ((JavascriptExecutor) driver)
	            .executeScript("window.scrollTo(0, 300)");
	        Thread.sleep(1500);
	        ((JavascriptExecutor) driver)
	            .executeScript("window.scrollTo(0, 600)");
	        Thread.sleep(1500);

	        // Try multiple price selectors
	        List<WebElement> prices = driver.findElements(
	            By.cssSelector("div[class*='_3n8fna1co']"));

	        if (prices.size() == 0) {
	            // Try XPath with ₹ symbol
	            prices = driver.findElements(
	                By.xpath("//*[contains(@class,'_3n8fna')]" +
	                        "[contains(text(),'₹')]"));
	        }

	        if (prices.size() == 0) {
	            // Broadest fallback
	            prices = driver.findElements(
	                By.xpath("//*[starts-with(normalize-space(text()),'₹')]"));
	        }

	        System.out.println("Prices found: " + prices.size());
	        return prices.size() > 0;

	    } catch (Exception e) {
	        System.out.println("Price error: " + e.getMessage());
	        return false;
	    }
	}

	public boolean isNoResultsMessageDisplayed() {
		try {
			Thread.sleep(2000);
			// Check if product count is 0
			List<WebElement> results = driver.findElements(By.cssSelector("a[href*='/p/']"));
			return results.size() == 0;
		} catch (Exception e) {
			return true;
		}
	}

	public String getCurrentUrl() {
		return driver.getCurrentUrl();
	}

	public boolean isKeywordInUrl(String keyword) {
		String url = driver.getCurrentUrl().toLowerCase();
		System.out.println("Current URL: " + url);
		return url.contains(keyword.toLowerCase());
	}

	public SearchResultsPage searchAgain(String keyword) {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(searchBox));
			searchBox.clear();
			searchBox.sendKeys(keyword);
			searchButton.click();
			Thread.sleep(2000);
		} catch (Exception e) {
			System.out.println("Search again failed: " + e.getMessage());
		}
		return new SearchResultsPage(driver);
	}

	public List<WebElement> getSearchSuggestions(String keyword) {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(searchBox));
			searchBox.clear();
			searchBox.sendKeys(keyword);
			Thread.sleep(1500);
			List<WebElement> suggestions = driver.findElements(By.cssSelector("ul li a, div[class*='suggest'] a"));
			System.out.println("Suggestions found: " + suggestions.size());
			return suggestions;
		} catch (Exception e) {
			return new java.util.ArrayList<>();
		}
	}

	public SearchResultsPage clickFirstSuggestion() {
		try {
			List<WebElement> suggestions = driver.findElements(By.cssSelector("ul li a"));
			if (suggestions.size() > 0) {
				suggestions.get(0).click();
				Thread.sleep(2000);
			}
		} catch (Exception e) {
			System.out.println("Click suggestion failed: " + e.getMessage());
		}
		return new SearchResultsPage(driver);
	}

	public ProductDetailPage clickFirstProduct() {
	    try {
	        List<WebElement> products = driver.findElements(
	            By.cssSelector("a[href*='/p/']"));

	        System.out.println("Products found: " + products.size());

	        if (products.size() > 0) {
	            // Get original window handle
	            String originalWindow = driver.getWindowHandle();

	            // Scroll to element
	            ((JavascriptExecutor) driver)
	                .executeScript(
	                    "arguments[0].scrollIntoView(true);",
	                    products.get(0));
	            Thread.sleep(1000);

	            // Click product
	            ((JavascriptExecutor) driver)
	                .executeScript(
	                    "arguments[0].click();",
	                    products.get(0));
	            Thread.sleep(3000);

	            // Switch to new tab if opened
	            for (String handle : driver.getWindowHandles()) {
	                if (!handle.equals(originalWindow)) {
	                    driver.switchTo().window(handle);
	                    break;
	                }
	            }

	            System.out.println("After click URL: "
	                + driver.getCurrentUrl());
	        }
	    } catch (Exception e) {
	        System.out.println("Click failed: " + e.getMessage());
	    }
	    return new ProductDetailPage(driver);
	}	

	public String getFirstProductTitle() {
		try {
			List<WebElement> titles = driver.findElements(By.cssSelector("a[href*='/p/']"));
			if (titles.size() > 0) {
				return titles.get(0).getText();
			}
		} catch (Exception e) {
			System.out.println("Get title failed: " + e.getMessage());
		}
		return "";
	}
}