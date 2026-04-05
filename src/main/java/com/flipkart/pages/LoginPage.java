package com.flipkart.pages;

import com.flipkart.utils.ConfigReader;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class LoginPage {

    private WebDriver driver;
    private WebDriverWait wait;

    // ─── Locators ─────────────────────────────────────────────────

    // ✅ Login button in header
    @FindBy(xpath = "//span[text()='Login']")
    private WebElement loginButton;

    // ✅ Mobile/Email input
    @FindBy(css = "input.c3Bd2c")
    private WebElement mobileEmailInput;

    // ✅ Request OTP button
    @FindBy(css = "button.dSM5Ub")
    private WebElement requestOtpButton;

    // ✅ Create Account link
    @FindBy(css = "a.nYcTDx")
    private WebElement createAccountLink;

    // ✅ Terms of Use
    @FindBy(xpath = "//a[text()='Terms of Use']")
    private WebElement termsOfUseLink;

    // ✅ Privacy Policy
    @FindBy(xpath = "//a[text()='Privacy Policy']")
    private WebElement privacyPolicyLink;

    // ─── Constructor ──────────────────────────────────────────────

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver,
            Duration.ofSeconds(ConfigReader.getExplicitWait()));
        PageFactory.initElements(driver, this);
    }

    // ─── Actions ──────────────────────────────────────────────────

    // Click login button
    public void clickLoginButton() {
        try {
            wait.until(ExpectedConditions
                .elementToBeClickable(loginButton));
            loginButton.click();
            Thread.sleep(2000);
            System.out.println("Login button clicked!");
        } catch (Exception e) {
            System.out.println("Login click failed: "
                + e.getMessage());
        }
    }

    // Verify login page/popup loaded
    public boolean isLoginPageLoaded() {
        try {
            Thread.sleep(2000);
            // Look for input with class c3Bd2c
            List<WebElement> inputs = driver.findElements(
                By.cssSelector("input.c3Bd2c"));
            if (inputs.size() > 0) {
                System.out.println("Login page loaded via class!");
                return true;
            }
            // Fallback — look for text on page
            List<WebElement> loginText = driver.findElements(
                By.xpath("//*[contains(text()," +
                        "'Enter Email/Mobile number')]"));
            System.out.println("Login text found: " + loginText.size());
            return loginText.size() > 0;
        } catch (Exception e) {
            System.out.println("Login page check failed: "
                + e.getMessage());
            return false;
        }
    }
    
    

    // Verify mobile input is present
    public boolean isMobileInputPresent() {
        try {
            // Use confirmed class from Console
            List<WebElement> inputs = driver.findElements(
                By.cssSelector("input.c3Bd2c"));
            System.out.println("Mobile input found: " + inputs.size());
            return inputs.size() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    // Enter mobile number
    public void enterMobileNumber(String mobile) {
        try {
            WebElement input = wait.until(ExpectedConditions
                .elementToBeClickable(
                    By.cssSelector("input.c3Bd2c")));
            input.clear();
            input.sendKeys(mobile);
            System.out.println("Mobile entered: " + mobile);
        } catch (Exception e) {
            System.out.println("Mobile input failed: "
                + e.getMessage());
        }
    }

    // Click Request OTP
    public void clickRequestOtp() {
        try {
            wait.until(ExpectedConditions
                .elementToBeClickable(requestOtpButton));
            ((JavascriptExecutor) driver)
                .executeScript(
                    "arguments[0].click();", requestOtpButton);
            Thread.sleep(2000);
            System.out.println("Request OTP clicked!");
        } catch (Exception e) {
            System.out.println("OTP click failed: "
                + e.getMessage());
        }
    }

    // Verify Request OTP button present
    public boolean isRequestOtpButtonPresent() {
        try {
            List<WebElement> btns = driver.findElements(
                By.cssSelector("button.dSM5Ub"));
            System.out.println("OTP button: " + btns.size());
            return btns.size() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    // Verify Create Account link present
    public boolean isCreateAccountLinkPresent() {
        try {
            List<WebElement> links = driver.findElements(
                By.cssSelector("a.nYcTDx"));
            System.out.println("Create account: " + links.size());
            return links.size() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    // Verify Terms of Use present
    public boolean isTermsOfUseLinkPresent() {
        try {
            List<WebElement> links = driver.findElements(
                By.xpath("//a[text()='Terms of Use']"));
            System.out.println("Terms of use: " + links.size());
            return links.size() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    // Verify Privacy Policy present
    public boolean isPrivacyPolicyLinkPresent() {
        try {
            List<WebElement> links = driver.findElements(
                By.xpath("//a[text()='Privacy Policy']"));
            System.out.println("Privacy policy: " + links.size());
            return links.size() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    // Enter invalid mobile number
    public void enterInvalidMobile(String mobile) {
        try {
            WebElement input = wait.until(ExpectedConditions
                .elementToBeClickable(
                    By.cssSelector("input.c3Bd2c")));
            input.clear();
            input.sendKeys(mobile);
            System.out.println("Invalid mobile entered: " + mobile);
        } catch (Exception e) {
            System.out.println("Invalid mobile failed: "
                + e.getMessage());
        }
    }
    // Verify error message after invalid input
    public boolean isErrorMessageDisplayed() {
        try {
            Thread.sleep(2000);
            List<WebElement> errors = driver.findElements(
                By.xpath("//*[contains(text(),'valid') or " +
                        "contains(text(),'incorrect') or " +
                        "contains(text(),'Invalid') or " +
                        "contains(text(),'Enter')]"));
            System.out.println("Error messages: " + errors.size());
            return errors.size() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    // Verify login URL
    public boolean isLoginUrl() {
        String url = driver.getCurrentUrl();
        System.out.println("Login URL: " + url);
        return url.contains("login") ||
               url.contains("account");
    }

    // Get current URL
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    // isLoginPageLoaded()e to login page directly
    public void isloginToLoginPage() {
        try {
            // Try direct URL first
            driver.get("https://www.flipkart.com/account/login");
            Thread.sleep(3000);

            // Check if login input appeared
            List<WebElement> inputs = driver.findElements(
                By.xpath("//input[@placeholder=" +
                        "'Enter Email/Mobile number']"));

            // If not found — click login button instead
            if (inputs.size() == 0) {
                driver.get("https://www.flipkart.com");
                Thread.sleep(3000);
                List<WebElement> loginBtns = driver.findElements(
                    By.xpath("//span[text()='Login']"));
                if (loginBtns.size() > 0) {
                    loginBtns.get(0).click();
                    Thread.sleep(3000);
                }
            }
            System.out.println("Login page setup done!");
        } catch (Exception e) {
            System.out.println("isLoginPageLoaded()e login failed: "
                + e.getMessage());
        }
    }

    // Verify login button visible in header
    public boolean isLoginButtonVisibleInHeader() {
        try {
            List<WebElement> btns = driver.findElements(
                By.xpath("//span[text()='Login']"));
            System.out.println("Login in header: " + btns.size());
            return btns.size() > 0;
        } catch (Exception e) {
            return false;
        }
    }
    public void navigateToLoginPage() {
        try {
            driver.get("https://www.flipkart.com/account/login");
            Thread.sleep(3000);

            // Check if login input appeared
            List<WebElement> inputs = driver.findElements(
                By.cssSelector("input.c3Bd2c"));

            // If not found — click login button instead
            if (inputs.size() == 0) {
                System.out.println("Direct URL failed — trying click!");
                driver.get("https://www.flipkart.com");
                Thread.sleep(3000);

                List<WebElement> loginBtns = driver.findElements(
                    By.xpath("//span[text()='Login']"));
                if (loginBtns.size() > 0) {
                    loginBtns.get(0).click();
                    Thread.sleep(3000);
                }
            }
            System.out.println("Login page setup done!");
        } catch (Exception e) {
            System.out.println("Navigate login failed: "
                + e.getMessage());
        }
    }
}