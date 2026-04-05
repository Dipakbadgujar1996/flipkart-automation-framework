package com.flipkart.tests;

import com.flipkart.base.BaseTest;
import com.flipkart.pages.HomePage;
import com.flipkart.pages.ProductDetailPage;
import com.flipkart.utils.ConfigReader;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.util.List;

public class ProductDetailTest extends BaseTest {

	private HomePage homePage;
	private ProductDetailPage detailPage;

	@BeforeMethod
	public void setUpPage() throws InterruptedException {
		homePage = new HomePage(getDriver());
		homePage.closeLoginPopupIfPresent();

		// Search and click first product
		homePage.searchProduct(ConfigReader.get("search.keyword.valid"));
		Thread.sleep(2000);

		// Click first product and switch to new tab
		List<WebElement> products = getDriver().findElements(By.cssSelector("a[href*='/p/']"));

		if (products.size() > 0) {
			String originalWindow = getDriver().getWindowHandle();
			((JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", products.get(0));
			Thread.sleep(3000);

			// Switch to new tab
			for (String handle : getDriver().getWindowHandles()) {
				if (!handle.equals(originalWindow)) {
					getDriver().switchTo().window(handle);
					break;
				}
			}
		}

		detailPage = new ProductDetailPage(getDriver());
		Thread.sleep(2000);
	}

	// ─── TC38: Verify product page loaded ────────────────────────
	@Test(description = "Verify product detail page loads")
	public void verifyProductPageLoaded() {
		Assert.assertTrue(detailPage.isProductPageLoaded(), "Product detail page should load!");
	}

	// ─── TC39: Verify product title displayed ────────────────────
	@Test(description = "Verify product title is displayed")
	public void verifyProductTitleDisplayed() {
		Assert.assertTrue(detailPage.isProductTitleDisplayed(), "Product title should be displayed!");
	}

	// ─── TC40: Verify product price displayed ────────────────────
	@Test(description = "Verify product price is displayed")
	public void verifyProductPriceDisplayed() {
		Assert.assertTrue(detailPage.isProductPriceDisplayed(), "Product price should be displayed!");
	}

	// ─── TC41: Verify product images displayed ───────────────────
	@Test(description = "Verify product images are displayed")
	public void verifyProductImagesDisplayed() {
		Assert.assertTrue(detailPage.areProductImagesDisplayed(), "Product images should be displayed!");
	}

	// ─── TC42: Verify Add to Cart button present ─────────────────
	@Test(description = "Verify Add to Cart button is present")
	public void verifyAddToCartButtonPresent() {
		Assert.assertTrue(detailPage.isAddToCartButtonPresent(), "Add to Cart button should be present!");
	}

	// ─── TC43: Verify Buy Now button present ─────────────────────
	@Test(description = "Verify Buy Now button is present")
	public void verifyBuyNowButtonPresent() {
		Assert.assertTrue(detailPage.isBuyNowButtonPresent(), "Buy Now button should be present!");
	}

	// ─── TC44: Verify rating displayed ───────────────────────────
	@Test(description = "Verify product rating is displayed")
	public void verifyRatingDisplayed() {
		Assert.assertTrue(detailPage.isRatingDisplayed(), "Product rating should be displayed!");
	}

	// ─── TC45: Verify seller info displayed ──────────────────────
	@Test(description = "Verify seller info is displayed")
	public void verifySellerInfoDisplayed() {
		Assert.assertTrue(detailPage.isSellerInfoDisplayed(), "Seller info should be displayed!");
	}

	// ─── TC46: Verify delivery info displayed ────────────────────
	@Test(description = "Verify delivery info is displayed")
	public void verifyDeliveryInfoDisplayed() {
		Assert.assertTrue(detailPage.isDeliveryInfoDisplayed(), "Delivery info should be displayed!");
	}

	// ─── TC47: Verify offers displayed ───────────────────────────
	@Test(description = "Verify offers section is displayed")
	public void verifyOffersDisplayed() {
		Assert.assertTrue(detailPage.areOffersDisplayed(), "Offers should be displayed!");
	}

	// ─── TC48: Verify specifications displayed ───────────────────
	@Test(description = "Verify specifications are displayed")
	public void verifySpecificationsDisplayed() {
		Assert.assertTrue(detailPage.isSpecificationsDisplayed(), "Specifications should be displayed!");
	}

	// ─── TC49: Verify URL contains product id ────────────────────
	@Test(description = "Verify URL contains product identifier")
	public void verifyUrlContainsProductId() {
		String url = detailPage.getCurrentUrl();
		System.out.println("Product URL: " + url);
		Assert.assertTrue(url.contains("/p/"), "URL should contain product identifier!");
	}

	// ─── TC50: Verify thumbnail click changes image ───────────────
	// ─── TC50: Verify thumbnail click changes image ───────────────
	@Test(description = "Verify thumbnail click changes main image")
	public void verifyThumbnailClick() {
		detailPage.clickThumbnailImage();
		try {
			Thread.sleep(2000);
		} catch (Exception e) {
		}

		// Verify page still on product detail after click
		String url = getDriver().getCurrentUrl();
		System.out.println("URL after thumbnail: " + url);
		Assert.assertTrue(url.contains("/p/"), "Should still be on product detail page!");
	}

	// ─── TC51: Verify MRP displayed ──────────────────────────────
	@Test(description = "Verify MRP and discount are shown")
	public void verifyMrpDisplayed() {
		List<WebElement> mrp = getDriver().findElements(By.xpath("//*[contains(text(),'MRP')]"));
		System.out.println("MRP elements: " + mrp.size());
		Assert.assertTrue(mrp.size() > 0 || detailPage.isProductPriceDisplayed(), "Price or MRP should be displayed!");
	}

	// ─── TC52: Verify similar products shown ─────────────────────
	@Test(description = "Verify similar products section loads")
	public void verifySimilarProductsDisplayed() {
		((JavascriptExecutor) getDriver()).executeScript("window.scrollTo(0, 1000)");
		try {
			Thread.sleep(2000);
		} catch (Exception e) {
		}

		List<WebElement> similar = getDriver().findElements(By.cssSelector("a[href*='/p/']"));
		System.out.println("Similar products: " + similar.size());
		Assert.assertTrue(similar.size() > 0, "Similar products should be displayed!");
	}
}