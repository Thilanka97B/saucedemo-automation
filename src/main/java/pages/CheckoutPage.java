package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.TestUtil;

/**
 * Represents the Checkout Information Page in the SauceDemo application.
 * Allows filling out personal information and proceeding to the overview step.
 */
public class CheckoutPage {
    WebDriver driver;

    // Input field for customer's first name
    @FindBy(id = "first-name")
    private WebElement firstNameField;

    // Input field for customer's last name
    @FindBy(id = "last-name")
    private WebElement lastNameField;

    // Input field for customer's postal/zip code
    @FindBy(id = "postal-code")
    private WebElement postalCodeField;

    // Continue button to proceed to the next step of checkout
    @FindBy(id = "continue")
    private WebElement continueButton;

    /**
     * Constructor to initialize web elements using PageFactory.
     * @param driver WebDriver instance passed from the test class.
     */
    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    /**
     * Fills out the checkout form with provided user information and clicks Continue.
     * @param firstName First name of the user.
     * @param lastName Last name of the user.
     * @param postalCode Postal code or ZIP code of the user.
     */
    public void fillCheckoutForm(String firstName, String lastName, String postalCode) {
        TestUtil.waitForElementVisible(driver, firstNameField, 10);
        firstNameField.sendKeys(firstName);
        lastNameField.sendKeys(lastName);
        postalCodeField.sendKeys(postalCode);
        continueButton.click();
    }
}
