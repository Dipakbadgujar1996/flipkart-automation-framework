package com.flipkart.tests;

import com.flipkart.base.BaseTest;
import com.flipkart.pages.HomePage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class HomePageTest extends BaseTest {

	private HomePage homePage;

	// ─── Setup ────────────────────────────────────────────────────
	@BeforeMethod
	public void setUpPage() {
		homePage = new HomePage(getDriver());
		homePage.closeLoginPopupIfPresent();
	}

	// ─── TC01: Verify homepage loads successfully ─────────────────
	@Test(description = "Verify homepage loads successfully")
	public void verifyHomePageLoads() {
		Assert.assertTrue(homePage.isHomePageLoaded(), "Homepage did not load successfully!");
	}

	// ─── TC02: Verify Flipkart logo is visible ────────────────────
	@Test(description = "Verify Flipkart logo is visible")
	public void verifyLogoIsVisible() {
		Assert.assertTrue(homePage.isLogoVisible(), "Flipkart logo is not visible!");
	}

	// ─── TC03: Verify search bar is present ──────────────────────
	@Test(description = "Verify search bar is present")
	public void verifySearchBarIsVisible() {
		Assert.assertTrue(homePage.isSearchBarVisible(), "Search bar is not visible!");
	}

	// ─── TC04: Verify login button is visible ────────────────────
	@Test(description = "Verify login button is visible")
	public void verifyLoginButtonIsVisible() {
		Assert.assertTrue(homePage.isLoginButtonVisible(), "Login button is not visible!");
	}

	// ─── TC05: Verify nav categories are displayed ───────────────
	@Test(description = "Verify top navigation categories are displayed")
	public void verifyNavCategoriesAreDisplayed() {
		Assert.assertTrue(homePage.areNavCategoriesVisible(), "Navigation categories are not visible!");
		System.out.println("Total nav categories found: " + homePage.getNavCategoryCount());
	}

	// ─── TC06: Verify banner is visible ──────────────────────────
	@Test(description = "Verify banner/carousel is visible")
	public void verifyBannerIsVisible() {
		Assert.assertTrue(homePage.isBannerVisible(), "Banner carousel is not visible!");
	}

	// ─── TC07: Verify deals section is visible ───────────────────
	@Test(description = "Verify Today's Deals section is visible")
	public void verifyDealsAreVisible() {
		Assert.assertTrue(homePage.areDealsVisible(), "Deals section is not visible!");
		System.out.println("Total deals found: " + homePage.getDealsCount());
	}

	// ─── TC08: Verify footer is present ──────────────────────────
	@Test(description = "Verify footer is present")
	public void verifyFooterIsVisible() {
		homePage.scrollToBottom();
		Assert.assertTrue(homePage.isFooterVisible(), "Footer is not visible!");
	}

	// ─── TC09: Verify page title ──────────────────────────────────
	@Test(description = "Verify page title is correct")
	public void verifyPageTitle() {
	    String title = homePage.getPageTitle();
	    System.out.println("Page title: " + title);
	    Assert.assertTrue(
	        title.contains("Online Shopping"),
	        "Page title wrong! Actual: " + title
	    );
	}

	// ─── TC10: Verify logo click goes to homepage ─────────────────
	@Test(description = "Verify Flipkart logo click redirects to homepage")
	public void verifyLogoClickRedirectsToHomePage() {
		homePage.clickLogo();
		Assert.assertTrue(getDriver().getCurrentUrl().contains("flipkart.com"),
				"Logo click did not redirect to homepage!");
	}
}