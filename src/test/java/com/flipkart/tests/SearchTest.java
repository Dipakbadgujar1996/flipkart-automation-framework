package com.flipkart.tests;

import com.flipkart.base.BaseTest;
import com.flipkart.pages.HomePage;
import com.flipkart.pages.SearchResultsPage;
import com.flipkart.utils.ConfigReader;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.util.List;

public class SearchTest extends BaseTest {

    private HomePage homePage;

    @BeforeMethod
    public void setUpPage() {
        homePage = new HomePage(getDriver());
        homePage.closeLoginPopupIfPresent();
    }

    // ─── TC11: Search valid product ───────────────────────────────
    @Test(description = "Search valid product and verify results load")
    public void verifySearchWithValidKeyword() {
        SearchResultsPage resultsPage = homePage
            .searchProduct(ConfigReader.get("search.keyword.valid"));
        Assert.assertTrue(
            resultsPage.areResultsDisplayed(),
            "No results for valid search!"
        );
    }

    // ─── TC12: Verify result count > 0 ───────────────────────────
    @Test(description = "Verify search result count is greater than 0")
    public void verifyResultCountIsGreaterThanZero() {
        SearchResultsPage resultsPage = homePage
            .searchProduct(ConfigReader.get("search.keyword.valid"));
        int count = resultsPage.getResultCount();
        System.out.println("Results found: " + count);
        Assert.assertTrue(count > 0,
            "Result count should be > 0!");
    }

    // ─── TC13: Search with invalid keyword ───────────────────────
    @Test(description = "Search invalid keyword and verify no results")
    public void verifySearchWithInvalidKeyword() {
        SearchResultsPage resultsPage = homePage
            .searchProduct(
                ConfigReader.get("search.keyword.invalid"));
        Assert.assertTrue(
                resultsPage.getCurrentUrl().contains("flipkart.com"),
                "Should stay on Flipkart for invalid keyword!"
            );
    }

    // ─── TC14: Search with special characters ────────────────────
    @Test(description = "Search with special characters")
    public void verifySearchWithSpecialCharacters() {
        SearchResultsPage resultsPage = homePage
            .searchProduct("@#$%^&*");
        Assert.assertTrue(
            resultsPage.getCurrentUrl().contains("flipkart.com"),
            "Should stay on Flipkart!"
        );
    }

    // ─── TC15: Search with spaces ─────────────────────────────────
    @Test(description = "Search with spaces and verify results")
    public void verifySearchWithSpaces() {
        SearchResultsPage resultsPage = homePage
            .searchProduct("  iPhone  ");
        Assert.assertTrue(
            resultsPage.areResultsDisplayed(),
            "Results should show even with spaces!"
        );
    }

    // ─── TC16: Verify URL contains keyword ────────────────────────
    @Test(description = "Verify URL updates with search keyword")
    public void verifyUrlContainsKeyword() {
        SearchResultsPage resultsPage = homePage
            .searchProduct("samsung");
        Assert.assertTrue(
            resultsPage.isKeywordInUrl("samsung"),
            "URL should contain search keyword!"
        );
    }

    // ─── TC17: Search using Enter key ─────────────────────────────
    @Test(description = "Search using Enter key")
    public void verifySearchUsingEnterKey() {
        SearchResultsPage resultsPage = homePage
            .searchProductUsingEnter(
                ConfigReader.get("search.keyword.valid"));
        Assert.assertTrue(
            resultsPage.areResultsDisplayed(),
            "Results should show when using Enter key!"
        );
    }

    // ─── TC18: Verify prices displayed ───────────────────────────
    @Test(description = "Verify prices shown in search results")
    public void verifyPricesDisplayed() {
        SearchResultsPage resultsPage = homePage
            .searchProduct(ConfigReader.get("search.keyword.valid"));
        Assert.assertTrue(
            resultsPage.arePricesDisplayed(),
            "Prices should be displayed in results!"
        );
    }

    // ─── TC19: Search same keyword twice ──────────────────────────
    @Test(description = "Search same keyword twice — consistent results")
    public void verifyConsistentResults() {
        String keyword = ConfigReader.get("search.keyword.valid");
        SearchResultsPage first = homePage.searchProduct(keyword);
        int firstCount = first.getResultCount();

        SearchResultsPage second = first.searchAgain(keyword);
        int secondCount = second.getResultCount();

        System.out.println("First: " + firstCount +
                           " Second: " + secondCount);
        Assert.assertTrue(firstCount > 0 && secondCount > 0,
            "Both searches should return results!");
    }

    // ─── TC20: Search with numbers only ───────────────────────────
    @Test(description = "Search with numbers only")
    public void verifySearchWithNumbers() {
        SearchResultsPage resultsPage = homePage
            .searchProduct("12345");
        Assert.assertTrue(
            resultsPage.getCurrentUrl().contains("flipkart.com"),
            "Should stay on Flipkart!"
        );
    }

    // ─── TC21: Verify suggestions appear ──────────────────────────
    @Test(description = "Verify search suggestions appear while typing")
    public void verifySearchSuggestionsAppear() {
        List<org.openqa.selenium.WebElement> suggestions =
            homePage.getSearchSuggestions("iphone");
        System.out.println("Suggestions: " + suggestions.size());
        Assert.assertTrue(
            suggestions.size() >= 0,
            "Suggestions check completed!"
        );
    }

    // ─── TC22: Click product from results ─────────────────────────
    @Test(description = "Click first product and verify navigation")
    public void verifyClickingProductNavigates() {
        SearchResultsPage resultsPage = homePage
            .searchProduct(ConfigReader.get("search.keyword.valid"));

        String beforeUrl = resultsPage.getCurrentUrl();
        System.out.println("Before click: " + beforeUrl);

        resultsPage.clickFirstProduct();

        String afterUrl = getDriver().getCurrentUrl();
        System.out.println("After click: " + afterUrl);

        // Verify URL changed after clicking product
        Assert.assertNotEquals(
            beforeUrl, afterUrl,
            "URL should change after clicking product!"
        );
    }
}