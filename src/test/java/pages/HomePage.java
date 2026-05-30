package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * HomePage — AutomationExercise home page interactions.
 * TC1, TC2, TC3 (navigation, sliders, subscription)
 */
public class HomePage extends BasePage {

    // ─── Locators ─────────────────────────────────────────────────────────────
    private final By logo               = By.cssSelector("img[alt='Website for automation practice']");
    private final By signupLoginLink    = By.cssSelector("a[href='/login']");
    private final By logoutLink         = By.cssSelector("a[href='/logout']");
    private final By deleteAccountLink  = By.cssSelector("a[href='/delete_account']");
    private final By contactUsLink      = By.cssSelector("a[href='/contact_us']");
    private final By cartLink           = By.cssSelector("a[href='/view_cart']");
    private final By testCasesLink      = By.cssSelector("a[href='/test_cases']");
    private final By loggedInAs         = By.xpath("//a[contains(text(),'Logged in as')]");
    private final By subscriptionEmail  = By.id("susbscribe_email");
    private final By subscribeBtn       = By.id("subscribe");
    private final By subscribeSuccess   = By.cssSelector("#success-subscribe");
    private final By scrollUpBtn        = By.id("scrollUp");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    @Override
    public void navigateTo() {
        helper.navigateTo(baseUrl);
    }

    // Verifications

    public boolean isLogoVisible() {
        return helper.isDisplayed(logo);
    }

    public boolean isLoggedIn() {
        return helper.isDisplayed(loggedInAs);
    }

    public String getLoggedInUsername() {
        return helper.getText(loggedInAs).replace("Logged in as ", "");
    }

    public boolean isSubscribeSuccessVisible() {
        return helper.isDisplayed(subscribeSuccess);
    }

    //  Navigation

    public void clickSignupLogin() {
        helper.click(signupLoginLink);
    }

    public void clickLogout() {
        helper.click(logoutLink);
    }

    public void clickDeleteAccount() {
        helper.click(deleteAccountLink);
    }

    public void clickContactUs() {
        helper.click(contactUsLink);
    }


    public void clickProducts() {
        driver.get(baseUrl + "/products");
        helper.waitForUrlContains("products");
    }

    public void clickCart() {
        helper.click(cartLink);
    }

    public void clickTestCases() {
        helper.click(testCasesLink);
    }

    // Subscription (TC10, TC11)

    public void scrollToSubscription() {
        helper.scrollToBottom();
    }

    public void enterSubscriptionEmail(String email) {
        helper.type(subscriptionEmail, email);
    }

    public void clickSubscribeButton() {
        helper.click(subscribeBtn);
    }

    public void subscribeWithEmail(String email) {
        scrollToSubscription();
        enterSubscriptionEmail(email);
        clickSubscribeButton();
    }

    // Scroll Arrow (TC25)

    public void scrollToTop() {
        helper.scrollToTop();
    }

    public void clickScrollUpArrow() {
        helper.click(scrollUpBtn);
    }

    public boolean isScrollUpButtonVisible() {
        return helper.isDisplayed(scrollUpBtn);
    }
}
