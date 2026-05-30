package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * ContactUsPage — contact form submission.
 * TC6
 */
public class ContactUsPage extends BasePage {

    private final By nameField          = By.cssSelector("input[data-qa='name']");
    private final By emailField         = By.cssSelector("input[data-qa='email']");
    private final By subjectField       = By.cssSelector("input[data-qa='subject']");
    private final By messageField       = By.cssSelector("textarea[data-qa='message']");
    private final By fileUploadField    = By.cssSelector("input[name='upload_file']");
    private final By submitBtn          = By.cssSelector("input[data-qa='submit-button']");
    private final By successMsg         = By.cssSelector(".status.alert.alert-success");
    private final By homeBtn            = By.cssSelector("a.btn.btn-success");
    private final By contactUsHeader    = By.cssSelector("h2.title.text-center");

    public ContactUsPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public void navigateTo() {
        helper.navigateTo(baseUrl + "/contact_us");
    }

    public boolean isContactUsHeaderVisible() {
        return helper.isDisplayed(contactUsHeader);
    }

    public void enterName(String name) {
        helper.type(nameField, name);
    }

    public void enterEmail(String email) {helper.type(emailField, email);
    }

    public void enterSubject(String subject) {
        helper.type(subjectField, subject);
    }

    public void enterMessage(String message) {
        helper.type(messageField, message);
    }

    public void uploadFile(String absoluteFilePath) {
        driver.findElement(fileUploadField).sendKeys(absoluteFilePath);
    }

    public void clickSubmit() {
        helper.click(submitBtn);
    }

    public boolean isSuccessMessageVisible() {return helper.isDisplayed(successMsg);
    }

    public String getSuccessMessageText() {return helper.getText(successMsg);
    }

    public void clickHomeButton() {
        helper.click(homeBtn);
    }

    /**
     * Fill and submit the full contact form, accepting the browser alert.
     */
    public void submitContactForm(String name, String email, String subject,
                                  String message, String filePath) {
        enterName(name);
        enterEmail(email);
        enterSubject(subject);
        enterMessage(message);
        if (filePath != null && !filePath.isEmpty()) {
            uploadFile(filePath);
        }
        clickSubmit();
        helper.acceptAlert();
    }
}
