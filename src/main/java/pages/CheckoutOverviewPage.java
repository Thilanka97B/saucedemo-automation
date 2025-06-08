package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.TestUtil;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents the Checkout Overview Page of the SauceDemo application.
 * Includes methods for verifying products, price calculation, and completing the checkout process.
 */
public class CheckoutOverviewPage {
    WebDriver driver;

    // Finish button to complete the checkout
    @FindBy(id = "finish")
    private WebElement finishButton;

    // Confirmation text displayed after order completion
    @FindBy(className = "complete-header")
    private WebElement confirmationText;

    // Prices of individual items listed in the order
    @FindBy(css = ".inventory_item_price")
    private List<WebElement> itemPrices;

    // The total amount before tax and shipping
    @FindBy(className = "summary_subtotal_label")
    private WebElement itemTotalLabel;

    // List of product titles shown in the overview
    @FindBy(css = ".cart_item .inventory_item_name")
    private List<WebElement> overviewItemTitles;

    /**
     * Constructor initializes all page elements using PageFactory.
     * @param driver WebDriver instance passed from test class.
     */
    public CheckoutOverviewPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    /**
     * Clicks the 'Finish' button to complete the checkout.
     */
    public void completeCheckout() {
        TestUtil.waitForElementVisible(driver, finishButton, 10);
        finishButton.click();
    }

    /**
     * Retrieves the confirmation message shown after successful checkout.
     * @return Confirmation message text.
     */
    public String getConfirmationMessage() {
        TestUtil.waitForElementVisible(driver, confirmationText, 10);
        return confirmationText.getText();
    }

    /**
     * Extracts the prices of all individual items listed on the page.
     * @return List of item prices as Double values.
     */
    public List<Double> getItemPrices() {
        return itemPrices.stream()
                .map(e -> Double.parseDouble(e.getText().replace("$", "")))
                .collect(Collectors.toList());
    }

    /**
     * Extracts and parses the subtotal (item total) value from the summary section.
     * @return Subtotal value as a Double.
     */
    public double getItemTotal() {
        String text = itemTotalLabel.getText();  // e.g. "Item total: $39.98"
        String amount = text.replace("Item total: $", "").trim();
        return Double.parseDouble(amount);
    }

    /**
     * Verifies if an item with the given name is listed on the overview page.
     * @param itemName The name of the product to check for.
     * @return true if the item is present; false otherwise.
     */
    public boolean verifyItemPresent(String itemName) {
        return overviewItemTitles.stream()
                .anyMatch(e -> e.getText().equalsIgnoreCase(itemName));
    }
}
