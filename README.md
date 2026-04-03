# Flipkart Automation Framework

A professional Selenium WebDriver automation framework built with Java + TestNG for testing Flipkart e-commerce website.

## Tech Stack
- Java 11
- Selenium WebDriver 4.21.0
- TestNG 7.10.2
- Maven
- ExtentReports 5.1.1
- WebDriverManager 5.8.0
- Page Object Model (POM)

## Framework Features
- Page Object Model design pattern
- ThreadLocal DriverManager for parallel execution
- Config driven framework (config.properties)
- ExtentReports with dark theme
- Screenshot on test failure
- Cross browser support (Chrome + Firefox)
- TestNG listeners

## Test Coverage — 52 Tests Passing
| Module | Tests |
|--------|-------|
| Homepage | 10 |
| Search | 12 |
| Product Listing | 15 |
| Product Detail | 15 |
| **Total** | **52** |

## Project Structure
src/
├── main/java/com/flipkart/
│   ├── pages/          # Page Object classes
│   └── utils/          # Utilities & helpers
└── test/java/com/flipkart/
├── base/           # BaseTest class
└── tests/          # Test classes

## How to Run
```bash
mvn test
```

## Reports
After running tests, HTML report is generated at:
