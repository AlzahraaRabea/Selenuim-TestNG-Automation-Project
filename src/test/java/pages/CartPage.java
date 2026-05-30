package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * TC12, TC13, TC14, TC16, TC17, TC20, TC22
 */
public class CartPage extends BasePage {

    private final By cartItems          = By.cssSelector("#cart_info_table tbody tr");
    private final By cartQuantities     = By.cssSelector(".cart_quantity button");
    private final By cartPrices         = By.cssSelector(".cart_price p");
    private final By cartTotalPrices    = By.cssSelector(".cart_total p");
    private final By removeButtons      = By.cssSelector(".cart_quantity_delete");
    private final By proceedCheckoutBtn = By.cssSelector(".btn.btn-default.check_out");
    private final By emptyCartMsg       = By.cssSelector("#empty_cart");
    private final By registerLoginLink  = By.cssSelector(".modal-body a[href='/login']");


    public CartPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public void navigateTo() {
        helper.navigateTo(baseUrl + "/view_cart");
    }

    public int getCartItemCount() {
        return driver.findElements(cartItems).size();
    }


    public void removeFirstProduct() {
        List<WebElement> removeBtns = driver.findElements(removeButtons);
        if (!removeBtns.isEmpty()) removeBtns.get(0).click();
    }


    public boolean isCartEmpty() {
        return helper.isDisplayed(emptyCartMsg);
    }

    public void clickProceedToCheckout() {
        helper.click(proceedCheckoutBtn);
    }

    public void clickRegisterLoginInModal() {
        helper.click(registerLoginLink);
    }

    public String getProductQuantity(int index) {
        List<WebElement> quantities = driver.findElements(cartQuantities);
        if (index < quantities.size()) return quantities.get(index).getText().trim();
        return "";
    }

    public String getProductPrice(int index) {
        List<WebElement> prices = driver.findElements(cartPrices);
        if (index < prices.size()) return prices.get(index).getText().trim();
        return "";
    }

    public String getProductTotalPrice(int index) {
        List<WebElement> totals = driver.findElements(cartTotalPrices);
        if (index < totals.size()) return totals.get(index).getText().trim();
        return "";
    }
}
