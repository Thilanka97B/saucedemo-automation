package tests;

import org.testng.annotations.*;
import pages.InventoryPage;
import pages.LoginPage;
import utils.DriverFactory;
import utils.TestUtil;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import java.util.List;
import java.util.logging.Logger;

import io.qameta.allure.*;
import io.qameta.allure.testng.AllureTestNg;
import org.testng.annotations.Listeners;

@Listeners({AllureTestNg.class})
public class SortingTest {
    WebDriver driver;
    private static final Logger logger = Logger.getLogger(SortingTest.class.getName());

    /**
     * Initializes WebDriver and logs into the SauceDemo site before each test.
     */
    @BeforeMethod
    @Step("Setup WebDriver and login to SauceDemo")
    public void setup() {
        logger.info("Starting WebDriver and opening SauceDemo site.");
        driver = DriverFactory.initDriver();
        driver.get("https://www.saucedemo.com/");

        logger.info("Logging in with standard_user.");
        new LoginPage(driver).login("standard_user", "secret_sauce");

        // Capture post-login screenshot
        attachScreenshot("login_successful");
    }

    /**
     * Test to verify sorting by price (high to low) and count products containing 'sauce' in their title.
     */
    @Test(description = "Verify product sorting by price from High to Low and count 'sauce' products")
    @Epic("Product Sorting")
    @Feature("Sorting Functionality")
    @Story("Sort products by price and validate sorting order and product count")
    @Severity(SeverityLevel.NORMAL)
    public void testPriceSortingHighToLowAndSauceCount() {
        logger.info("Navigating to inventory page and sorting by Price: High to Low.");
        InventoryPage inventory = new InventoryPage(driver);

        // Sort by "Price (high to low)"
        sortProductsBy("Price (high to low)", inventory);

        // Get the top 3 prices after sorting
        List<Double> prices = getTopNPrices(3, inventory);
        logger.info("Top 3 prices extracted: " + prices);

        // Validate sorting order
        Assert.assertTrue(prices.get(0) >= prices.get(1), "First price is not greater than or equal to second");
        Assert.assertTrue(prices.get(1) >= prices.get(2), "Second price is not greater than or equal to third");

        logger.info("Prices are sorted correctly in descending order.");
        attachScreenshot("sorted_high_to_low");

        // Count number of product titles containing the word "sauce"
        long sauceCount = countProductsWithKeyword("sauce", inventory);
        logger.info("Number of products with 'sauce' in title: " + sauceCount);

        // Validate that at least one product contains the keyword
        Assert.assertTrue(sauceCount > 0, "No product titles contain 'sauce'");
    }

    /**
     * Helper method to sort products using the inventory page.
     */
    @Step("Sort products by '{criteria}'")
    private void sortProductsBy(String criteria, InventoryPage inventory) {
        inventory.sortBy(criteria);
    }

    /**
     * Helper method to retrieve top N product prices.
     */
    @Step("Get top {count} prices from inventory")
    private List<Double> getTopNPrices(int count, InventoryPage inventory) {
        return inventory.getTopNPrices(count);
    }

    /**
     * Helper method to count product titles with a given keyword.
     */
    @Step("Count products with keyword '{keyword}' in title")
    private long countProductsWithKeyword(String keyword, InventoryPage inventory) {
        return inventory.countTitlesWith(keyword);
    }

    /**
     * Attaches a screenshot to the Allure report.
     */
    @Attachment(value = "{0}", type = "image/png")
    private byte[] attachScreenshot(String name) {
        return TestUtil.getScreenshotBytes(driver);
    }

    /**
     * Cleans up WebDriver instance after test execution.
     */
    @AfterMethod
    @Step("Quit WebDriver")
    public void teardown() {
        logger.info("Closing WebDriver.");
        DriverFactory.quitDriver();
    }
}
