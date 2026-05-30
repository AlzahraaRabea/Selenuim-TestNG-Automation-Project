package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * TC15, TC16, TC24
 */
public class PaymentPage extends BasePage {

    private final By nameOnCard         = By.cssSelector("input[data-qa='name-on-card']");
    private final By cardNumber         = By.cssSelector("input[data-qa='card-number']");
    private final By cvcField           = By.cssSelector("input[data-qa='cvc']");
    private final By expiryMonth        = By.cssSelector("input[data-qa='expiry-month']");
    private final By expiryYear         = By.cssSelector("input[data-qa='expiry-year']");
    private final By payAndConfirmBtn   = By.cssSelector("#submit");
    private final By orderPlacedMsg     = By.cssSelector("h2[data-qa='order-placed']");
    private final By downloadInvoiceBtn = By.cssSelector("a.btn.btn-default.check_out");
    private final By continueBtn        = By.cssSelector("a[data-qa='continue-button']");

    public PaymentPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public void navigateTo() {
        helper.navigateTo(baseUrl + "/payment");
    }

    public void enterNameOnCard(String name) {
        helper.type(nameOnCard, name);
    }

    public void enterCardNumber(String number) {
        helper.type(cardNumber, number);
    }

    public void enterCVC(String cvc) {
        helper.type(cvcField, cvc);
    }

    public void enterExpiryMonth(String month) {
        helper.type(expiryMonth, month);
    }

    public void enterExpiryYear(String year) {
        helper.type(expiryYear, year);
    }

    public void clickPayAndConfirm() {
        helper.click(payAndConfirmBtn);
    }

    public boolean isOrderPlacedMessageVisible() {
        return helper.isDisplayed(orderPlacedMsg);
    }

    public String getOrderPlacedMessage() {
        return helper.getText(orderPlacedMsg);
    }

    public boolean isDownloadInvoiceVisible() {
        return helper.isDisplayed(downloadInvoiceBtn);
    }

    public void clickDownloadInvoice() {
        helper.click(downloadInvoiceBtn);
    }

    public void clickContinue() {
        helper.click(continueBtn);
    }

    /**
     * Fill full payment form and submit.
     */
    public void fillPaymentAndConfirm(String name, String number, String cvc,
                                      String month, String year) {
        enterNameOnCard(name);
        enterCardNumber(number);
        enterCVC(cvc);
        enterExpiryMonth(month);
        enterExpiryYear(year);
        clickPayAndConfirm();
    }
}
