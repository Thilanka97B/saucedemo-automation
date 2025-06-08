package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.*;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Represents the Inventory Page of the SauceDemo application.
 * Handles product sorting, product retrieval, and cart interactions.
 */
public class InventoryPage {
    WebDriver driver;
    private static final Logger logger = Logger.getLogger(InventoryPage.class.getName());

    // All product item containers on the page
    @FindBy(css = ".inventory_item")
    private List<WebElement> productItems;

    // Dropdown element used to sort products
    @FindBy(className = "product_sort_container")
    private WebElement sortDropdown;

    // Titles of each product listed
    @FindBy(css = ".inventory_item_name")
    private List<WebElement> productTitles;

    // Price labels of each product
    @FindBy(css = ".inventory_item_price")
    private List<WebElement> productPrices;

    // "Add to Cart" button for the Backpack
    @FindBy(id = "add-to-cart-sauce-labs-backpack")
    private WebElement addBackpack;

    // "Add to Cart" button for the Bike Light
    @FindBy(id = "add-to-cart-sauce-labs-bike-light")
    private WebElement addBikeLight;

    // Shopping cart icon element
    @FindBy(className = "shopping_cart_link")
    private WebElement cartIcon;

    /**
     * Constructor that initializes page elements using PageFactory.
     * @param driver WebDriver instance passed from the test class.
     */
    public InventoryPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    /**
     * Retrieves a list of all product titles displayed on the inventory page.
     * @return List of product names as strings.
     */
    public List<String> getProductTitles() {
        List<String> titles = productTitles.stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
        logger.info("Product titles: " + titles);
        return titles;
    }

    /**
     * Sorts the products using the dropdown based on visible text value.
     * @param value Visible text of the sort option (e.g., "Price (low to high)").
     */
    public void sortBy(String value) {
        logger.info("Selecting sort option: " + value);
        new Select(sortDropdown).selectByVisibleText(value);
    }

    /**
     * Retrieves the top N product prices from the displayed list.
     * @param n Number of prices to retrieve.
     * @return List of prices as double values.
     */
    public List<Double> getTopNPrices(int n) {
        List<Double> prices = productPrices.stream()
                .limit(n)
                .map(e -> Double.parseDouble(e.getText().replace("$", "")))
                .collect(Collectors.toList());
        logger.info("Top " + n + " prices: " + prices);
        return prices;
    }

    /**
     * Counts the number of product titles that contain a given keyword.
     * @param keyword Word or phrase to search for in product titles.
     * @return Number of matching titles.
     */
    public long countTitlesWith(String keyword) {
        long count = productTitles.stream()
                .filter(e -> e.getText().toLowerCase().contains(keyword.toLowerCase()))
                .count();
        logger.info("Number of product titles containing '" + keyword + "': " + count);
        return count;
    }

    /**
     * Adds both the Backpack and Bike Light products to the shopping cart.
     */
    public void addToCartBackpackAndBikeLight() {
        logger.info("Adding Backpack and Bike Light to cart.");
        addBackpack.click();
        addBikeLight.click();
    }

    /**
     * Clicks the cart icon to navigate to the cart page.
     */
    public void goToCart() {
        logger.info("Navigating to cart.");
        cartIcon.click();
    }
}
