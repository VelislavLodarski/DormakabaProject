package core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.NoSuchElementException;
import utils.Browser;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.function.Function;

public class BasePageAction {

    private static final Logger logger = LogManager.getLogger(BasePageAction.class);
    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePageAction() {
        this.driver = Browser.getInstance("chrome");
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }
//This method types text into an element. Good method for input fields such as Login, Password, etc.
    protected void type(By locator, String text) {
        logger.info("Typing '{}' into {}", text, locator);
        waitForElement(locator, ExpectedConditions::visibilityOfElementLocated).sendKeys(text);
    }
//This method clicks on an element
    protected void click(By locator) {
        logger.info("Click on '{}' ", locator);
        waitForElement(locator, ExpectedConditions::elementToBeClickable).click();
    }
//This method opens a URL in the browser
    protected void openUrl(String url) {
        logger.info("Opening URL: {}", url);
        driver.get(url);
    }
//This method gets the text from an element and returns it. Good method for verification
    protected String getText(By locator) {
        logger.info("Getting text from: '{}'", locator);
        return waitForElement(locator, ExpectedConditions::visibilityOfElementLocated).getText();
    }

//This method waits for a specific condition to be met before returning the element
    private WebElement waitForElement(By locator, Function<By, ExpectedCondition<WebElement>> condition) {
        logger.debug("Waiting for condition on: {}", locator);
        return wait.until(condition.apply(locator));
    }
//In case you need to switch to an iframe, you can use the following method
    protected void waitForIframeAndSwitchToIt(By locator) {
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(locator));
    }

//example of a method that waits for a specific number of seconds
    protected void waitForSeconds(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException ignored) {}
    }
// method that waits for a specific number of milliseconds
    public WebElement waitForElementPresence(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    //reusing existing wait instance
    public WebElement waitForElementToBeClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

// method that waits for an element to be visible
    public boolean isElementVisible(By locator) {
        try {
            return driver.findElement(locator).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

}
