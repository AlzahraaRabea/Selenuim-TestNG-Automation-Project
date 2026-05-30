package pages;

import org.openqa.selenium.WebDriver;
import utils.ConfigReader;
import utils.SeleniumHelper;

/**
 * BasePage — parent of all Page Objects.
 */
public abstract class BasePage {

    protected final WebDriver driver;
    protected final SeleniumHelper helper;
    protected final String baseUrl;

    public BasePage(WebDriver driver) {
        this.driver  = driver;
        this.helper  = new SeleniumHelper(driver);
        this.baseUrl = ConfigReader.getBaseUrl();
    }

    /** Navigate directly to this page's URL. */
    public abstract void navigateTo();
}
