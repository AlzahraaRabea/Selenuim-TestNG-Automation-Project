package tests;

import com.fasterxml.jackson.databind.JsonNode;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;
import utils.JsonReader;

import java.util.Objects;

import static utils.DriverFactory.quitDriver;

/**
 * TC2  — Login with correct credentials
 * TC3  — Login with incorrect credentials
 * TC4  — Logout from account
 */

public class LoginLogoutTests extends BaseTest {

    // TC2: Login with correct credentials
    @Test(description = "TC2 - Login with correct credentials")
    public void TC2_loginWithCorrectCredentials() {
        JsonNode user = JsonReader.getUser("loginUser");

        HomePage homePage = new HomePage(driver);
        homePage.clickSignupLogin();

        LoginPage loginPage = new LoginPage(driver);
        Assert.assertTrue(loginPage.isLoginFormVisible(), "Login form should be visible");

        loginPage.login(
            user.get("email").asText(),
            user.get("password").asText()
        );

        Assert.assertTrue(homePage.isLoggedIn(), "User should be logged in");
        Assert.assertEquals(homePage.getLoggedInUsername(), user.get("name").asText(), "Logged-in username should match");
        quitDriver();
    }

    // TC3: Login with incorrect credentials
    @Test

    public void TC3_loginWithIncorrectCredentials() {
        JsonNode user = JsonReader.getUser("invalidUser");

        HomePage homePage = new HomePage(driver);
        homePage.clickSignupLogin();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(
            user.get("email").asText(),
            user.get("password").asText()
        );

        String errorMsg = loginPage.getLoginErrorMessage();
        Assert.assertEquals(errorMsg, "Your email or password is incorrect!",
            "Error message should appear for wrong credentials");
    }

    // TC4: Logout
    @Test(description = "TC4 - Logout from account")
    public void TC4_logoutFromAccount() {
        JsonNode user = JsonReader.getUser("loginUser");

        HomePage homePage = new HomePage(driver);
        homePage.clickSignupLogin();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(
            user.get("email").asText(),
            user.get("password").asText()
        );
        Assert.assertTrue(homePage.isLoggedIn(), "User should be logged in before logout");
        homePage.clickLogout();
        Assert.assertTrue(loginPage.isLoginFormVisible(), "Login form should be visible after logout");
        Assert.assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).contains("/login"), "URL should redirect to /login after logout");
    }
}
