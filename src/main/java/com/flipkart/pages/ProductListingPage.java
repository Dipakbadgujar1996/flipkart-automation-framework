package com.flipkart.pages;

import com.flipkart.utils.ConfigReader;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class ProductListingPage {

	private WebDriver driver;
	private WebDriverWait wait;

	// ─── Locators ─────────────────────────────────────────────────

	// ✅ Product links — confirmed 29 found
	@FindBy(css = "a[href*='/p/']")
	private List<WebElement> productLinks;

	// ✅ Product prices — confirmed working
	@FindBy(css = "div[class*='_3n8fna1co']")
	private List<WebElement> productPrices;

	// ✅ Filter checkboxes — confirmed 45 found
	@FindBy(css = "input[type='checkbox']")
	private List<WebElement> filterCheckboxes;

	// ✅ Next page button — confirmed working
	@FindBy(xpath = "//span[contains(text(),'Next')]/..")
	private WebElement nextPageButton;

	// ✅ Current page info
	@FindBy(xpath = "//span[contains(text(),'Page')]")
	private WebElement currentPageInfo;

	// ✅ Sort options — will update after Console output
	@FindBy(css = "div.WNv7PR")
	private List<WebElement> sortOptions;

	// ─── Constructor ──────────────────────────────────────────────

	public ProductListingPage(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(ConfigReader.getExplicitWait()));
		PageFactory.initElements(driver, this);
	}

	// ─── Actions ──────────────────────────────────────────────────

	public boolean areProductsDisplayed() {
		try {
			wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("a[href*='/p/']")));
			List<WebElement> products = driver.findElements(By.cssSelector("a[href*='/p/']"));
			System.out.println("Products found: " + products.size());
			return products.size() > 0;
		} catch (Exception e) {
			return false;
		}
	}

	public int getProductCount() {
		try {
			return driver.findElements(By.cssSelector("a[href*='/p/']")).size();
		} catch (Exception e) {
			return 0;
		}
	}

	public boolean arePricesDisplayed() {
		try {
			((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 500)");
			Thread.sleep(1500);
			List<WebElement> prices = driver.findElements(By.xpath("//*[starts-with(normalize-space(text()),'₹')]"));
			System.out.println("Prices found: " + prices.size());
			return prices.size() > 0;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean areProductImagesLoaded() {
		try {
			List<WebElement> images = driver.findElements(By.cssSelector("a[href*='/p/'] img"));
			System.out.println("Images found: " + images.size());
			return images.size() > 0;
		} catch (Exception e) {
			return false;
		}
	}

	public ProductListingPage sortByLowToHigh() {
		try {
			WebElement sortOption = driver.findElement(By.xpath("//div[text()='Price -- Low to High']"));
			sortOption.click();
			Thread.sleep(2000);
			System.out.println("Sorted Low to High!");
		} catch (Exception e) {
			System.out.println("Sort failed: " + e.getMessage());
		}
		return this;
	}

	public ProductListingPage sortByHighToLow() {
		try {
			WebElement sortOption = driver.findElement(By.xpath("//div[text()='Price -- High to Low']"));
			sortOption.click();
			Thread.sleep(2000);
			System.out.println("Sorted High to Low!");
		} catch (Exception e) {
			System.out.println("Sort failed: " + e.getMessage());
		}
		return this;
	}

	public ProductListingPage sortByPopularity() {
		try {
			WebElement sortOption = driver.findElement(By.xpath("//div[text()='Popularity']"));
			sortOption.click();
			Thread.sleep(2000);
			System.out.println("Sorted by Popularity!");
		} catch (Exception e) {
			System.out.println("Sort failed: " + e.getMessage());
		}
		return this;
	}

	public ProductListingPage sortByNewestFirst() {
		try {
			WebElement sortOption = driver.findElement(By.xpath("//div[text()='Newest First']"));
			sortOption.click();
			Thread.sleep(2000);
			System.out.println("Sorted Newest First!");
		} catch (Exception e) {
			System.out.println("Sort failed: " + e.getMessage());
		}
		return this;
	}

	public ProductListingPage applyFirstBrandFilter() {
		try {
			List<WebElement> checkboxes = driver.findElements(By.cssSelector("input[type='checkbox']"));
			if (checkboxes.size() > 0) {
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", checkboxes.get(0));
				Thread.sleep(2000);
				System.out.println("Brand filter applied!");
			}
		} catch (Exception e) {
			System.out.println("Brand filter failed: " + e.getMessage());
		}
		return this;
	}

	public ProductListingPage applyFirstRatingFilter() {
		try {
			List<WebElement> checkboxes = driver.findElements(By.cssSelector("input[type='checkbox']"));
			if (checkboxes.size() > 3) {
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", checkboxes.get(3));
				Thread.sleep(2000);
				System.out.println("Rating filter applied!");
			}
		} catch (Exception e) {
			System.out.println("Rating filter failed: " + e.getMessage());
		}
		return this;
	}

	public ProductListingPage applyFirstPriceFilter() {
		try {
			List<WebElement> checkboxes = driver.findElements(By.cssSelector("input[type='checkbox']"));
			if (checkboxes.size() > 1) {
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", checkboxes.get(1));
				Thread.sleep(2000);
				System.out.println("Price filter applied!");
			}
		} catch (Exception e) {
			System.out.println("Price filter failed: " + e.getMessage());
		}
		return this;
	}

	public ProductListingPage applyMultipleFilters() {
		applyFirstBrandFilter();
		applyFirstPriceFilter();
		return this;
	}

	public ProductListingPage clearAllFilters() {
		try {
			WebElement clearBtn = driver
					.findElement(By.xpath("//span[contains(text(),'Clear All') or " + "contains(text(),'CLEAR ALL')]"));
			clearBtn.click();
			Thread.sleep(2000);
			System.out.println("Filters cleared!");
		} catch (Exception e) {
			System.out.println("Clear filters failed: " + e.getMessage());
		}
		return this;
	}

	public ProductListingPage goToNextPage() {
		try {
			WebElement nextBtn = driver.findElement(By.xpath("//span[contains(text(),'Next')]/.."));
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", nextBtn);
			Thread.sleep(3000);
			System.out.println("Navigated to next page!");
		} catch (Exception e) {
			System.out.println("Next page failed: " + e.getMessage());
		}
		return this;
	}

	public String getCurrentPageInfo() {
		try {
			WebElement pageInfo = driver.findElement(By.xpath("//span[contains(text(),'Page')]"));
			return pageInfo.getText();
		} catch (Exception e) {
			return "Page info not found";
		}
	}

	public boolean isNoResultsMessageDisplayed() {
		try {
			List<WebElement> results = driver.findElements(By.cssSelector("a[href*='/p/']"));
			return results.size() == 0;
		} catch (Exception e) {
			return true;
		}
	}

	public ProductDetailPage clickFirstProduct() {
		try {
			List<WebElement> products = driver.findElements(By.cssSelector("a[href*='/p/']"));
			if (products.size() > 0) {
				String originalWindow = driver.getWindowHandle();
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", products.get(0));
				Thread.sleep(1000);
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", products.get(0));
				Thread.sleep(3000);
				for (String handle : driver.getWindowHandles()) {
					if (!handle.equals(originalWindow)) {
						driver.switchTo().window(handle);
						break;
					}
				}
			}
		} catch (Exception e) {
			System.out.println("Click product failed: " + e.getMessage());
		}
		return new ProductDetailPage(driver);
	}

	public String getFirstProductTitle() {
		try {
			List<WebElement> products = driver.findElements(By.cssSelector("a[href*='/p/']"));
			if (products.size() > 0) {
				return products.get(0).getText();
			}
		} catch (Exception e) {
			System.out.println("Get title failed: " + e.getMessage());
		}
		return "";
	}

	public String getCurrentUrl() {
		return driver.getCurrentUrl();
	}
}