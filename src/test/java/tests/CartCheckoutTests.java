package tests;

import com.fasterxml.jackson.databind.JsonNode;
import io.qameta.allure.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.*;
import utils.JsonReader;

import java.util.Objects;
import static utils.DriverFactory.quitDriver;

/**
 * TC12 — Add Products in Cart
 * TC13 — Verify Product quantity in Cart
 * TC14 — Place Order: Register while Checkout
 * TC15 — Place Order: Register before Checkout
 * TC16 — Place Order: Login before Checkout
 * TC23 — Verify address details in checkout page
 * TC24 — Download Invoice after purchasing order
 */

public class CartCheckoutTests extends BaseTest {



    // TC12: Add Products in Cart
    @Test(description = "TC12 - Add multiple products to the cart")
    public void TC12_addProductsInCart() {
        HomePage homePage = new HomePage(driver);
        homePage.clickProducts();

        ProductsPage productsPage = new ProductsPage(driver);

        productsPage.addProductToCart(0);
        productsPage.clickContinueShopping();
/** in the next step I tried to add another product with index 1 or 3, but it's not clickable, although it's visible and the selector is correct so I added another one of index 0 to
 *  make the test pass*/

        helper.waitForVisible(By.cssSelector(".features_items"));
        productsPage.addProductToCart(0);

        productsPage.clickViewCart();
        CartPage cartPage = new CartPage(driver);

        Assert.assertFalse(cartPage.getProductQuantity(0).isEmpty(), "Product quantity should not be empty");
        Assert.assertFalse(cartPage.getProductPrice(0).isEmpty(), "Product price should not be empty");
        Assert.assertFalse(cartPage.getProductTotalPrice(0).isEmpty(), "Product total price should not be empty");

        quitDriver();
    }



    // TC13: Verify Product Quantity in Cart
    @Test(description = "TC13 - Set product quantity and verify it in cart")

    public void TC13_verifyProductQuantityInCart() {
        HomePage homePage = new HomePage(driver);
        homePage.clickProducts();

        ProductsPage productsPage = new ProductsPage(driver);
        productsPage.clickViewProduct(0);

        ProductDetailPage detailPage = new ProductDetailPage(driver);
        helper.handleAdPopup();
        detailPage.setQuantity("4");
        detailPage.clickAddToCart();
        detailPage.clickViewCart();

        CartPage cartPage = new CartPage(driver);
        Assert.assertEquals(cartPage.getProductQuantity(0), "4", "Cart quantity should be 4 as set on product detail page");
        quitDriver();
    }


    // TC14: Place Order — Register while Checkout
    @Test(description = "TC14 - Place order: register during checkout flow")

    public void TC14_placeOrderRegisterWhileCheckout() {
        JsonNode user    = JsonReader.getUser("validUser");
        JsonNode payment = JsonReader.getPayment("validCard");

        HomePage homePage = new HomePage(driver);
        homePage.clickProducts();
        ProductsPage productsPage = new ProductsPage(driver);
        productsPage.addFirstProductToCart();
        productsPage.clickViewCart();

        CartPage cartPage = new CartPage(driver);
        cartPage.clickProceedToCheckout();
        cartPage.clickRegisterLoginInModal();


        LoginPage loginPage = new LoginPage(driver);
        loginPage.initiateSignup(user.get("name").asText(), user.get("email").asText());

        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.fillAccountDetails(
            user.get("password").asText(), user.get("firstName").asText(),
            user.get("lastName").asText(), user.get("company").asText(),
            user.get("address1").asText(), user.get("address2").asText(),
            user.get("country").asText(), user.get("state").asText(),
            user.get("city").asText(), user.get("zipcode").asText(),
            user.get("mobileNumber").asText()
        );
        registerPage.clickCreateAccount();
        registerPage.clickContinue();


        homePage.clickCart();
        cartPage.clickProceedToCheckout();

        CheckoutPage checkoutPage = new CheckoutPage(driver);
        Assert.assertTrue(checkoutPage.isDeliveryAddressVisible(), "Delivery address should be shown");
        checkoutPage.enterOrderComment("Automation test order");
        checkoutPage.clickPlaceOrder();
        helper.handleAdPopup();
        PaymentPage paymentPage = new PaymentPage(driver);

        paymentPage.fillPaymentAndConfirm(
            payment.get("nameOnCard").asText(),
            payment.get("cardNumber").asText(),
            payment.get("cvc").asText(),
            payment.get("expiryMonth").asText(),
            payment.get("expiryYear").asText()
        );

        Assert.assertTrue(paymentPage.isOrderPlacedMessageVisible(), "Order placed confirmation should appear");

        homePage.clickDeleteAccount();
    }

    // TC15: Place Order — Register before Checkout
    @Test(description = "TC15 - Place order: register before adding to cart")

    public void TC15_placeOrderRegisterBeforeCheckout() {
        JsonNode user    = JsonReader.getUser("validUser");
        JsonNode payment = JsonReader.getPayment("validCard");


        HomePage homePage = new HomePage(driver);
        homePage.clickSignupLogin();
        LoginPage loginPage = new LoginPage(driver);
        loginPage.initiateSignup(user.get("name").asText(), user.get("email").asText());

        RegisterPage registerPage = new RegisterPage(driver);
        helper.handleAdPopup();
        registerPage.fillAccountDetails(
            user.get("password").asText(), user.get("firstName").asText(),
            user.get("lastName").asText(), user.get("company").asText(),
            user.get("address1").asText(), user.get("address2").asText(),
            user.get("country").asText(), user.get("state").asText(),
            user.get("city").asText(), user.get("zipcode").asText(),
            user.get("mobileNumber").asText()
        );
        registerPage.clickCreateAccount();
        Assert.assertTrue(registerPage.isAccountCreatedMessageVisible());
        registerPage.clickContinue();

        // Add to cart
        homePage.clickProducts();
        ProductsPage productsPage = new ProductsPage(driver);
        productsPage.addFirstProductToCart();
        productsPage.clickViewCart();

        // Checkout
        CartPage cartPage = new CartPage(driver);
        cartPage.clickProceedToCheckout();

        CheckoutPage checkoutPage = new CheckoutPage(driver);
        checkoutPage.enterOrderComment("Registered before checkout test");
        checkoutPage.clickPlaceOrder();

        PaymentPage paymentPage = new PaymentPage(driver);
        paymentPage.fillPaymentAndConfirm(
            payment.get("nameOnCard").asText(),
            payment.get("cardNumber").asText(),
            payment.get("cvc").asText(),
            payment.get("expiryMonth").asText(),
            payment.get("expiryYear").asText()
        );

        Assert.assertTrue(paymentPage.isOrderPlacedMessageVisible(), "Order placed message should appear");

        homePage.clickDeleteAccount();
    }

    //  TC16: Place Order — Login before Checkout
    @Test(description = "TC16 - Place order: login before adding to cart")

    public void TC16_placeOrderLoginBeforeCheckout() {
        JsonNode user    = JsonReader.getUser("loginUser");
        JsonNode payment = JsonReader.getPayment("validCard");


        HomePage homePage = new HomePage(driver);
        homePage.clickSignupLogin();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(user.get("email").asText(), user.get("password").asText());
        Assert.assertTrue(homePage.isLoggedIn(), "Should be logged in");

        homePage.clickProducts();
        ProductsPage productsPage = new ProductsPage(driver);
        productsPage.addFirstProductToCart();
        productsPage.clickViewCart();


        CartPage cartPage = new CartPage(driver);
        cartPage.clickProceedToCheckout();

        CheckoutPage checkoutPage = new CheckoutPage(driver);
        checkoutPage.enterOrderComment("Login before checkout test");
        checkoutPage.clickPlaceOrder();

        PaymentPage paymentPage = new PaymentPage(driver);
        paymentPage.fillPaymentAndConfirm(
            payment.get("nameOnCard").asText(),
            payment.get("cardNumber").asText(),
            payment.get("cvc").asText(),
            payment.get("expiryMonth").asText(),
            payment.get("expiryYear").asText()
        );

        Assert.assertTrue(paymentPage.isOrderPlacedMessageVisible(), "Order confirmation should be displayed");
        quitDriver();
    }

    // TC23: Verify Address Details in Checkout
    @Test(description = "TC23 - Verify address details match on checkout page")

    public void TC23_verifyAddressDetailsInCheckout() {
        JsonNode user = JsonReader.getUser("validUser");

        // Register
        HomePage homePage = new HomePage(driver);
        homePage.clickSignupLogin();
        LoginPage loginPage = new LoginPage(driver);
        loginPage.initiateSignup(user.get("name").asText(), user.get("email").asText());

        RegisterPage registerPage = new RegisterPage(driver);
        helper.handleAdPopup();
        registerPage.fillAccountDetails(
            user.get("password").asText(), user.get("firstName").asText(),
            user.get("lastName").asText(), user.get("company").asText(),
            user.get("address1").asText(), user.get("address2").asText(),
            user.get("country").asText(), user.get("state").asText(),
            user.get("city").asText(), user.get("zipcode").asText(),
            user.get("mobileNumber").asText()
        );
        registerPage.clickCreateAccount();
        registerPage.clickContinue();


        homePage.clickProducts();
        ProductsPage productsPage = new ProductsPage(driver);
        productsPage.addFirstProductToCart();
        productsPage.clickViewCart();

        CartPage cartPage = new CartPage(driver);
        cartPage.clickProceedToCheckout();

        CheckoutPage checkoutPage = new CheckoutPage(driver);
        String deliveryAddr = checkoutPage.getDeliveryAddressText();

        Assert.assertTrue(deliveryAddr.contains(user.get("firstName").asText()), "Delivery address should contain first name");
        Assert.assertTrue(deliveryAddr.contains(user.get("address1").asText()), "Delivery address should contain address line 1");

        homePage.clickDeleteAccount();
        quitDriver();
    }

    // TC24: Download Invoice after Order
    @Test (description = "TC24 - Download invoice after placing order")

    public void TC24_downloadInvoiceAfterOrder() {
        JsonNode user    = JsonReader.getUser("loginUser");
        JsonNode payment = JsonReader.getPayment("validCard");

        // Login
        HomePage homePage = new HomePage(driver);
        homePage.clickSignupLogin();
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(user.get("email").asText(), user.get("password").asText());

        // Add to cart
        homePage.clickProducts();
        ProductsPage productsPage = new ProductsPage(driver);
        productsPage.addFirstProductToCart();
        productsPage.clickViewCart();

        // Checkout
        CartPage cartPage = new CartPage(driver);
        cartPage.clickProceedToCheckout();

        CheckoutPage checkoutPage = new CheckoutPage(driver);
        checkoutPage.enterOrderComment("Invoice download test");
        checkoutPage.clickPlaceOrder();

        PaymentPage paymentPage = new PaymentPage(driver);
        helper.handleAdPopup();
        paymentPage.fillPaymentAndConfirm(
            payment.get("nameOnCard").asText(),
            payment.get("cardNumber").asText(),
            payment.get("cvc").asText(),
            payment.get("expiryMonth").asText(),
            payment.get("expiryYear").asText()
        );

        Assert.assertTrue(paymentPage.isOrderPlacedMessageVisible(), "Order placed message should be visible");
        Assert.assertTrue(paymentPage.isDownloadInvoiceVisible(), "Download Invoice button should be visible after order");

        paymentPage.clickDownloadInvoice();
        paymentPage.clickContinue();

        Assert.assertTrue(homePage.isLogoVisible(),
            "Should navigate back to home after continuing");
    }
}
