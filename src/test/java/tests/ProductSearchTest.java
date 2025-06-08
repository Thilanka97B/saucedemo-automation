package tests;

import org.testng.annotations.*;
import pages.InventoryPage;
import pages.LoginPage;
import utils.DriverFactory;
import utils.TestUtil;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import java.util.logging.Logger;

import io.qameta.allure.*;
import io.qameta.allure.testng.AllureTestNg;
import org.testng.annotations.Listeners;

@Listeners({AllureTestNg.class})
public class ProductSearchTest {
    WebDriver driver;
    private static final Logger logger = Logger.getLogger(ProductSearchTest.class.getName());

    @BeforeMethod
    @Step("Initialize WebDriver and login to SauceDemo")
    public void setup() {
        logger.info("Initializing WebDriver and navigating to SauceDemo login page.");
        driver = DriverFactory.initDriver();
        driver.get("https://www.saucedemo.com/");

        logger.info("Logging in with valid credentials.");
        new LoginPage(driver).login("standard_user", "secret_sauce");

        logger.info("Login successful. Taking screenshot.");
        attachScreenshot("after_login");
    }

    @Test(description = "Verify product search returns expected results")
    @Epic("Product Search")
    @Feature("Search Functionality")
    @Story("Search products by keyword")
    @Severity(SeverityLevel.NORMAL)
    public void testSearchKeyword() {
        String keyword = "backpack";
        logger.info("Searching for keyword: " + keyword);

        InventoryPage inventory = new InventoryPage(driver);

        long count = countProductsWithKeyword(keyword, inventory);

        logger.info("Number of products found with keyword '" + keyword + "': " + count);

        attachScreenshot("after_search");

        Assert.assertTrue(count > 0, "No products found with keyword '" + keyword + "'");
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
        logger.info("Quitting WebDriver.");
        DriverFactory.quitDriver();
    }
}
