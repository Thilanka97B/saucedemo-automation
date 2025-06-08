package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.*;
import org.openqa.selenium.support.ui.Select;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class InventoryPage {
    WebDriver driver;
    private static final Logger logger = Logger.getLogger(InventoryPage.class.getName());

    @FindBy(css = ".inventory_item")
    private List<WebElement> productItems;

    @FindBy(className = "product_sort_container")
    private WebElement sortDropdown;

    @FindBy(css = ".inventory_item_name")
    private List<WebElement> productTitles;

    @FindBy(css = ".inventory_item_price")
    private List<WebElement> productPrices;

    @FindBy(id = "add-to-cart-sauce-labs-backpack")
    private WebElement addBackpack;

    @FindBy(id = "add-to-cart-sauce-labs-bike-light")
    private WebElement addBikeLight;

    @FindBy(className = "shopping_cart_link")
    private WebElement cartIcon;

    public InventoryPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public List<String> getProductTitles() {
        List<String> titles = productTitles.stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
        logger.info("Product titles: " + titles);
        return titles;
    }

    public void sortBy(String value) {
        logger.info("Selecting sort option: " + value);
        new Select(sortDropdown).selectByVisibleText(value);
    }

    public List<Double> getTopNPrices(int n) {
        List<Double> prices = productPrices.stream()
                .limit(n)
                .map(e -> Double.parseDouble(e.getText().replace("$", "")))
                .collect(Collectors.toList());
        logger.info("Top " + n + " prices: " + prices);
        return prices;
    }

    public long countTitlesWith(String keyword) {
        long count = productTitles.stream()
                .filter(e -> e.getText().toLowerCase().contains(keyword.toLowerCase()))
                .count();
        logger.info("Number of product titles containing '" + keyword + "': " + count);
        return count;
    }

    public void addToCartBackpackAndBikeLight() {
        logger.info("Adding Backpack and Bike Light to cart.");
        addBackpack.click();
        addBikeLight.click();
    }

    public void goToCart() {
        logger.info("Navigating to cart.");
        cartIcon.click();
    }
}
