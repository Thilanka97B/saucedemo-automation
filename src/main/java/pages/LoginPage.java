package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.TestUtil;

/**
 * Represents the Login Page of the SauceDemo application.
 * Provides functionality to perform login using credentials.
 */
public class LoginPage {
    WebDriver driver;

    // Input field for username
    @FindBy(id = "user-name")
    private WebElement usernameField;

    // Input field for password
    @FindBy(id = "password")
    private WebElement passwordField;

    // Login button to submit credentials
    @FindBy(id = "login-button")
    private WebElement loginButton;

    /**
     * Constructor initializes all elements using PageFactory.
     * @param driver WebDriver instance passed from the test class.
     */
    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    /**
     * Performs login action by entering username, password, and clicking login button.
     * @param username Username string to input
     * @param password Password string to input
     */
    public void login(String username, String password) {
        TestUtil.waitForElementVisible(driver, usernameField, 10);
        usernameField.sendKeys(username);
        passwordField.sendKeys(password);
        loginButton.click();
    }
}
