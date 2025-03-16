
package testsPositive;

import components.HomePage;
import core.BasePageAction;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import components.ProductPage;
import components.CartPage;

import static utils.Browser.quit;

public class ShopExerciseTest extends BasePageAction {
    private HomePage homePage;
    private ProductPage productPage;
    private CartPage cartPage;

//In case you want to have all test cases in one flow and want to initiate browser before class. More info --> https://testng.org/annotations.html
@BeforeClass
    public void setup(){
    homePage = new HomePage();
    productPage = new ProductPage();
    cartPage = new CartPage();

    }

@Test (description = "Test Case 1 : Add coupe of items in cart")
    public void addCoupleOfItemsInCart(){
    homePage.navigateHomePage();
    homePage.navigateToProducts();
    productPage.openSection("Men");
    productPage.openProductType("Jeans");
    productPage.addMultipleProductsToCart(2);
    cartPage.navigateToCart();
    cartPage.verifyCartItems(2); // In this approach Assertion is hidden from the test cases.
    Assert.assertTrue(cartPage.verifyProductInCart("Soft Stretch Jeans"), "Men Tshirt is missing from the cart!");
    Assert.assertTrue(cartPage.verifyProductInCart("Regular Fit Straight Jeans"), "Blue Jeans is missing from the cart!");
    cartPage.proceedToCheckout();

    }
//Note: here you can proceed with another Test Flow, but the Annotation is BeforeClass More info --> https://testng.org/annotations.html
@Test  (description = "Test Case 2: TEST")
 public void secondTest(){

     System.out.println("Write test cases --> Here");

 }


@AfterClass
    public void tearDownOnce(){
        quit();
    }
}
