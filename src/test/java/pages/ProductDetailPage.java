package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * ProductDetailPage — individual product view with quantity, reviews.
 * TC13, TC21, TC22
 */
public class ProductDetailPage extends BasePage {

    private final By productName        = By.cssSelector(".product-information h2");
    private final By productCategory    = By.cssSelector(".product-information p:nth-child(3)");
    private final By productPrice       = By.cssSelector(".product-information span span");
    private final By quantityField      = By.id("quantity");
    private final By addToCartBtn       = By.cssSelector("button.cart");
    private final By reviewNameField    = By.id("name");
    private final By reviewEmailField   = By.id("email");
    private final By reviewTextArea     = By.id("review");
    private final By reviewSubmitBtn    = By.id("button-review");
    private final By reviewSuccessMsg   = By.cssSelector(".alert-success.alert");
    private final By viewCartModal      = By.cssSelector(".modal-body a[href='/view_cart']");
    private final By continueShoppingBtn= By.cssSelector("button.close-modal");

    public ProductDetailPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public void navigateTo() {
        helper.navigateTo(baseUrl + "/product_details/1");
    }

    public boolean isProductDetailPageLoaded() {
        return helper.waitForUrlContains("product_details");
    }

    public boolean isProductInfoVisible() {
        return helper.isDisplayed(productName)
            && helper.isDisplayed(productCategory)
            && helper.isDisplayed(productPrice);
    }

    public void setQuantity(String quantity) {
        helper.type(quantityField, quantity);
    }

    public void clickAddToCart() {
        helper.click(addToCartBtn);
    }

    public void clickViewCart() {
        helper.click(viewCartModal);
    }

    public void clickContinueShopping() {
        helper.click(continueShoppingBtn);
    }

    //  Review Section

    public void enterReviewName(String name) {
        helper.type(reviewNameField, name);
    }

    public void enterReviewEmail(String email) {
        helper.type(reviewEmailField, email);
    }

    public void enterReviewText(String review) {
        helper.type(reviewTextArea, review);
    }

    public void clickSubmitReview() {
        helper.click(reviewSubmitBtn);
    }

    public boolean isReviewSuccessVisible() {
        return helper.isDisplayed(reviewSuccessMsg);
    }

    public void submitReview(String name, String email, String review) {
        helper.scrollToBottom();
        enterReviewName(name);
        enterReviewEmail(email);
        enterReviewText(review);
        clickSubmitReview();
    }
}
