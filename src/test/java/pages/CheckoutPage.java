package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * CheckoutPage — address review, order comment, and payment entry point.
 * TC14, TC15, TC16, TC23, TC24
 */
public class CheckoutPage extends BasePage {

    private final By deliveryAddress    = By.id("address_delivery");
    private final By orderCommentBox    = By.cssSelector("textarea[name='message']");
    private final By placeOrderBtn      = By.cssSelector("a.btn.btn-default.check_out");


    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public void navigateTo() {
        helper.navigateTo(baseUrl + "/checkout");
    }

    public boolean isDeliveryAddressVisible() {
        return helper.isDisplayed(deliveryAddress);
    }


    public String getDeliveryAddressText() {
        return helper.getText(deliveryAddress);
    }

    public boolean isCheckoutPageLoaded() {
        return helper.waitForUrlContains("checkout");
    }

    public void enterOrderComment(String comment) {
        helper.type(orderCommentBox, comment);
    }

    public void clickPlaceOrder() {
        helper.click(placeOrderBtn);
    }

}