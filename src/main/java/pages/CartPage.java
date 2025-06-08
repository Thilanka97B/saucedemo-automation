package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.*;
import utils.TestUtil;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents the Cart Page in the SauceDemo application.
 * Provides methods to verify cart contents and proceed to checkout.
 */
public class CartPage {
    WebDriver driver;

    // Checkout button on the cart page
    @FindBy(id = "checkout")
    private WebElement checkoutButton;

    // List of item names displayed in the cart
    @FindBy(css = ".cart_item .inventory_item_name")
    private List<WebElement> cartItemNames;

    /**
     * Constructor that initializes page elements using PageFactory.
     * @param driver WebDriver instance passed from test class.
     */
    public CartPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    /**
     * Clicks the checkout button after ensuring it is visible.
     * Navigates the user to the checkout information page.
     */
    public void proceedToCheckout() {
        TestUtil.waitForElementVisible(driver, checkoutButton, 10);
        checkoutButton.click();
    }

    /**
     * Verifies that the expected item names are present in the cart.
     * @param expectedItems Varargs list of expected item names.
     * @return true if all expected items are found in the cart; false otherwise.
     */
    public boolean verifyItemsInCart(String... expectedItems) {
        // Extract the text of all item names currently displayed in the cart
        List<String> actualItemNames = cartItemNames.stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());

        // Check that each expected item is present in the cart
        for (String expected : expectedItems) {
            if (!actualItemNames.contains(expected)) {
                return false;
            }
        }
        return true;
    }
}
