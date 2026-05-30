package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.time.Duration;

/**
 * DriverFactory — creates and manages WebDriver instances per thread
 * (supports Chrome, Edge, Firefox; headless configurable via config.properties)
 */
public class DriverFactory {

    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    private DriverFactory() {}

    public static WebDriver getDriver() {
        if (driverThreadLocal.get() == null) {
            initDriver();
        }
        return driverThreadLocal.get();
    }

    private static void initDriver() {
        String browser   = ConfigReader.get("browser", "chrome").toLowerCase().trim();
        boolean headless = Boolean.parseBoolean(ConfigReader.get("headless", "false"));
        int implicitWait = Integer.parseInt(ConfigReader.get("implicit.wait", "10"));
        int pageLoadTimeout = Integer.parseInt(ConfigReader.get("page.load.timeout", "30"));

        WebDriver driver;

        switch (browser) {
            case "edge" -> {
                WebDriverManager.edgedriver().setup();
                EdgeOptions edgeOptions = new EdgeOptions();
                if (headless) edgeOptions.addArguments("--headless=new");
                edgeOptions.addArguments("--start-maximized", "--disable-notifications");
                driver = new EdgeDriver(edgeOptions);
            }
            case "firefox" -> {
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions ffOptions = new FirefoxOptions();
                if (headless) ffOptions.addArguments("--headless");
                driver = new FirefoxDriver(ffOptions);
            }
            default -> {  // chrome (default)
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = getChromeOptions(headless);

                driver = new ChromeDriver(chromeOptions);
            }
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWait));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(pageLoadTimeout));
        driver.manage().window().maximize();
        driverThreadLocal.set(driver);
    }

    private static ChromeOptions getChromeOptions(boolean headless) {
        ChromeOptions chromeOptions = new ChromeOptions();
        if (headless) chromeOptions.addArguments("--headless=new");
        chromeOptions.addArguments(
                "--start-maximized",
                "--disable-notifications",
                "--disable-infobars",
                "--disable-extensions",
                "--disable-gpu",
                "--no-sandbox",
                "--disable-dev-shm-usage"
        );

// Don't wait for full page load — just wait for DOM ready
        chromeOptions.setPageLoadStrategy(PageLoadStrategy.EAGER);
        return chromeOptions;
    }

    public static void quitDriver() {
        if (driverThreadLocal.get() != null) {
            driverThreadLocal.get().quit();
            driverThreadLocal.remove();
        }
    }
}
