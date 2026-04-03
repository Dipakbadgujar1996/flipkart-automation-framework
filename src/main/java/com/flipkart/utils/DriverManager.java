package com.flipkart.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;

import java.time.Duration;

public class DriverManager {

	// ThreadLocal ensures each test thread gets its OWN browser
	private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

	// ─── Initialize Browser ───────────────────────────────────────
	public static void initDriver() {
		String browser = ConfigReader.getBrowser().toLowerCase();

		switch (browser) {
		case "chrome":
			WebDriverManager.chromedriver().setup();
			ChromeOptions chromeOptions = new ChromeOptions();

			if (ConfigReader.isHeadless()) {
				chromeOptions.addArguments("--headless");
			}

			// Standard options
			chromeOptions.addArguments("--no-sandbox");
			chromeOptions.addArguments("--disable-dev-shm-usage");
			chromeOptions.addArguments("--disable-notifications");
			chromeOptions.addArguments("--disable-popup-blocking");
			chromeOptions.addArguments("--disable-extensions");
			chromeOptions.addArguments("--no-first-run");
			chromeOptions.addArguments("--no-default-browser-check");
			chromeOptions.addArguments("--disable-infobars");

			// Hide automation — very important for Flipkart!
			chromeOptions.addArguments("--disable-blink-features=AutomationControlled");
			chromeOptions.setExperimentalOption("excludeSwitches", java.util.Arrays.asList("enable-automation"));
			chromeOptions.setExperimentalOption("useAutomationExtension", false);

			// Set real user agent
			chromeOptions.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) "
					+ "AppleWebKit/537.36 (KHTML, like Gecko) " + "Chrome/146.0.0.0 Safari/537.36");

			driver.set(new ChromeDriver(chromeOptions));
			break;

		case "firefox":
			WebDriverManager.firefoxdriver().setup();
			FirefoxOptions firefoxOptions = new FirefoxOptions();
			if (ConfigReader.isHeadless()) {
				firefoxOptions.addArguments("--headless");
			}
			driver.set(new FirefoxDriver(firefoxOptions));
			break;

		default:
			throw new RuntimeException("Browser '" + browser + "' not supported!");
		}

		// Apply settings
		getDriver().manage().window()
				.setSize(new Dimension(ConfigReader.getBrowserWidth(), ConfigReader.getBrowserHeight()));
		getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(ConfigReader.getImplicitWait()));
		getDriver().manage().timeouts().pageLoadTimeout(Duration.ofSeconds(ConfigReader.getPageLoadTimeout()));
		getDriver().manage().deleteAllCookies();

		// Remove webdriver property using JavaScript
		((JavascriptExecutor) getDriver())
				.executeScript("Object.defineProperty(navigator, 'webdriver', {get: () => undefined})");
	

	// Apply settings from config.properties
	getDriver().manage().timeouts()
        .implicitlyWait(Duration.ofSeconds(ConfigReader.getImplicitWait()));
        
        getDriver().manage().window().setSize(
            new Dimension(
                ConfigReader.getBrowserWidth(),
                ConfigReader.getBrowserHeight()
            )
        );

        getDriver().manage().timeouts()
            .implicitlyWait(Duration.ofSeconds(ConfigReader.getImplicitWait()));

        getDriver().manage().timeouts()
            .pageLoadTimeout(Duration.ofSeconds(ConfigReader.getPageLoadTimeout()));

        getDriver().manage().deleteAllCookies();
    }

	// ─── Get Current Driver ───────────────────────────────────────
	public static WebDriver getDriver() {
		return driver.get();
	}

	// ─── Open URL ─────────────────────────────────────────────────
	public static void openUrl(String url) {
		getDriver().get(url);
	}

	// ─── Quit Browser ─────────────────────────────────────────────
	public static void quitDriver() {
		if (getDriver() != null) {
			getDriver().quit();
			driver.remove(); // Important! Cleans up ThreadLocal memory
		}
	}
}