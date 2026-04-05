package com.flipkart.pages;

import com.flipkart.utils.ConfigReader;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class CartPage {

    private WebDriver driver;
    private WebDriverWait wait;

    // ─── Locators ─────────────────────────────────────────────────

    // ✅ Cart link in header
    @FindBy(css = "a.E7_UTN")
    private WebElement cartLink;

    // ✅ Empty cart message
    @FindBy(xpath = "//*[contains(text(),'Missing Cart items?')]")
    private WebElement emptyCartMessage;

    // ✅ Continue Shopping button
    @FindBy(xpath = "//*[contains(text(),'Continue Shopping')]")
    private WebElement continueShoppingButton;

    // ✅ Login button on cart page
    @FindBy(xpath = "//*[text()='Login']")
    private WebElement loginButton;

    // ✅ Cart items — product links
    @FindBy(css = "a[href*='/p/']")
    private List<WebElement> cartItems;

    // ✅ Place Order button
    @FindBy(xpath = "//*[contains(text(),'Place Order') or " +
                   "contains(text(),'PLACE ORDER')]")
    private WebElement placeOrderButton;

    // ✅ Price elements
    @FindBy(xpath = "//*[starts-with(normalize-space(text()),'₹')]")
    private List<WebElement> priceElements;

    // ✅ Remove button
    @FindBy(xpath = "//*[text()='Remove']")
    private List<WebElement> removeButtons;

    // ✅ Save for later
    @FindBy(xpath = "//*[contains(text(),'Save for later')]")
    private List<WebElement> saveForLaterButtons;

    // ─── Constructor ──────────────────────────────────────────────

    public CartPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver,
            Duration.ofSeconds(ConfigReader.getExplicitWait()));
        PageFactory.initElements(driver, this);
    }

    // ─── Actions ──────────────────────────────────────────────────

    // Navigate to cart page
    public void navigateToCart() {
        try {
            driver.get("https://www.flipkart.com/" +
                      "viewcart?marketplace=FLIPKART");
            Thread.sleep(3000);
            System.out.println("Navigated to cart page!");
        } catch (Exception e) {
            System.out.println("Navigate cart failed: "
                + e.getMessage());
        }
    }

    // Click cart icon in header
    public void clickCartIcon() {
        try {
            wait.until(ExpectedConditions
                .elementToBeClickable(cartLink));
            cartLink.click();
            Thread.sleep(2000);
            System.out.println("Cart icon clicked!");
        } catch (Exception e) {
            System.out.println("Cart icon click failed: "
                + e.getMessage());
        }
    }

    // Verify cart page loaded
    public boolean isCartPageLoaded() {
        try {
            return driver.getCurrentUrl()
                .contains("viewcart") ||
                   driver.getCurrentUrl()
                .contains("cart");
        } catch (Exception e) {
            return false;
        }
    }

    // Verify cart is empty
    public boolean isCartEmpty() {
        try {
            Thread.sleep(2000);
            List<WebElement> emptyMsg = driver.findElements(
                By.xpath("//*[contains(text()," +
                        "'Missing Cart items?')]"));
            System.out.println("Empty cart messages: "
                + emptyMsg.size());
            return emptyMsg.size() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    // Verify continue shopping button visible
    public boolean isContinueShoppingVisible() {
        try {
            List<WebElement> btn = driver.findElements(
                By.xpath("//*[contains(text()," +
                        "'Continue Shopping')]"));
            System.out.println("Continue shopping: "
                + btn.size());
            return btn.size() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    // Click continue shopping
    public void clickContinueShopping() {
        try {
            List<WebElement> btn = driver.findElements(
                By.xpath("//*[contains(text()," +
                        "'Continue Shopping')]"));
            if (btn.size() > 0) {
                btn.get(0).click();
                Thread.sleep(2000);
                System.out.println("Continue shopping clicked!");
            }
        } catch (Exception e) {
            System.out.println("Continue shopping failed: "
                + e.getMessage());
        }
    }

    // Verify cart has items
    public boolean hasCartItems() {
        try {
            List<WebElement> items = driver.findElements(
                By.cssSelector("a[href*='/p/']"));
            System.out.println("Cart items: " + items.size());
            return items.size() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    // Get cart item count
    public int getCartItemCount() {
        try {
            List<WebElement> items = driver.findElements(
                By.cssSelector("a[href*='/p/']"));
            return items.size();
        } catch (Exception e) {
            return 0;
        }
    }

    // Verify prices displayed
    public boolean arePricesDisplayed() {
        try {
            List<WebElement> prices = driver.findElements(
                By.xpath("//*[starts-with(" +
                        "normalize-space(text()),'₹')]"));
            System.out.println("Prices: " + prices.size());
            return prices.size() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    // Verify place order button
    public boolean isPlaceOrderButtonVisible() {
        try {
            List<WebElement> btn = driver.findElements(
                By.xpath("//*[contains(text(),'Place Order') or " +
                        "contains(text(),'PLACE ORDER')]"));
            System.out.println("Place order: " + btn.size());
            return btn.size() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    // Verify login prompt on cart
    public boolean isLoginPromptDisplayed() {
        try {
            List<WebElement> login = driver.findElements(
                By.xpath("//*[text()='Login']"));
            System.out.println("Login prompt: " + login.size());
            return login.size() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    // Verify cart icon in header
    public boolean isCartIconVisible() {
        try {
            List<WebElement> cartIcons = driver.findElements(
                By.cssSelector("a.E7_UTN"));
            System.out.println("Cart icon: " + cartIcons.size());
            return cartIcons.size() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    // Get current URL
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    // Verify remove button visible
    public boolean isRemoveButtonVisible() {
        try {
            List<WebElement> remove = driver.findElements(
                By.xpath("//*[text()='Remove']"));
            System.out.println("Remove buttons: "
                + remove.size());
            return remove.size() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    // Verify save for later visible
    public boolean isSaveForLaterVisible() {
        try {
            List<WebElement> save = driver.findElements(
                By.xpath("//*[contains(text(),'Save for later')]"));
            System.out.println("Save for later: " + save.size());
            return save.size() > 0;
        } catch (Exception e) {
            return false;
        }
    }
}
