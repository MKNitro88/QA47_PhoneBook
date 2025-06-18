package pages;

import dto.UserLombok;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.openqa.selenium.support.ui.ExpectedConditions.alertIsPresent;

public class LoginPage extends BasePage{
    public LoginPage(WebDriver driver) {
        setDriver(driver);
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 10), this);
    }
    @FindBy(xpath = "//div[@class='login_login__3EHKB']")
    WebElement formLogin;
    @FindBy(xpath = "//input[@name='email']")
    WebElement inputEmail;
    @FindBy(xpath = "//input[@name='password']")
    WebElement inputPassword;
    @FindBy(xpath = "//button[@name='login']")
    WebElement btnLogin;
    @FindBy(xpath = "//button[@name='registration']")
    WebElement btnRegistration;
    @FindBy(xpath = "//div[@class = 'contact-page_message__2qafk']")
    WebElement messageNoContacts;
    @FindBy(xpath = "//button[text()='Sign Out']")
    WebElement btnSignOutHeader;

    public void logOut(){
        btnSignOutHeader.click();
    }

    public void fillLoginForm(UserLombok user) {
        logger.info("type logging form with data "+ user);
        inputEmail.sendKeys(user.getEmail());
        inputPassword.sendKeys(user.getPassword());
    }
    public void clickBtnLogin() {

        btnLogin.click();
    }
    public void clickBtnRegistration() {
        btnRegistration.click();
    }
    public void closeAlert(){
        Alert alert = new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.alertIsPresent());
        System.out.println(alert.getText());
        alert.accept();
    }
    public String closeAlertReturnText(){
        Alert alert = new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.alertIsPresent());
        String text = alert.getText();
        alert.accept();
        return text;
    }

    public boolean isErrorMessageDisplayed(String text) {

        return isTextElementPreseant(formLogin, text);
    }
    public boolean isContactMessageDisplayed(String text) {

        return isTextElementPreseant(messageNoContacts, text);
    }
}
