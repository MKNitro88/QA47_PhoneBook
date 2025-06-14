package pages;

import dto.ContactLombok;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

import java.util.List;

public class ContactPage extends  BasePage{

    public ContactPage(WebDriver driver) {
        setDriver(driver);
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 10), this);
    }
    @FindBy(xpath = "//a[text()='CONTACTS']")
    WebElement btnContactsHeader;
    @FindBy(xpath = "//div[@class = 'contact-item_card__2SOIM']/h2")
    List<WebElement> contactsList;


    public boolean isBtnContactsHeaderDisplayed() {

        return isElementPreseant(btnContactsHeader);
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
