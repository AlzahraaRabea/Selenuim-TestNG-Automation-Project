package utils;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * SeleniumHelper — reusable low-level Selenium operations.
 * Wraps explicit waits, scrolling, screenshots, JS execution, etc.
 */
public class SeleniumHelper {

    private final WebDriver driver;
    private final WebDriverWait wait;

    public SeleniumHelper(WebDriver driver) {
        this.driver = driver;
        int explicitWait = Integer.parseInt(ConfigReader.get("explicit.wait", "15"));
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(explicitWait));
    }

    // ─── Wait Methods ─────────────────────────────────────────────────────────

    public WebElement waitForVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public WebElement waitForClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public boolean waitForTextPresent(By locator, String text) {
        return wait.until(ExpectedConditions.textToBePresentInElementLocated(locator, text));
    }

    public boolean waitForUrlContains(String fragment) {
        return wait.until(ExpectedConditions.urlContains(fragment));
    }

    public boolean waitForTitleContains(String title) {
        return wait.until(ExpectedConditions.titleContains(title));
    }

    // ─── Interaction Methods ──────────────────────────────────────────────────

    public void click(By locator) {
        waitForClickable(locator).click();
    }

    public void type(By locator, String text) {
        WebElement el = waitForVisible(locator);
        el.clear();
        el.sendKeys(text);
    }

    public String getText(By locator) {
        return waitForVisible(locator).getText().trim();
    }

    public boolean isDisplayed(By locator) {
        try {
            return driver.findElement(locator).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public void selectByVisibleText(By locator, String text) {
        new Select(waitForVisible(locator)).selectByVisibleText(text);
    }

    public void selectByValue(By locator, String value) {
        new Select(waitForVisible(locator)).selectByValue(value);
    }

    // ─── Scroll Methods ───────────────────────────────────────────────────────

    public void scrollToElement(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public void scrollToBottom() {
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");
    }

    public void scrollToTop() {
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 0);");
    }

    // ─── JS Click (for overlapping elements) ─────────────────────────────────

    public void jsClick(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    public void jsClick(By locator) {
        jsClick(driver.findElement(locator));
    }

    // ─── Screenshot ───────────────────────────────────────────────────────────

    public byte[] takeScreenshot() {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

    public void saveScreenshot(String testName) {
        try {
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String fileName = "screenshots/" + testName + "_" + timestamp + ".png";
            Path dir = Paths.get("screenshots");
            if (!Files.exists(dir)) Files.createDirectories(dir);
            byte[] bytes = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            Files.write(Paths.get(fileName), bytes);
        } catch (IOException e) {
            System.err.println("Could not save screenshot: " + e.getMessage());
        }
    }

    // ─── Navigation ───────────────────────────────────────────────────────────

    public void navigateTo(String url) {
        driver.get(url);
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public String getPageTitle() {
        return driver.getTitle();
    }
    // ─── Ad / Popup Handler ───────────────────────────────────────────────────
    public void handleAdPopup() {
        // Remove overlay ads
        try {
            ((org.openqa.selenium.JavascriptExecutor) driver)
                    .executeScript(
                            "document.querySelectorAll('#ad_position_box, .adsbygoogle, #aswift_1_host')" +
                                    ".forEach(el => el.remove());"
                    );
        } catch (Exception e) {
            System.out.println("Could not remove overlay ad: " + e.getMessage());
        }
    }
    // ─── Alert handling ───────────────────────────────────────────────────────

    public void acceptAlert() {
        wait.until(ExpectedConditions.alertIsPresent()).accept();
    }

     public void dismissAlert() {
        wait.until(ExpectedConditions.alertIsPresent()).dismiss();
    }

     public String getAlertText() {
        return wait.until(ExpectedConditions.alertIsPresent()).getText();
    }


}
