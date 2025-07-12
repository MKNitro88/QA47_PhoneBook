package pages;

import dto.ContactDto;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

import java.util.List;

public class ContactPage extends  BasePage {

    public ContactPage(WebDriver driver) {
        setDriver(driver);
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 10), this);
    }

    @FindBy(xpath = "//a[text()='CONTACTS']")
    WebElement btnContactsHeader;
    @FindBy(xpath = "//div[@class = 'contact-item_card__2SOIM']")
    List<WebElement> contactsList;
    @FindBy(xpath = "//button[text()='Edit']")
    WebElement btnEditContact;
    @FindBy(xpath = "//button[text()='Remove']")
    WebElement btnRemoveContact;
    @FindBy(xpath = "//div[@class = 'form_form__FOqHs']")
    WebElement contactEditForm;
    @FindBy(xpath = "//input[@placeholder='Name']")
    WebElement inputContactName;
    @FindBy(xpath = "//input[@placeholder='Last Name']")
    WebElement inputContactLastName;
    @FindBy(xpath = "//input[@placeholder='Phone']")
    WebElement inputContactPhone;
    @FindBy(xpath = "//input[@placeholder='email']")
    WebElement inputContactEmail;
    @FindBy(xpath = "//input[@placeholder='Address']")
    WebElement inputContactAddress;
    @FindBy(xpath = "//input[@placeholder='desc']")
    WebElement inputContactDesc;
    @FindBy(xpath = "//button[text()='Save']")
    WebElement btnSaveContact;



    public boolean isBtnContactsHeaderDisplayed() {

        return isElementPreseant(btnContactsHeader);
    }

    public boolean isContactInList(ContactDto contact) {
        for (WebElement contactElement : contactsList) {
            System.out.println(contactElement.getText());
            logger.info("Checking contact in list: " + contactElement.getText());
            if (contactElement.getText().contains(contact.getName())
                    && contactElement.getText().contains(contact.getPhone())) {
                logger.info("Contact found in list: " + contactElement.getText() +" number in list: "+ contactsList.indexOf(contactElement) +1);
                return true;
            }
        }
        logger.info("Contact not found in list: " + contact);
        return false;
    }

    public Integer getContactsSize() {
        return contactsList.size();
    }

    public Integer getContactsListSize2UseFindElement() {
        List<WebElement> contactsFindElement = driver.findElements(By.xpath("//div[@class = 'contact-item_card__2SOIM']"));
        return contactsList.size();
    }

    public String getPhoneNumberFromContact() {
        if (!contactsList.isEmpty()) {
            System.out.println(contactsList.get(0).getText().split("\n")[1]);
            return contactsList.get(0).getText().split("\n")[1];
        }
        System.out.println("contactsList is empty");
        return null;

    }
    public void clickOnFirstContactInList(){
        if (!contactsList.isEmpty()) {
            contactsList.get(0).click();
        } else {
            System.out.println("contactsList is empty");
        }
    }
    public void clickBtnEditContact() {
            btnEditContact.click();
    }
    public void clickBtnRemoveContact() {
        btnRemoveContact.click();
        pause(1);
    }
    public void fillEditForm(ContactDto contact){
        logger.info("edit contact form using -> "+ contact);
        inputContactName.clear();
        inputContactName.sendKeys(contact.getName());
        inputContactLastName.clear();
        inputContactLastName.sendKeys(contact.getLastName());
        inputContactPhone.clear();
        inputContactPhone.sendKeys(contact.getPhone());
        inputContactEmail.clear();
        inputContactEmail.sendKeys(contact.getEmail());
        inputContactAddress.clear();
        inputContactAddress.sendKeys(contact.getAddress());
        inputContactDesc.clear();
        inputContactDesc.sendKeys(contact.getDescription());
        btnSaveContact.click();
        pause(1);

    }

}
