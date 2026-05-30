package tests;

import io.qameta.allure.Attachment;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import utils.ConfigReader;
import utils.DriverFactory;
import utils.SeleniumHelper;

/**
 * BaseTest — parent of all test classes.
 * Handles driver lifecycle, ad popups, screenshots on failure, and Allure attachments.
 */
public abstract class BaseTest {

    protected WebDriver driver;
    protected SeleniumHelper helper;

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        driver = DriverFactory.getDriver();
        helper = new SeleniumHelper(driver);
        driver.get(ConfigReader.getBaseUrl());

        // Handle any ad popup that appears on page load
        helper.handleAdPopup();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            boolean screenshotOnFail = Boolean.parseBoolean(
                    ConfigReader.get("screenshot.on.failure", "true"));
            if (screenshotOnFail) {
                attachScreenshot();
            }
        }
//        DriverFactory.quitDriver();
    }

    @Attachment(value = "Screenshot on Failure", type = "image/png")
    private byte[] attachScreenshot() {
        try {
            return ((org.openqa.selenium.TakesScreenshot) driver)
                    .getScreenshotAs(org.openqa.selenium.OutputType.BYTES);
        } catch (Exception e) {
            return new byte[0];
        }
    }
}