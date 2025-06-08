package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.*;
import utils.TestUtil;

import java.util.List;
import java.util.stream.Collectors;

public class CartPage {
    WebDriver driver;

    @FindBy(id = "checkout")
    private WebElement checkoutButton;

    @FindBy(css = ".cart_item .inventory_item_name")
    private List<WebElement> cartItemNames;

    public CartPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void proceedToCheckout() {
        TestUtil.waitForElementVisible(driver, checkoutButton, 10);
        checkoutButton.click();
    }

    // New method: Verify expected items are present in cart
    public boolean verifyItemsInCart(String... expectedItems) {
        List<String> actualItemNames = cartItemNames.stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());

        for (String expected : expectedItems) {
            if (!actualItemNames.contains(expected)) {
                return false;
            }
        }
        return true;
    }
}
