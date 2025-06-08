package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * Factory class for managing the WebDriver instance lifecycle.
 * Uses WebDriverManager to handle driver binaries automatically.
 */
public class DriverFactory {
    private static WebDriver driver;

    /**
     * Initializes the WebDriver instance using ChromeDriver.
     * WebDriverManager is used to ensure the correct ChromeDriver version is available.
     * The browser window is maximized by default.
     *
     * @return WebDriver instance
     */
    public static WebDriver initDriver() {
        WebDriverManager.chromedriver().setup(); // Setup compatible ChromeDriver automatically
        driver = new ChromeDriver();             // Launch Chrome browser
        driver.manage().window().maximize();     // Maximize browser window
        return driver;
    }

    /**
     * Gracefully quits the WebDriver instance if it is active.
     * This should be called in test teardown to close the browser.
     */
    public static void quitDriver() {
        if (driver != null) driver.quit();
    }
}
