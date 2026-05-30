package tests;

import com.fasterxml.jackson.databind.JsonNode;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.*;
import utils.JsonReader;

import java.util.Objects;

/**
 * TC1  — Register User
 * TC5  — Register User with existing email
 */

public class UserRegistrationTests extends BaseTest {

    // TC1: Register User
    @Test
    public void TC1_registerUser() {
        JsonNode user = JsonReader.getUser("validUser");

        HomePage homePage = new HomePage(driver);
//        Assert.assertTrue(homePage.isLogoVisible(), "Home page should be visible");

        homePage.clickSignupLogin();


        LoginPage loginPage = new LoginPage(driver);
        Assert.assertTrue(loginPage.isSignupFormVisible(), "Signup form should be visible");
        loginPage.initiateSignup(
            user.get("name").asText(),
            user.get("email").asText()
        );

        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.fillAccountDetails(
            user.get("password").asText(),
            user.get("firstName").asText(),
            user.get("lastName").asText(),
            user.get("company").asText(),
            user.get("address1").asText(),
            user.get("address2").asText(),
            user.get("country").asText(),
            user.get("state").asText(),
            user.get("city").asText(),
            user.get("zipcode").asText(),
            user.get("mobileNumber").asText()
        );
        registerPage.clickCreateAccount();

        Assert.assertTrue(registerPage.isAccountCreatedMessageVisible(),
            "Account Created message should be displayed");
        Assert.assertEquals(registerPage.getAccountCreatedMessage(), "ACCOUNT CREATED!");

        registerPage.clickContinue();
        Assert.assertTrue(homePage.isLoggedIn(), "User should be logged in after registration");
        Assert.assertEquals(homePage.getLoggedInUsername(), user.get("name").asText(),
            "Logged-in username should match registered name");
helper.handleAdPopup();

        homePage.clickDeleteAccount();
        Assert.assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).contains("delete_account"), "Should navigate to account deletion confirmation");
    }

    // TC5: Register with existing email
    @Test
    public void TC5_registerWithExistingEmail() {
        JsonNode user = JsonReader.getUser("existingUser");

        HomePage homePage = new HomePage(driver);
        homePage.clickSignupLogin();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.initiateSignup(
            user.get("name").asText(),
            user.get("email").asText()
        );

        String errorMsg = loginPage.getSignupErrorMessage();
        Assert.assertEquals(errorMsg, "Email Address already exist!",
            "Error message for duplicate email should be shown");
    }
}
