package utils;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

/**
 * Utility class providing common helper methods for WebDriver-based tests.
 * Includes wait utilities, screenshot capturing (for reports and debugging), and timestamping.
 */
public class TestUtil {

    /**
     * Waits until the specified WebElement becomes visible within the given timeout.
     * Useful to ensure elements are interactable before performing actions.
     *
     * @param driver  The active WebDriver instance.
     * @param element The WebElement to wait for.
     * @param timeout Timeout in seconds to wait.
     */
    public static void waitForElementVisible(WebDriver driver, WebElement element, int timeout) {
        new WebDriverWait(driver, Duration.ofSeconds(timeout))
                .until(ExpectedConditions.visibilityOf(element));
    }

    /**
     * Captures a screenshot of the current browser window and saves it to the /screenshots directory.
     * The screenshot is named with a provided base name and current timestamp.
     *
     * @param driver The active WebDriver instance.
     * @param name   The base name for the screenshot file (e.g., "login_page").
     */
    public static void takeScreenshot(WebDriver driver, String name) {
        TakesScreenshot ts = (TakesScreenshot) driver;
        File src = ts.getScreenshotAs(OutputType.FILE);
        String path = "screenshots/" + name + "_" + timestamp() + ".png";
        try {
            FileUtils.copyFile(src, new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Captures a screenshot as a byte array.
     * Typically used for embedding screenshots in Allure or other test reports.
     *
     * @param driver The active WebDriver instance.
     * @return Byte array representing the screenshot image, or empty array if capture fails.
     */
    public static byte[] getScreenshotBytes(WebDriver driver) {
        try {
            return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        } catch (WebDriverException e) {
            e.printStackTrace();
            return new byte[0]; // return empty array on failure
        }
    }

    /**
     * Generates a timestamp string in format yyyyMMdd_HHmmss.
     * Used to uniquely name screenshots or logs.
     *
     * @return Formatted timestamp string.
     */
    private static String timestamp() {
        return new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
    }
}
