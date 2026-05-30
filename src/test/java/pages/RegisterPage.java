package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * RegisterPage — the full account-creation form reached after initial signup.
 * Used in TC1 (Register User)
 */
public class RegisterPage extends BasePage {

    // Account info
    private final By titleMr            = By.id("id_gender1");
    private final By titleMrs           = By.id("id_gender2");
    private final By nameField          = By.id("name");
    private final By passwordField      = By.id("password");
    private final By dobDay             = By.id("days");
    private final By dobMonth           = By.id("months");
    private final By dobYear            = By.id("years");
    private final By newsletterCheck    = By.id("newsletter");
    private final By offersCheck        = By.id("optin");

    // Address
    private final By firstNameField     = By.id("first_name");
    private final By lastNameField      = By.id("last_name");
    private final By companyField       = By.id("company");
    private final By address1Field      = By.id("address1");
    private final By address2Field      = By.id("address2");
    private final By countryDropdown    = By.id("country");
    private final By stateField         = By.id("state");
    private final By cityField          = By.id("city");
    private final By zipcodeField       = By.id("zipcode");
    private final By mobileField        = By.id("mobile_number");

    private final By createAccountBtn   = By.cssSelector("button[data-qa='create-account']");
    private final By accountCreatedMsg  = By.cssSelector("h2[data-qa='account-created']");
    private final By continueBtn        = By.cssSelector("a[data-qa='continue-button']");

    public RegisterPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public void navigateTo() {
        // Not navigated directly — reached via LoginPage signup flow
    }

    public void selectTitle(String title) {
        if (title.equalsIgnoreCase("Mr")) helper.click(titleMr);
        else helper.click(titleMrs);
    }

    public void enterPassword(String password) {
        helper.type(passwordField, password);
    }

    public void selectDateOfBirth(String day, String month, String year) {
        helper.selectByValue(dobDay, day);
        helper.selectByValue(dobMonth, month);
        helper.selectByValue(dobYear, year);
    }

    public void checkNewsletter() {
        helper.click(newsletterCheck);
    }

    public void checkOffers() {
        helper.click(offersCheck);
    }

    public void enterFirstName(String firstName) {
        helper.type(firstNameField, firstName);
    }

    public void enterLastName(String lastName) {
        helper.type(lastNameField, lastName);
    }

    public void enterCompany(String company) {
        helper.type(companyField, company);
    }

    public void enterAddress1(String address) {
        helper.type(address1Field, address);
    }

    public void enterAddress2(String address) {
        helper.type(address2Field, address);
    }

    public void selectCountry(String country) {
        helper.selectByVisibleText(countryDropdown, country);
    }

    public void enterState(String state) {
        helper.type(stateField, state);
    }

    public void enterCity(String city) {
        helper.type(cityField, city);
    }

    public void enterZipcode(String zipcode) {
        helper.type(zipcodeField, zipcode);
    }

    public void enterMobileNumber(String mobile) {
        helper.type(mobileField, mobile);
    }

    public void clickCreateAccount() {
        helper.click(createAccountBtn);
    }

    public boolean isAccountCreatedMessageVisible() {
        return helper.isDisplayed(accountCreatedMsg);
    }

    public String getAccountCreatedMessage() {
        return helper.getText(accountCreatedMsg);
    }

    public void clickContinue() {
        helper.click(continueBtn);
    }

    /**
     * Fill the complete registration form with all required fields.
     */
    public void fillAccountDetails(String password, String firstName, String lastName,
                                   String company, String address1, String address2,
                                   String country, String state, String city,
                                   String zipcode, String mobile) {
        selectTitle("Mr");
        enterPassword(password);
        selectDateOfBirth("1", "1", "1990");
        checkNewsletter();
        checkOffers();
        enterFirstName(firstName);
        enterLastName(lastName);
        enterCompany(company);
        enterAddress1(address1);
        enterAddress2(address2);
        selectCountry(country);
        enterState(state);
        enterCity(city);
        enterZipcode(zipcode);
        enterMobileNumber(mobile);
    }
}
