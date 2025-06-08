package tests;

import io.qameta.allure.testng.AllureTestNg;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import org.testng.Assert;
import pages.*;
import utils.DriverFactory;
import utils.TestUtil;

import java.util.logging.Logger;

import io.qameta.allure.*;

@Listeners({AllureTestNg.class})
public class CheckoutTest {

    WebDriver driver;
    private static final Logger logger = Logger.getLogger(CheckoutTest.class.getName());

    /**
     * Initializes the browser and logs into the application before each test.
     */
    @BeforeMethod
    @Step("Initialize browser and log in to the application")
    public void setup() {
        logger.info("Initializing WebDriver and opening the website");
        driver = DriverFactory.initDriver();
        driver.get("https://www.saucedemo.com/");

        // Perform login with valid credentials
        logger.info("Logging in as standard_user");
        new LoginPage(driver).login("standard_user", "secret_sauce");
        attachScreenshot("01_logged_in");
    }

    /**
     * Executes the full end-to-end checkout flow and verifies each stage.
     */
    @Test(description = "Verify the complete checkout process")
    @Epic("Checkout Flow")
    @Feature("End-to-End Purchase")
    @Story("Complete order from login to confirmation")
    @Severity(SeverityLevel.CRITICAL)
    public void testFullCheckoutFlow() {
        logger.info("Starting full checkout flow test");

        // Add products to cart
        InventoryPage inventory = new InventoryPage(driver);
        inventory.addToCartBackpackAndBikeLight();
        attachScreenshot("02_products_added");

        // Go to cart and verify added items
        inventory.goToCart();
        CartPage cart = new CartPage(driver);
        Assert.assertTrue(cart.verifyItemsInCart("Sauce Labs Backpack", "Sauce Labs Bike Light"),
                "Cart does not contain expected items.");
        attachScreenshot("03_cart_verified");

        // Proceed to checkout step 1
        cart.proceedToCheckout();
        attachScreenshot("04_checkout_started");

        // Fill in user info and continue
        CheckoutPage checkout = new CheckoutPage(driver);
        checkout.fillCheckoutForm("John", "Doe", "12345");
        attachScreenshot("05_checkout_info_entered");

        // On overview page: verify items and subtotal
        CheckoutOverviewPage overview = new CheckoutOverviewPage(driver);
        Assert.assertTrue(overview.verifyItemPresent("Sauce Labs Backpack"), "Backpack not shown in overview.");
        Assert.assertTrue(overview.verifyItemPresent("Sauce Labs Bike Light"), "Bike Light not shown in overview.");

        // Validate the calculated total matches displayed total
        double expectedTotal = overview.getItemPrices().stream().mapToDouble(Double::doubleValue).sum();
        double displayedTotal = overview.getItemTotal();
        Assert.assertEquals(displayedTotal, expectedTotal, 0.01, "Displayed total doesn't match calculated total.");
        attachScreenshot("06_price_verified");

        // Finish checkout process
        overview.completeCheckout();
        attachScreenshot("07_order_completed");

        // Verify confirmation message
        String confirmation = overview.getConfirmationMessage();
        Assert.assertTrue(confirmation.toLowerCase().contains("thank you"), "Confirmation message not shown.");
        attachScreenshot("08_confirmation_displayed");
    }

    /**
     * Quits the browser after each test execution.
     */
    @AfterMethod
    @Step("Close the browser")
    public void teardown() {
        logger.info("Quitting the WebDriver");
        DriverFactory.quitDriver();
    }

    /**
     * Captures screenshot and attaches it to Allure report.
     * @param name Descriptive name for the screenshot
     * @return Screenshot bytes
     */
    @Attachment(value = "{0}", type = "image/png")
    public byte[] attachScreenshot(String name) {
        return TestUtil.getScreenshotBytes(driver);
    }
}
