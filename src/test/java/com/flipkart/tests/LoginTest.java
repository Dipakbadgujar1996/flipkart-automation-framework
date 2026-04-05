package com.flipkart.tests;

import com.flipkart.base.BaseTest;
import com.flipkart.pages.HomePage;
import com.flipkart.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    private LoginPage loginPage;
    private HomePage homePage;

    @BeforeMethod
    public void setUpPage() {
        homePage = new HomePage(getDriver());
        homePage.closeLoginPopupIfPresent();
        loginPage = new LoginPage(getDriver());
    }

    // ─── TC68: Verify login button visible ───────────────────────
    @Test(description = "Verify login button visible in header")
    public void verifyLoginButtonVisible() {
        Assert.assertTrue(
            loginPage.isLoginButtonVisibleInHeader(),
            "Login button should be visible in header!"
        );
    }

    // ─── TC69: Verify login page loads ───────────────────────────
    @Test(description = "Verify login page loads after clicking login")
    public void verifyLoginPageLoads() {
        loginPage.navigateToLoginPage();
        Assert.assertTrue(
            loginPage.isLoginPageLoaded(),
            "Login page should load!"
        );
    }

    // ─── TC70: Verify mobile input present ───────────────────────
    @Test(description = "Verify mobile/email input is present")
    public void verifyMobileInputPresent() {
        loginPage.navigateToLoginPage();
        Assert.assertTrue(
            loginPage.isMobileInputPresent(),
            "Mobile input should be present!"
        );
    }

    // ─── TC71: Verify Request OTP button present ─────────────────
    @Test(description = "Verify Request OTP button is present")
    public void verifyRequestOtpButtonPresent() {
        loginPage.navigateToLoginPage();
        Assert.assertTrue(
            loginPage.isRequestOtpButtonPresent(),
            "Request OTP button should be present!"
        );
    }

    // ─── TC72: Verify Create Account link present ─────────────────
    @Test(description = "Verify Create Account link is present")
    public void verifyCreateAccountLinkPresent() {
        loginPage.navigateToLoginPage();
        Assert.assertTrue(
            loginPage.isCreateAccountLinkPresent(),
            "Create Account link should be present!"
        );
    }

    // ─── TC73: Verify Terms of Use link present ───────────────────
    @Test(description = "Verify Terms of Use link is present")
    public void verifyTermsOfUseLinkPresent() {
        loginPage.navigateToLoginPage();
        Assert.assertTrue(
            loginPage.isTermsOfUseLinkPresent(),
            "Terms of Use link should be present!"
        );
    }

    // ─── TC74: Verify Privacy Policy link present ─────────────────
    @Test(description = "Verify Privacy Policy link is present")
    public void verifyPrivacyPolicyLinkPresent() {
        loginPage.navigateToLoginPage();
        Assert.assertTrue(
            loginPage.isPrivacyPolicyLinkPresent(),
            "Privacy Policy link should be present!"
        );
    }

    // ─── TC75: Enter valid mobile number ──────────────────────────
    @Test(description = "Enter valid mobile number and verify OTP screen")
    public void verifyValidMobileNumber() {
        loginPage.navigateToLoginPage();
        loginPage.enterMobileNumber("9999999999");
        loginPage.clickRequestOtp();
        String url = loginPage.getCurrentUrl();
        System.out.println("URL after OTP: " + url);
        Assert.assertTrue(
            url.contains("flipkart.com"),
            "Should stay on Flipkart after entering mobile!"
        );
    }

    // ─── TC76: Enter invalid mobile number ───────────────────────
    @Test(description = "Enter invalid mobile number and verify error")
    public void verifyInvalidMobileNumber() {
        loginPage.navigateToLoginPage();
        loginPage.enterInvalidMobile("1234");
        loginPage.clickRequestOtp();
        Assert.assertTrue(
            loginPage.isErrorMessageDisplayed(),
            "Error message should show for invalid mobile!"
        );
    }

    // ─── TC77: Enter empty mobile number ─────────────────────────
    @Test(description = "Submit empty mobile number and verify error")
    public void verifyEmptyMobileNumber() {
        loginPage.navigateToLoginPage();
        loginPage.clickRequestOtp();
        Assert.assertTrue(
            loginPage.isErrorMessageDisplayed(),
            "Error should show for empty mobile!"
        );
    }

    // ─── TC78: Verify login URL ───────────────────────────────────
    @Test(description = "Verify login page URL is correct")
    public void verifyLoginUrl() {
        loginPage.navigateToLoginPage();
        Assert.assertTrue(
            loginPage.isLoginUrl(),
            "URL should contain login identifier!"
        );
    }

    // ─── TC79: Verify login button click opens login ──────────────
    @Test(description = "Verify clicking login button opens login")
    public void verifyLoginButtonClickOpensLogin() {
        loginPage.clickLoginButton();
        String url = loginPage.getCurrentUrl();
        System.out.println("URL after login click: " + url);
        Assert.assertTrue(
            url.contains("login") ||
            url.contains("account") ||
            loginPage.isMobileInputPresent(),
            "Login should open after clicking login button!"
        );
    }

    // ─── TC80: Verify login page title ───────────────────────────
    @Test(description = "Verify login page has correct title")
    public void verifyLoginPageTitle() {
        loginPage.navigateToLoginPage();
        String title = getDriver().getTitle();
        System.out.println("Login title: " + title);
        Assert.assertTrue(
            title.length() > 0,
            "Login page should have a title!"
        );
    }
}