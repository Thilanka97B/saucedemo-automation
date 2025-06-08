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

    @BeforeMethod
    @Step("Setup WebDriver and login to SauceDemo")
    public void setup() {
        logger.info("Starting WebDriver and opening SauceDemo site.");
        driver = DriverFactory.initDriver();
        driver.get("https://www.saucedemo.com/");

        logger.info("Logging in with standard_user.");
        new LoginPage(driver).login("standard_user", "secret_sauce");

        attachScreenshot("login_successful");
    }

    @Test(description = "Verify product sorting by price from High to Low and count 'sauce' products")
    @Epic("Product Sorting")
    @Feature("Sorting Functionality")
    @Story("Sort products by price and validate sorting order and product count")
    @Severity(SeverityLevel.NORMAL)
    public void testPriceSortingHighToLowAndSauceCount() {
        logger.info("Navigating to inventory page and sorting by Price: High to Low.");
        InventoryPage inventory = new InventoryPage(driver);

        sortProductsBy("Price (high to low)", inventory);

        List<Double> prices = getTopNPrices(3, inventory);
        logger.info("Top 3 prices extracted: " + prices);

        Assert.assertTrue(prices.get(0) >= prices.get(1), "First price is not greater than or equal to second");
        Assert.assertTrue(prices.get(1) >= prices.get(2), "Second price is not greater than or equal to third");

        logger.info("Prices are sorted correctly in descending order.");
        attachScreenshot("sorted_high_to_low");

        long sauceCount = countProductsWithKeyword("sauce", inventory);
        logger.info("Number of products with 'sauce' in title: " + sauceCount);

        Assert.assertTrue(sauceCount > 0, "No product titles contain 'sauce'");
    }

    @Step("Sort products by '{criteria}'")
    private void sortProductsBy(String criteria, InventoryPage inventory) {
        inventory.sortBy(criteria);
    }

    @Step("Get top {count} prices from inventory")
    private List<Double> getTopNPrices(int count, InventoryPage inventory) {
        return inventory.getTopNPrices(count);
    }

    @Step("Count products with keyword '{keyword}' in title")
    private long countProductsWithKeyword(String keyword, InventoryPage inventory) {
        return inventory.countTitlesWith(keyword);
    }

    @Attachment(value = "{0}", type = "image/png")
    private byte[] attachScreenshot(String name) {
        return TestUtil.getScreenshotBytes(driver);
    }

    @AfterMethod
    @Step("Quit WebDriver")
    public void teardown() {
        logger.info("Closing WebDriver.");
        DriverFactory.quitDriver();
    }
}
