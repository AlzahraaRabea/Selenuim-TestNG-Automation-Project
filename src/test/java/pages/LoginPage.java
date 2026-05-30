package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * LoginPage — covers both Login and Register sections on the same URL.
 * Used by TC1 (Register), TC2 (Login correct), TC3 (Login incorrect),
 * TC4 (Logout), TC5 (Register existing email)
 */
public class LoginPage extends BasePage {

    // ─── Login section locators ───────────────────────────────────────────────
    private final By loginEmailField     = By.cssSelector("form[action='/login'] input[data-qa='login-email']");
    private final By loginPasswordField  = By.cssSelector("form[action='/login'] input[data-qa='login-password']");
    private final By loginButton         = By.cssSelector("button[data-qa='login-button']");
    private final By loginErrorMsg       = By.cssSelector("form[action='/login'] p");

    // ─── Signup section locators ──────────────────────────────────────────────
    private final By signupNameField     = By.cssSelector("input[data-qa='signup-name']");
    private final By signupEmailField    = By.cssSelector("input[data-qa='signup-email']");
    private final By signupButton        = By.cssSelector("button[data-qa='signup-button']");
    private final By signupErrorMsg      = By.cssSelector("form[action='/signup'] p");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public void navigateTo() {
        helper.navigateTo(baseUrl + "/login");
    }

    //  Login actions

    public void enterLoginEmail(String email) {
        helper.type(loginEmailField, email);
    }

    public void enterLoginPassword(String password) {
        helper.type(loginPasswordField, password);
    }

    public void clickLoginButton() {
        helper.click(loginButton);
    }

    public void login(String email, String password) {
        enterLoginEmail(email);
        enterLoginPassword(password);
        clickLoginButton();
    }

    public String getLoginErrorMessage() {
        return helper.getText(loginErrorMsg);
    }

    // Signup actions

    public void enterSignupName(String name) {
        helper.type(signupNameField, name);
    }

    public void enterSignupEmail(String email) {
        helper.type(signupEmailField, email);
    }

    public void clickSignupButton() {
        helper.click(signupButton);
    }

    public void initiateSignup(String name, String email) {
        enterSignupName(name);
        enterSignupEmail(email);
        clickSignupButton();
    }

    public String getSignupErrorMessage() {
        return helper.getText(signupErrorMsg);
    }

    public boolean isLoginFormVisible() {
        return helper.isDisplayed(loginButton);
    }

    public boolean isSignupFormVisible() {
        return helper.isDisplayed(signupButton);
    }
}
