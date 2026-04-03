package com.flipkart.pages;

import com.flipkart.utils.ConfigReader;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class ProductDetailPage {

	private WebDriver driver;
	private WebDriverWait wait;

	// ─── Locators ─────────────────────────────────────────────────

	// ✅ Product title — confirmed h1
	@FindBy(css = "h1")
	private WebElement productTitle;

	// ✅ Product price — confirmed class
	@FindBy(css = "div[class*='_1psv1ze2c']")
	private WebElement productPrice;

	// ✅ Product images — confirmed 43 found
	@FindBy(css = "img[src*='rukminim']")
	private List<WebElement> productImages;

	// ✅ Buy Now — confirmed div with text
	@FindBy(xpath = "//div[text()='Buy now']")
	private WebElement buyNowButton;

	// ✅ Add to Cart — confirmed div with text
	@FindBy(xpath = "//div[text()='Add to cart']")
	private WebElement addToCartButton;

	// ✅ Rating — confirmed class
	@FindBy(css = "div.css-146c3p1")
	private List<WebElement> ratingElements;

	// ─── Constructor ──────────────────────────────────────────────

	public ProductDetailPage(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(ConfigReader.getExplicitWait()));
		PageFactory.initElements(driver, this);
	}

	// ─── Actions ──────────────────────────────────────────────────

	public boolean isProductTitleDisplayed() {
		try {
			wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("h1")));
			List<WebElement> titles = driver.findElements(By.cssSelector("h1"));
			System.out.println("Title found: " + titles.get(0).getText().substring(0, 30));
			return titles.size() > 0 && titles.get(0).getText().length() > 0;
		} catch (Exception e) {
			System.out.println("Title not found: " + e.getMessage());
			return false;
		}
	}

	public String getProductTitle() {
		try {
			List<WebElement> titles = driver.findElements(By.cssSelector("h1"));
			if (titles.size() > 0) {
				return titles.get(0).getText();
			}
		} catch (Exception e) {
			System.out.println("Get title failed: " + e.getMessage());
		}
		return "";
	}

	public boolean isProductPriceDisplayed() {
		try {
			// Scroll to make price visible
			((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 200)");
			Thread.sleep(1000);

			// Find price using ₹ symbol
			List<WebElement> prices = driver
					.findElements(By.xpath("//*[starts-with(" + "normalize-space(text()),'₹')]"));
			System.out.println("Prices found: " + prices.size());
			return prices.size() > 0;
		} catch (Exception e) {
			System.out.println("Price not found: " + e.getMessage());
			return false;
		}
	}

	public boolean areProductImagesDisplayed() {
		try {
			List<WebElement> images = driver.findElements(By.cssSelector("img[src*='rukminim']"));
			System.out.println("Images found: " + images.size());
			return images.size() > 0;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean isAddToCartButtonPresent() {
		try {
			((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 300)");
			Thread.sleep(1500);

			// Flipkart shows "Cart" not "Add to cart"
			List<WebElement> btns = driver.findElements(By.xpath("//*[text()='Cart' or " + "text()='Add to cart' or "
					+ "text()='ADD TO CART' or " + "text()='Add To Cart']"));
			System.out.println("Cart button found: " + btns.size());
			return btns.size() > 0;
		} catch (Exception e) {
			System.out.println("Cart button error: " + e.getMessage());
			return false;
		}
	}

	public boolean isBuyNowButtonPresent() {
		try {
			((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 300)");
			Thread.sleep(1500);
			List<WebElement> btns = driver.findElements(By.xpath("//*[text()='Buy now']"));
			System.out.println("Buy Now found: " + btns.size());
			return btns.size() > 0;
		} catch (Exception e) {
			System.out.println("Buy Now not found: " + e.getMessage());
			return false;
		}
	}

	public boolean isRatingDisplayed() {
		try {
			// Rating is div.css-146c3p1 with text like "4.6"
			List<WebElement> elements = driver.findElements(By.cssSelector("div.css-146c3p1"));
			for (WebElement el : elements) {
				String text = el.getText().trim();
				if (text.matches("[0-9]\\.[0-9]")) {
					System.out.println("Rating found: " + text);
					return true;
				}
			}
			System.out.println("Rating not found in elements");
			return false;
		} catch (Exception e) {
			System.out.println("Rating error: " + e.getMessage());
			return false;
		}
	}

	public boolean isSellerInfoDisplayed() {
		try {
			List<WebElement> sellers = driver
					.findElements(By.xpath("//*[contains(text(),'Sold by') or " + "contains(text(),'Seller')]"));
			System.out.println("Seller info found: " + sellers.size());
			return sellers.size() > 0;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean isDeliveryInfoDisplayed() {
		try {
			List<WebElement> delivery = driver
					.findElements(By.xpath("//*[contains(text(),'Delivery') or " + "contains(text(),'delivery')]"));
			System.out.println("Delivery info found: " + delivery.size());
			return delivery.size() > 0;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean areOffersDisplayed() {
		try {
			List<WebElement> offers = driver
					.findElements(By.xpath("//*[contains(text(),'offer') or " + "contains(text(),'Offer')]"));
			System.out.println("Offers found: " + offers.size());
			return offers.size() > 0;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean isSpecificationsDisplayed() {
		try {
			// Scroll down to load specifications
			((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 800)");
			Thread.sleep(2000);

			// Specifications is div with exact text
			List<WebElement> specs = driver.findElements(By.xpath("//*[text()='Specifications' or "
					+ "text()='SPECIFICATIONS' or " + "contains(text(),'Specifications')]"));
			System.out.println("Specs found: " + specs.size());
			return specs.size() > 0;
		} catch (Exception e) {
			System.out.println("Specs error: " + e.getMessage());
			return false;
		}
	}

	public String getCurrentUrl() {
		return driver.getCurrentUrl();
	}

	public boolean isProductPageLoaded() {
		try {
			wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("h1")));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public void clickThumbnailImage() {
		try {
			List<WebElement> images = driver.findElements(By.cssSelector("img[src*='rukminim']"));
			if (images.size() > 1) {
				images.get(1).click();
				Thread.sleep(1000);
				System.out.println("Thumbnail clicked!");
			}
		} catch (Exception e) {
			System.out.println("Thumbnail click failed: " + e.getMessage());
		}
	}
}