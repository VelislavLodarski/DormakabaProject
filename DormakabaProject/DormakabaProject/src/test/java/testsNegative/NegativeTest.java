package testsNegative;

import components.CartPage;
import components.HomePage;
import components.ProductPage;
import core.BasePageAction;
import org.testng.Assert;
import org.testng.annotations.*;
import utils.Browser;

import static utils.Browser.getInstance;
import static utils.Browser.quit;

public class NegativeTest extends BasePageAction {
    private HomePage homePage;
    private ProductPage productPage;
    private CartPage cartPage;


//In case you want to run the tests in parallel, you can use the following annotation
    @BeforeMethod
    public void setup(){
        getInstance("chrome");
        homePage = new HomePage();
        productPage = new ProductPage();
        cartPage = new CartPage();

    }

    @Test(description = "Test Case: Attempt to More Products than Available")
    public void addingInvalidNumberOfItems(){
        homePage.navigateHomePage();
        homePage.navigateToProducts();
        productPage.openSection("Men");
        productPage.openProductType("Jeans");
        boolean isProductsAdded = productPage.addInvalidNumberItensToCart(5); // Assuming 5 is an invalid count
        Assert.assertFalse(isProductsAdded, "Invalid count of products should not be added to the cart!");
    }

    @Test  (description = "Test Case : Attempt to Add Non-Existent Product")
    public void addNonExistingProduct(){
        homePage.navigateHomePage();
        homePage.navigateToProducts();
        productPage.openSection("Men");
        productPage.openProductType("Jeans");
        boolean isProductAdded = productPage.addProductToCart("Non-Existent Product");
        Assert.assertFalse(isProductAdded, "Non-existent product should not be added to the cart!");

    }


    @AfterMethod
    public void tearDown() {

            quit();
        }

}