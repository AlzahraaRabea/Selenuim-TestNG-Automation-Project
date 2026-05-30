package tests;

import com.fasterxml.jackson.databind.JsonNode;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.HomePage;
import pages.ProductsPage;
import utils.JsonReader;

/**
 * TC10 — Verify Subscription in Home page
 * TC11 — Verify Subscription in Cart page
 */

public class SubscriptionTests extends BaseTest {

    // TC10: Subscription on Home Page
    @Test

    public void TC10_subscriptionOnHomePage() {
        JsonNode product = JsonReader.getProduct("subscribeEmail");

        HomePage homePage = new HomePage(driver);
        Assert.assertTrue(homePage.isLogoVisible(), "Home page should be visible");

        homePage.subscribeWithEmail(product.get("email").asText());

        Assert.assertTrue(homePage.isSubscribeSuccessVisible(),
            "Subscription success message should be displayed on home page");
    }

    //TC11: Subscription on Cart Page
    @Test
    public void TC11_subscriptionOnCartPage() {
        JsonNode product = JsonReader.getProduct("subscribeEmail");

        CartPage cartPage = new CartPage(driver);
        cartPage.navigateTo();

        driver.findElement(org.openqa.selenium.By.cssSelector("footer"))
            .findElement(org.openqa.selenium.By.id("susbscribe_email"))
            .sendKeys(product.get("email").asText());

        driver.findElement(org.openqa.selenium.By.id("subscribe")).click();

        boolean successVisible = driver.findElement(
                org.openqa.selenium.By.id("success-subscribe")).isDisplayed();

        Assert.assertTrue(successVisible, "Subscription success message should be displayed on cart page footer");
    }
}
