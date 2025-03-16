
package components;

import core.BasePageAction;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.List;

import static org.testng.AssertJUnit.assertTrue;
import static utils.Browser.takeScreenshot;

public class ProductPage extends BasePageAction {

    private By JEANS_CATEGORY = By.xpath("//a[contains(text(),'Jeans')]"); // Locator for "Jeans for Men"
    private By TSHIRT_CATEGORY = By.xpath("//a[contains(text(),'Tshirts')]"); // Locator for "Tshirts
    private By FIRST_PRODUCT_ADD_TO_CART = By.xpath("(//a[@class='btn btn-default add-to-cart'])[1]"); // First item Add-to-cart
    private By SECOND_PRODUCT_ADD_TO_CART = By.xpath("(//a[@class='btn btn-default add-to-cart'])[2]"); // Second item Add-to-cart
    private By VIEW_CART_BUTTON = By.xpath("//u[contains(text(),'View Cart')]"); // "View Cart" button in pop-up
    private By MEN_SECTION = By.xpath("//a[@href='#Men']/span/i");
    private By WOMEN_SECTION = By.xpath("//*[@id='accordian']/div[1]/div[1]/h4/a/span/i");
    private By JEANS_PRODUCT_PAGE = By.xpath("//h2[contains(text(),'Men - Jeans Products')]"); // Locator
    private By TSHIRT_PRODUCT_PAGE = By.xpath("//h2[contains(text(),'Men - Tshirts Products')]"); // Locator
    private final By ADD_TO_CART_BTN = By.xpath("//div[@class='productinfo text-center']/a[contains(text(),'Add to cart')]");
    private final By CONTINUE_SHOPPING_BTN = By.xpath("//button[contains(text(),'Continue Shopping')]");
    private final By PRODUCT_NAMES = By.xpath("//div[@class='productinfo text-center']/p");

 //simple method to select Jeans category
    public void selectJeansCategory() {
        click(JEANS_CATEGORY);
    }

    public void addFirstItemToCart() {
        click(FIRST_PRODUCT_ADD_TO_CART);
    }

    public void addSecondItemToCart() {
        click(SECOND_PRODUCT_ADD_TO_CART);
    }

    public void goToCart() {
        click(VIEW_CART_BUTTON);
    }

    public void openSection(String section) {
        switch (section) {
            case "Men":
                waitForElementPresence(MEN_SECTION).click();

                break;
            case "Women":
                waitForElementPresence(WOMEN_SECTION).click();

                break;
            default:
                throw new IllegalArgumentException("Invalid section: " + section);
        }
    }

    //Implementing this method with 2 different validation - assertTrue and isElementVisible to show different ways of validation
    /**
     * Opens a product type page based on the provided product type.
     * Takes a screenshot of the product page after it is loaded.
     *
     * @param productType the type of product to open (e.g., "Tshirts", "Jeans")
     * @throws IllegalArgumentException if the provided product type is invalid
     */

    public void openProductType(String productType) {
        switch (productType) {
            case "Tshirts":
                waitForElementPresence(TSHIRT_CATEGORY).click(); //Different implementation, more suitable for getting element
                isElementVisible(TSHIRT_PRODUCT_PAGE);
                takeScreenshot("screenshots/TshirtProductPage.png");
                break;

                case "Jeans":
                waitForElementToBeClickable(JEANS_CATEGORY).click(); //implementation for waiting for element to be clickable
                assertTrue(isElementVisible(JEANS_PRODUCT_PAGE));
                takeScreenshot("screenshots/JeansProductPage.png");

                break;

                default:
                throw new IllegalArgumentException("Invalid product type: " + productType);
        }
    }

    //In case JAVA DOC is needed
    /**
     * Adds a specified number of products to the cart.
     * Throws an IllegalArgumentException if the requested count exceeds available products.
     *
     * @param count the number of products to add to the cart
     */
    public void addMultipleProductsToCart(int count) {
        List<WebElement> products = waitForElementPresence(ADD_TO_CART_BTN).findElements(ADD_TO_CART_BTN);

        if (count > products.size()) {
            throw new IllegalArgumentException("Requested count exceeds available products. Available: " + products.size() + ", Requested: " + count);
        }

        for (int i = 0; i < count; i++) {
            products.get(i).click(); // Click "Add to Cart" button
            waitForElementToBeClickable(CONTINUE_SHOPPING_BTN);
            waitForElementPresence(CONTINUE_SHOPPING_BTN).click(); // Click "Continue Shopping"
        }
    }

    // Method to add a specified number of items to the cart, returns false if the count is invalid
    /**
     * Adds a specified number of items to the cart.
     * Returns false if the count is invalid (greater than available items or less than or equal to zero).
     *
     * @param count the number of items to add to the cart
     * @return true if items were added successfully, false otherwise
     */
    public boolean addInvalidNumberItensToCart (int count) {
        List<WebElement> addButtons = waitForElementPresence(ADD_TO_CART_BTN).findElements(ADD_TO_CART_BTN);

        if (count > addButtons.size() || count <= 0) {
            return false; // validating Invalid count
        }

        for (int i = 0; i < count; i++) {
            addButtons.get(i).click(); // Click Add to Cart button
        }

        return true; // Products added successfully
    }

    // Adds a product to the cart by its name, returns true if successful, false otherwise
    /**
     * Adds a product to the cart by its name.
     *
     * @param productName the name of the product to add to the cart
     * @return true if the product was added successfully, false otherwise
     */
    public boolean addProductToCart(String productName) {
        List<WebElement> products = waitForElementPresence(PRODUCT_NAMES).findElements(PRODUCT_NAMES);
        List<WebElement> addButtons = waitForElementPresence(ADD_TO_CART_BTN).findElements(ADD_TO_CART_BTN);

        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getText().trim().equalsIgnoreCase(productName)) {
                addButtons.get(i).click();
                return true;
            }
        }

        return false;
    }

    public void verifyNonExistentProductCannotBeAdded(String productName) {
        List<WebElement> products = waitForElementPresence(PRODUCT_NAMES).findElements(PRODUCT_NAMES);
        List<WebElement> addButtons = waitForElementPresence(ADD_TO_CART_BTN).findElements(ADD_TO_CART_BTN);

        boolean isProductAdded = false;
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getText().trim().equalsIgnoreCase(productName)) {
                addButtons.get(i).click(); // Click Add to Cart button
                isProductAdded = true;
                break;
            }
        }
        Assert.assertFalse(isProductAdded, "Non-existent product should not be added to the cart!");
    }

}



