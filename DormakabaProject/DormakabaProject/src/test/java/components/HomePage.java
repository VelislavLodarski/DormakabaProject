package components;

import core.BasePageAction;
import org.openqa.selenium.By;
import org.testng.Assert;

import static utils.Browser.takeScreenshot;
import static utils.Parameters.*;

    public class HomePage extends BasePageAction {
    private static By CONSENT_BTN = By.cssSelector("button[class*='consent']");
    private By PRODUCTS_LINK = By.xpath("//a[@href='/products']"); // Locator for "Products" menu

    //navigate to the home page and wait for the consent button to be displayed, then click
    public  void navigateHomePage() {
        openUrl(BASE_URL);
        takeScreenshot("screenshots/HomePage.png"); // Take a screenshot of the home page and validating popup is displayed
        click(CONSENT_BTN);
    }

    //An example if you want to navigate to a specific page
    public void navigateToProductPage() {
        openUrl(BASE_URL + PRODUCTS);
    }

    public void navigateToProducts() {
        click(PRODUCTS_LINK);
        isProductPageLoaded();
        if (isProductPageLoaded()) {
            System.out.println("Product page is loaded");
        } else {
            System.err.println("Product page did not load correctly");
            Assert.fail("Product page did not load correctly");
        }
    }
    // Checks if the URL ends with "products". Could be checked in separate method
        public boolean isProductPageLoaded()
        {
            String currentUrl = driver.getCurrentUrl();
            return currentUrl.endsWith("products");
        }
}
