package pages;

import dto.ContactLombok;
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
import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.alertIsPresent;

public class AddPage extends BasePage{
    public AddPage(WebDriver driver) {
        setDriver(driver);
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 10), this);
    }
    @FindBy(xpath = "//input[@placeholder='Name']")
    WebElement inputName;
    @FindBy(xpath = "//input[@placeholder='Last Name']")
    WebElement inputLastName;
    @FindBy(xpath = "//input[@placeholder='Phone']")
    WebElement inputPhone;
    @FindBy(xpath = "//input[@placeholder='email']")
    WebElement inputEmail;
    @FindBy(xpath = "//input[@placeholder='Address']")
    WebElement inputAddress;
    @FindBy(xpath = "//input[@placeholder='description']")
    WebElement inputDescription;
    @FindBy(xpath = "//b/..")
    WebElement btnAddContact;
    @FindBy(xpath = "//div[@class = 'contact-item_card__2SOIM']/h2")
    List<WebElement> contactsList;

    public void fillAddContactForm(ContactLombok contact) {
        inputName.sendKeys(contact.getName());
        inputLastName.sendKeys(contact.getLastName());
        inputPhone.sendKeys(contact.getPhone());
        inputEmail.sendKeys(contact.getEmail());
        inputAddress.sendKeys(contact.getAddress());
        inputDescription.sendKeys(contact.getDescription());
        btnAddContact.click();
    }
    public void closeAlert() {
        Alert alert = new WebDriverWait(driver, Duration.ofSeconds(5)).until(alertIsPresent());
        alert.accept();
    }
    public String getAlertText() {
        Alert alert = new WebDriverWait(driver, Duration.ofSeconds(5)).until(alertIsPresent());
        String text = alert.getText();
        alert.accept();
        return text;
    }
    public boolean isContactInList(ContactLombok contact){
        for (WebElement contactElement : contactsList) {
            System.out.println(contactElement.getText());
            if (contactElement.getText().contains(contact.getName())){
                return true;
            }
        }
        return false;
    }
}
