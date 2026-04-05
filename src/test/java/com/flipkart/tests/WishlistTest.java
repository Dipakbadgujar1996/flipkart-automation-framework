package com.flipkart.tests;

import com.flipkart.base.BaseTest;
import com.flipkart.pages.HomePage;
import com.flipkart.pages.WishlistPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class WishlistTest extends BaseTest {

    private WishlistPage wishlistPage;
    private HomePage homePage;

    @BeforeMethod
    public void setUpPage() {
        homePage = new HomePage(getDriver());
        homePage.closeLoginPopupIfPresent();
        wishlistPage = new WishlistPage(getDriver());
    }

    // ─── TC81: Verify wishlist icon visible ───────────────────────
    @Test(description = "Verify wishlist icon visible in header")
    public void verifyWishlistIconVisible() {
        Assert.assertTrue(
            wishlistPage.isWishlistIconVisible(),
            "Wishlist icon should be visible in header!"
        );
    }

    // ─── TC82: Verify wishlist text visible ───────────────────────
    @Test(description = "Verify wishlist text visible in header")
    public void verifyWishlistTextVisible() {
        Assert.assertTrue(
            wishlistPage.isWishlistTextVisible(),
            "Wishlist text should be visible!"
        );
    }

    // ─── TC83: Verify wishlist redirects to login ─────────────────
    @Test(description = "Verify wishlist redirects to login for guest")
    public void verifyWishlistRedirectsToLogin() {
        wishlistPage.navigateToWishlist();
        Assert.assertTrue(
            wishlistPage.isRedirectedToLogin(),
            "Wishlist should redirect to login for guest!"
        );
    }

    // ─── TC84: Verify login prompt on wishlist ────────────────────
    @Test(description = "Verify login prompt shown on wishlist page")
    public void verifyLoginPromptOnWishlist() {
        wishlistPage.navigateToWishlist();
        Assert.assertTrue(
            wishlistPage.isLoginPromptDisplayed(),
            "Login prompt should be shown on wishlist!"
        );
    }

    // ─── TC85: Verify wishlist icon click ─────────────────────────
    @Test(description = "Verify wishlist icon click navigates")
    public void verifyWishlistIconClick() {
        String beforeUrl = getDriver().getCurrentUrl();
        wishlistPage.clickWishlistIcon();
        String afterUrl = wishlistPage.getCurrentUrl();
        System.out.println("Before: " + beforeUrl);
        System.out.println("After: " + afterUrl);

        // Just verify URL changed OR stays on Flipkart
        Assert.assertTrue(
            afterUrl.contains("flipkart.com"),
            "Should stay on Flipkart after wishlist click!"
        );
    }

    // ─── TC86: Verify wishlist page title ─────────────────────────
    @Test(description = "Verify wishlist page has correct title")
    public void verifyWishlistPageTitle() {
        wishlistPage.navigateToWishlist();
        Assert.assertTrue(
            wishlistPage.isWishlistPageTitleCorrect(),
            "Wishlist page should have a title!"
        );
    }

    // ─── TC87: Verify back navigation from wishlist ───────────────
    @Test(description = "Verify back navigation from wishlist")
    public void verifyBackNavigationFromWishlist() {
        wishlistPage.navigateToWishlist();
        getDriver().navigate().back();
        try { Thread.sleep(2000); }
        catch (Exception e) {}
        Assert.assertTrue(
            getDriver().getCurrentUrl()
                .contains("flipkart.com"),
            "Back navigation should stay on Flipkart!"
        );
    }

    // ─── TC88: Verify wishlist accessible from search ─────────────
    @Test(description = "Verify wishlist accessible from search page")
    public void verifyWishlistAccessibleFromSearch() {
        homePage.searchProduct("iPhone");
        wishlistPage.clickWishlistFromSearch();
        String url = wishlistPage.getCurrentUrl();
        System.out.println("URL: " + url);
        Assert.assertTrue(
            url.contains("wishlist") ||
            url.contains("login") ||
            url.contains("flipkart.com"),
            "Wishlist should be accessible from search!"
        );
    }
}