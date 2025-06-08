package tests;

import org.testng.annotations.*;
import org.testng.Assert;
import pages.LoginPage;
import pages.InventoryPage;
import utils.DriverFactory;
import utils.TestUtil;
import org.openqa.selenium.WebDriver;

import java.util.logging.Logger;

import io.qameta.allure.*;
import io.qameta.allure.testng.AllureTestNg;
import org.testng.annotations.Listeners;

@Listeners({AllureTestNg.class})
public class LoginTest {

    WebDriver driver;
    private static final Logger logger = Logger.getLogger(LoginTest.class.getName());

    /**
     * Setup method executed before each test.
     * Initializes the WebDriver and navigates to the login page.
     */
    @BeforeMethod
    @Step("Setup WebDriver and open login page")
    public void setup() {
        logger.info("Initializing WebDriver...");
        driver = DriverFactory.initDriver();
        driver.get("https://www.saucedemo.com/");
        logger.info("Navigated to SauceDemo login page.");
    }

    /**
     * Test to verify successful login using valid credentials.
     */
    @Test(description = "Verify valid login functionality")
    @Epic("Authentication")
    @Feature("Login")
    @Story("User logs in with valid credentials")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Ensure that user can login using standard_user credentials and see inventory items.")
    public void testValidLogin() {
        logger.info("Starting login test with valid credentials.");

        LoginPage loginPage = new LoginPage(driver);
        login("standard_user", "secret_sauce");

        // Verify redirection to inventory page after login
        String currentUrl = driver.getCurrentUrl();
        logger.info("Current URL after login: " + currentUrl);
        Assert.assertTrue(currentUrl.contains("/inventory.html"), "User is not redirected to inventory page");

        // Verify inventory items are displayed
        InventoryPage inventoryPage = new InventoryPage(driver);
        int itemCount = inventoryPage.getProductTitles().size();
        logger.info("Number of products displayed: " + itemCount);
        Assert.assertTrue(itemCount > 0, "No inventory items displayed");

        // Capture screenshot for Allure report
        attachScreenshot("valid_login");
    }

    /**
     * Helper method to perform login and log the action.
     */
    @Step("Login with username: {0} and password: {1}")
    private void login(String username, String password) {
        new LoginPage(driver).login(username, password);
        logger.info("Login form submitted.");
    }

    /**
     * Captures a screenshot and attaches it to the Allure report.
     */
    @Attachment(value = "{0}", type = "image/png")
    private byte[] attachScreenshot(String name) {
        return TestUtil.getScreenshotBytes(driver);
    }

    /**
     * Teardown method executed after each test.
     * Closes the browser and cleans up resources.
     */
    @AfterMethod
    @Step("Close the browser")
    public void teardown() {
        logger.info("Quitting WebDriver session.");
        DriverFactory.quitDriver();
    }
}
