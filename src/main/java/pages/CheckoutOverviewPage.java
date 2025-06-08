package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.TestUtil;

import java.util.List;
import java.util.stream.Collectors;

public class CheckoutOverviewPage {
    WebDriver driver;

    @FindBy(id = "finish")
    private WebElement finishButton;

    @FindBy(className = "complete-header")
    private WebElement confirmationText;

    @FindBy(css = ".inventory_item_price")
    private List<WebElement> itemPrices;

    @FindBy(className = "summary_subtotal_label")
    private WebElement itemTotalLabel;

    @FindBy(css = ".cart_item .inventory_item_name")
    private List<WebElement> overviewItemTitles;

    public CheckoutOverviewPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void completeCheckout() {
        TestUtil.waitForElementVisible(driver, finishButton, 10);
        finishButton.click();
    }

    public String getConfirmationMessage() {
        TestUtil.waitForElementVisible(driver, confirmationText, 10);
        return confirmationText.getText();
    }

    public List<Double> getItemPrices() {
        return itemPrices.stream()
                .map(e -> Double.parseDouble(e.getText().replace("$", "")))
                .collect(Collectors.toList());
    }

    public double getItemTotal() {
        String text = itemTotalLabel.getText();  // e.g. "Item total: $39.98"
        String amount = text.replace("Item total: $", "").trim();
        return Double.parseDouble(amount);
    }

    /**
     * Verify if an item with the given name is present on the overview page.
     * @param itemName The product name to check for
     * @return true if item is present, false otherwise
     */
    public boolean verifyItemPresent(String itemName) {
        return overviewItemTitles.stream()
                .anyMatch(e -> e.getText().equalsIgnoreCase(itemName));
    }
}
