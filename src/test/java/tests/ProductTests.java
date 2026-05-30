package tests;

import com.fasterxml.jackson.databind.JsonNode;
import io.qameta.allure.*;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.*;
import utils.JsonReader;

import java.util.Objects;

/**
 * TC8  — Verify All Products and product detail page
 * TC9  — Search Product
 * TC17 — Remove Products From Cart
 * TC18 — View Category Products
 * TC19 — View & Cart Brand Products
 * TC20 — Search Products and Verify Cart After Login
 * TC21 — Add review on product
 * TC22 — Add to cart from Recommended items
 */

public class ProductTests extends BaseTest {

    // TC8: All Products & Product Detail
    @Test
    public void TC8_allProductsAndProductDetail() {
        HomePage homePage = new HomePage(driver);
        homePage.clickProducts();

        ProductsPage productsPage = new ProductsPage(driver);
        Assert.assertTrue(productsPage.isProductsPageVisible(), "Products list should be visible");
        Assert.assertTrue(productsPage.getProductCount() > 0, "There should be at least one product listed");

        // Click first product's View Product
        productsPage.clickViewProduct(0);

        ProductDetailPage detailPage = new ProductDetailPage(driver);
        Assert.assertTrue(detailPage.isProductDetailPageLoaded(), "Should navigate to product detail URL");
        Assert.assertTrue(detailPage.isProductInfoVisible(), "Product name, category and price should be visible");
    }


    //TC9: Search Product
    @Test

    public void TC9_searchProduct() {
        JsonNode product = JsonReader.getProduct("searchProduct");
        HomePage homePage = new HomePage(driver);
        homePage.clickProducts();
        ProductsPage productsPage = new ProductsPage(driver);
        Assert.assertTrue(productsPage.isProductsPageVisible(),
                "Should be navigated to ALL PRODUCTS page");

        productsPage.searchForProduct(product.get("keyword").asText());

        Assert.assertTrue(productsPage.isSearchResultsVisible(), "Search results section should be visible");
        Assert.assertTrue(productsPage.getSearchResultCount() >= product.get("expectedMinResults").asInt(),
                "Search results count should be >= " + product.get("expectedMinResults").asInt());
    }


    // TC17: Remove Products From Cart

    @Test

    public void TC17_removeProductFromCart() {

        HomePage homePage = new HomePage(driver);
        homePage.clickProducts();

        ProductsPage productsPage = new ProductsPage(driver);
        productsPage.addFirstProductToCart();
        productsPage.clickViewCart();

        CartPage cartPage = new CartPage(driver);
        Assert.assertTrue(cartPage.getCartItemCount() > 0, "Cart should have at least one item");

        cartPage.removeFirstProduct();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {
        }
        Assert.assertTrue(cartPage.isCartEmpty() || cartPage.getCartItemCount() == 0,
                "Cart should be empty after removing the product");
    }


    // TC18: View Category Products
    @Test
    public void TC18_viewCategoryProducts() {

        driver.get("https://www.automationexercise.com");

        // Click Women category
        driver.findElement(org.openqa.selenium.By.xpath("//a[@href='#Women']")).click();

        driver.findElement(org.openqa.selenium.By.xpath(
                "//div[@id='Women']//a[contains(@href,'category_products')]")).click();

        Assert.assertTrue(driver.getCurrentUrl().contains("category_products"),
                "URL should contain 'category_products'");
        Assert.assertFalse(driver.findElements(By.cssSelector(".features_items .col-sm-4")).isEmpty(),
                "Category products should be visible");
    }

    // TC19: View & Cart Brand Products
    @Test

    public void TC19_viewBrandProducts() {
        HomePage homePage = new HomePage(driver);
        homePage.clickProducts();

        ProductsPage productsPage = new ProductsPage(driver);
        Assert.assertTrue(productsPage.isBrandsSidebarVisible(),
                "Brands sidebar should be visible on products page");

        // Click first available brand link
        driver.findElement(By.cssSelector(".brands_products .brands-name li:first-child a")).click();
        helper.handleAdPopup();
        Assert.assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).contains("brand_products"), "URL should contain 'brand_products'");
        Assert.assertFalse(driver.findElements(By.cssSelector(".features_items .col-sm-4")).isEmpty(),
                "Brand products should be listed");
    }

    // TC20: Search & Verify Cart After Login
    @Test

    public void TC20_searchAndVerifyCartAfterLogin() {
        JsonNode product = JsonReader.getProduct("searchProduct");
        JsonNode user = JsonReader.getUser("loginUser");

        HomePage homePage = new HomePage(driver);
        homePage.clickProducts();

        ProductsPage productsPage = new ProductsPage(driver);
        productsPage.searchForProduct(product.get("keyword").asText());
        productsPage.addFirstProductToCart();
        productsPage.clickContinueShopping();
helper.handleAdPopup();
        homePage.clickCart();
        CartPage cartPage = new CartPage(driver);
        int itemsBefore = cartPage.getCartItemCount();
        Assert.assertTrue(itemsBefore > 0, "Cart should have items before login");


        cartPage.clickProceedToCheckout();
        cartPage.clickRegisterLoginInModal();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(user.get("email").asText(), user.get("password").asText());

        homePage.clickCart();
        Assert.assertTrue(cartPage.getCartItemCount() > 0, "Cart should still have items after logging in");
    }

    // TC21: Add Review on Product
    @Test

    public void TC21_addReviewOnProduct() {
        JsonNode user = JsonReader.getUser("loginUser");

        HomePage homePage = new HomePage(driver);
        homePage.clickProducts();

        ProductsPage productsPage = new ProductsPage(driver);
        productsPage.clickViewProduct(0);

        ProductDetailPage detailPage = new ProductDetailPage(driver);
        Assert.assertTrue(detailPage.isProductDetailPageLoaded(), "Product detail page should be loaded");
        helper.handleAdPopup();
        detailPage.submitReview(
                user.get("name").asText(),
                user.get("email").asText(),
                "Great product and Quality is very good."
        );

        Assert.assertTrue(detailPage.isReviewSuccessVisible(),
                "Review success message should be shown");
    }

    // TC22: Add to Cart from Recommended Items
        @Test
        public void TC22_addToCartFromRecommendedItems () {

            HomePage homePage = new HomePage(driver);
            homePage.navigateTo();

            helper.scrollToBottom();
            helper.handleAdPopup();


            Assert.assertTrue(helper.isDisplayed(By.cssSelector("#recommended-item-carousel")),
                    "Recommended items carousel should be visible");

            // Click Add to Cart on first recommended item
            helper.click(By.cssSelector("#recommended-item-carousel .item.active .add-to-cart"));
            helper.handleAdPopup();

            helper.waitForVisible(By.cssSelector(".modal-body a[href='/view_cart']"));
            helper.click(By.cssSelector(".modal-body a[href='/view_cart']"));

            CartPage cartPage = new CartPage(driver);
            Assert.assertTrue(cartPage.getCartItemCount() > 0, "Cart should have the recommended product");
        }
    }

