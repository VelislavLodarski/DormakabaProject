package components;

import core.BasePageAction;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

import static utils.Browser.takeScreenshot;

public class CartPage extends BasePageAction {

    private By CART_ITEMS = By.xpath("//table[@id='cart_info_table']//tbody/tr"); // Locator for cart items
    private By CART_LINK = By.xpath("//ul[contains(@class, 'nav')]//a[contains(text(), 'Cart')]"); // Locator for "Cart" link
    private final By CART_PRODUCT_NAMES = By.xpath("//td[@class='cart_description']//h4"); // Locator for product names in cart
    private final By PROCEED_TO_CHECKOUT_BTN = By.xpath("//a[contains(text(),'Proceed To Checkout')]");

    public int getNumberOfItemsInCart() {
        List<WebElement> items = waitForElementPresence(CART_ITEMS).findElements(CART_ITEMS);
        return items.size();
    }

  //Verify the number of items in the cart. Checks the quantity of items in the cart
    public boolean verifyCartItems(int expectedItemCount) {
        int actualItemCount = getNumberOfItemsInCart();
        if (actualItemCount != expectedItemCount) {
            System.err.println("Expected " + expectedItemCount + " items, but found " + actualItemCount);
            return false;
        }
        return true;
    }

// Verify specific product present in the cart.
    public boolean verifyProductInCart(String productName) {
        List<WebElement> products = waitForElementPresence(CART_PRODUCT_NAMES).findElements(CART_PRODUCT_NAMES);
        for (WebElement product : products) {
            if (product.getText().trim().equalsIgnoreCase(productName)) {
                return true;
            }
        }
        return false;
    }

    public void navigateToCart() {
        click( CART_LINK);
        takeScreenshot("screenshots/CartPage.png");
    }

    public void proceedToCheckout() {
        click(PROCEED_TO_CHECKOUT_BTN);
        takeScreenshot("screenshots/Checkout PopUp.png");
    }
}
