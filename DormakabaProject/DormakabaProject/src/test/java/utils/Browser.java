package utils;

import io.github.bonigarcia.wdm.WebDriverManager; // In case you are using WebDriverManager is allowed
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;


import java.io.File;
import java.io.IOException;


public class Browser {
    private static final Logger logger = LogManager.getLogger(Browser.class);

    private static WebDriver driver;


    public static WebDriver getInstance(String browserType) {
        if (driver == null) {

            if ("chrome".equalsIgnoreCase(browserType)) {
                logger.info("Launching browser: Chrome");

                ChromeOptions options = new ChromeOptions();
                options.addArguments(
                        "--lang=en-US",
                        "--no-sandbox",
                        "--disable-gpu",
                        "--start-maximized",
                        "--force-device-scale-factor=0.9",
                        "--disable-extensions",
                        "--disable-web-security",
                        "--allow-running-insecure-content",
                        "--disable-dev-shm-usage",
                        "--disable-infobars",
                        "--no-default-browser-check",
                        "--no-first-run",
                        "--disable-default-apps",
                        "--disable-popup-blocking",
                        "--disable-translate",
                        "--disable-background-timer-throttling",
                        "--disable-renderer-backgrounding",
                        "--disable-device-discovery-notifications");

                driver = new ChromeDriver(options);
            } else {  // Default to Firefox if no valid browserType is provided or it's Firefox. Note: Decided to ignore EDGE and IE for now
                logger.info("Launching browser: Firefox");

                FirefoxOptions options = new FirefoxOptions();
                options.addArguments(
                        "--lang=en-US",
                        "--disable-extensions",
                        "--disable-web-security",
                        "--allow-running-insecure-content",
                        "--disable-infobars",
                        "--no-default-browser-check",
                        "--disable-popup-blocking",
                        "--disable-translate");

                driver = new FirefoxDriver(options);
            }
        }
        return driver;
    }

    public static void takeScreenshot(String fileName) {
        if (driver instanceof TakesScreenshot) {
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            try {
                FileUtils.copyFile(screenshot, new File(fileName));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Screenshot cannot be taken. WebDriver does not support taking screenshots.");
        }
    }

    // additional if statement for the browser quit was added
    public static void quit() {
        if (driver != null) {
            logger.info("Quitting the browser");
            driver.quit();
            driver = null;  // Ensure the instance is reset
            logger.info("Browser quit successfully");
        }
    }
}