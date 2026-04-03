package com.flipkart.tests;

import com.flipkart.base.BaseTest;
import com.flipkart.pages.HomePage;
import com.flipkart.pages.ProductListingPage;
import com.flipkart.utils.ConfigReader;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ProductListingTest extends BaseTest {

    private HomePage homePage;
    private ProductListingPage listingPage;

    @BeforeMethod
    public void setUpPage() {
        homePage = new HomePage(getDriver());
        homePage.closeLoginPopupIfPresent();
        homePage.searchProduct(
            ConfigReader.get("search.keyword.valid"));
        listingPage = new ProductListingPage(getDriver());
    }

    // ─── TC23: Verify products display ───────────────────────────
    @Test(description = "Verify products display on listing page")
    public void verifyProductsDisplay() {
        Assert.assertTrue(
            listingPage.areProductsDisplayed(),
            "Products should be displayed!"
        );
    }

    // ─── TC24: Verify product images loaded ──────────────────────
    @Test(description = "Verify product images are loaded")
    public void verifyProductImagesLoaded() {
        Assert.assertTrue(
            listingPage.areProductImagesLoaded(),
            "Product images should be loaded!"
        );
    }

    // ─── TC25: Verify prices displayed ───────────────────────────
    @Test(description = "Verify product prices are displayed")
    public void verifyPricesDisplayed() {
        Assert.assertTrue(
            listingPage.arePricesDisplayed(),
            "Prices should be displayed!"
        );
    }

    // ─── TC26: Sort by Low to High ───────────────────────────────
    @Test(description = "Sort by Price Low to High")
    public void verifySortByLowToHigh() {
        int before = listingPage.getProductCount();
        listingPage.sortByLowToHigh();
        int after = listingPage.getProductCount();
        System.out.println("Before: " + before
            + " After: " + after);
        Assert.assertTrue(after > 0,
            "Products should display after sort!");
    }

    // ─── TC27: Sort by High to Low ───────────────────────────────
    @Test(description = "Sort by Price High to Low")
    public void verifySortByHighToLow() {
        listingPage.sortByHighToLow();
        Assert.assertTrue(
            listingPage.areProductsDisplayed(),
            "Products should display after sort!"
        );
    }

    // ─── TC28: Sort by Popularity ────────────────────────────────
    @Test(description = "Sort by Popularity")
    public void verifySortByPopularity() {
        listingPage.sortByPopularity();
        Assert.assertTrue(
            listingPage.areProductsDisplayed(),
            "Products should display after sort!"
        );
    }

    // ─── TC29: Sort by Newest First ──────────────────────────────
    @Test(description = "Sort by Newest First")
    public void verifySortByNewestFirst() {
        listingPage.sortByNewestFirst();
        Assert.assertTrue(
            listingPage.areProductsDisplayed(),
            "Products should display after sort!"
        );
    }

    // ─── TC30: Apply brand filter ────────────────────────────────
    @Test(description = "Apply brand filter and verify results")
    public void verifyBrandFilterApplied() {
        int before = listingPage.getProductCount();
        listingPage.applyFirstBrandFilter();
        int after = listingPage.getProductCount();
        System.out.println("Before: " + before
            + " After: " + after);
        Assert.assertTrue(after > 0,
            "Products should display after filter!"
        );
    }

    // ─── TC31: Apply rating filter ───────────────────────────────
    @Test(description = "Apply rating filter and verify results")
    public void verifyRatingFilterApplied() {
        listingPage.applyFirstRatingFilter();
        Assert.assertTrue(
            listingPage.areProductsDisplayed(),
            "Products should display after rating filter!"
        );
    }

    // ─── TC32: Apply price filter ────────────────────────────────
    @Test(description = "Apply price filter and verify results")
    public void verifyPriceFilterApplied() {
        listingPage.applyFirstPriceFilter();
        Assert.assertTrue(
            listingPage.areProductsDisplayed(),
            "Products should display after price filter!"
        );
    }

    // ─── TC33: Apply multiple filters ────────────────────────────
    @Test(description = "Apply multiple filters simultaneously")
    public void verifyMultipleFiltersApplied() {
        listingPage.applyMultipleFilters();
        Assert.assertTrue(
            listingPage.areProductsDisplayed(),
            "Products should display after multiple filters!"
        );
    }

    // ─── TC34: Clear all filters ─────────────────────────────────
    @Test(description = "Clear all filters and verify reset")
    public void verifyClearAllFilters() {
        listingPage.applyFirstBrandFilter();
        listingPage.clearAllFilters();
        Assert.assertTrue(
            listingPage.areProductsDisplayed(),
            "Products should display after clearing filters!"
        );
    }

    // ─── TC35: Verify pagination ─────────────────────────────────
    @Test(description = "Verify pagination works")
    public void verifyPagination() {
        String pageInfo = listingPage.getCurrentPageInfo();
        System.out.println("Page info: " + pageInfo);
        Assert.assertTrue(
            pageInfo.contains("Page"),
            "Page info should be displayed!"
        );
    }

    // ─── TC36: Go to next page ───────────────────────────────────
    @Test(description = "Go to next page and verify products")
    public void verifyNextPage() {
        listingPage.goToNextPage();
        Assert.assertTrue(
            listingPage.areProductsDisplayed(),
            "Products should display on next page!"
        );
    }

    // ─── TC37: Click product opens detail page ───────────────────
    @Test(description = "Click product opens detail page")
    public void verifyClickProductOpensDetailPage() {
        String beforeUrl = listingPage.getCurrentUrl();
        listingPage.clickFirstProduct();
        String afterUrl = getDriver().getCurrentUrl();
        System.out.println("Before: " + beforeUrl);
        System.out.println("After: " + afterUrl);
        Assert.assertNotEquals(beforeUrl, afterUrl,
            "URL should change after clicking product!");
    }
}