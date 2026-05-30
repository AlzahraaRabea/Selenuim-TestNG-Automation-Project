package tests;

import com.fasterxml.jackson.databind.JsonNode;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.ContactUsPage;
import pages.HomePage;
import utils.JsonReader;

import java.io.File;
import java.nio.file.Paths;

/**
 * Contact Us Test Cases
 * TC6 — Contact Us Form
 */

public class ContactUsTest extends BaseTest {

    @Test

    public void TC6_contactUsForm() {
        JsonNode contact = JsonReader.getContact("validContact");


        HomePage homePage = new HomePage(driver);
        homePage.clickContactUs();

        ContactUsPage contactUsPage = new ContactUsPage(driver);
        Assert.assertTrue(contactUsPage.isContactUsHeaderVisible(), "Contact Us header should be visible");


        String uploadFilePath = Paths.get(new File("").getAbsolutePath(), contact.get("uploadFile").asText()).toString();

        contactUsPage.submitContactForm(
            contact.get("name").asText(), contact.get("email").asText(), contact.get("subject").asText(), contact.get("message").asText(), uploadFilePath);


        Assert.assertTrue(contactUsPage.isSuccessMessageVisible(), "Success message should be displayed after form submission");
        Assert.assertTrue(contactUsPage.getSuccessMessageText().contains("Success! Your details have been submitted successfully."), "Success message text should match");


        contactUsPage.clickHomeButton();
        Assert.assertTrue(homePage.isLogoVisible(), "Should be redirected back to home page");
    }
}
