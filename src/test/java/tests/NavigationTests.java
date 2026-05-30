package tests;

import io.qameta.allure.*;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import utils.ConfigReader;

/**
 * TC7  — Verify Test Cases Page
 * TC25 — Verify Scroll Up using Arrow
 * TC26 — Verify Scroll Up without Arrow button
 */
public class NavigationTests extends BaseTest {

    // TC7: Test Cases Page
    @Test
    public void TC7_verifyTestCasesPage() {
        HomePage homePage = new HomePage(driver);
        Assert.assertTrue(homePage.isLogoVisible(), "Home page should be loaded");

        homePage.clickTestCases();

        Assert.assertTrue(driver.getCurrentUrl().contains("test_cases"), "URL should contain 'test_cases'");
        Assert.assertTrue(driver.getTitle().contains("Test Cases"), "Page title should contain 'Test Cases'");
    }

    // TC25: Scroll Up using Arrow
    @Test

    public void TC25_scrollUpUsingArrow() {
        HomePage homePage = new HomePage(driver);
        Assert.assertTrue(homePage.isLogoVisible(), "Home page should be visible");

        homePage.scrollToSubscription();

        Assert.assertTrue(homePage.isScrollUpButtonVisible(), "Scroll-up arrow should be visible after scrolling down");

        homePage.clickScrollUpArrow();

        Assert.assertFalse(driver.findElements(By.cssSelector("#slider")).isEmpty(), "Home page slider should be visible after scrolling to top");
    }

    // ─── TC26: Scroll Up without Arrow
    @Test
    public void TC26_scrollUpWithoutArrow() {
        HomePage homePage = new HomePage(driver);
        Assert.assertTrue(homePage.isLogoVisible(), "Home page should be visible");

        homePage.scrollToSubscription();
        homePage.scrollToTop();
        Assert.assertTrue(homePage.isLogoVisible(), "Home page logo should be visible after scrolling to top");
    }
}
