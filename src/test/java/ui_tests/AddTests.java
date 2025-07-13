package ui_tests;

import data_provider.ContactsDP;
import dto.ContactDto;
import dto.UserDto;
import manager.ApplicationManager;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.AddPage;
import pages.ContactPage;
import pages.HomePage;
import pages.LoginPage;
import utils.HeaderMenuItem;
import utils.TestNGListner;

import static pages.BasePage.clickButtonsOnHeader;
import static utils.RandomUtils.*;

@Listeners(TestNGListner.class)

public class AddTests extends ApplicationManager {
    HomePage homePage;
    LoginPage loginPage;
    AddPage addPage;
    ContactPage contactPage;
    String urlBeforeAdd;

    int sizeBeforeAdd;
    String testEmail = "mail@mail.mail";
    String testPassword = "123456Q$qqq";
    String existingPhone;

    @BeforeMethod(alwaysRun = true)
    public void goToLoginPage() {
        homePage = new HomePage(getDriver());
        loginPage = clickButtonsOnHeader(HeaderMenuItem.LOGIN);
        UserDto user = UserDto.builder()
                .username(testEmail)
                .password(testPassword)
                .build();
        loginPage.fillLoginForm(user);
        loginPage.clickBtnLogin();
        contactPage =clickButtonsOnHeader(HeaderMenuItem.CONTACTS);
        sizeBeforeAdd = contactPage.getContactsSize();
        existingPhone = contactPage.getPhoneNumberFromContact();
        addPage = clickButtonsOnHeader(HeaderMenuItem.ADD);
        urlBeforeAdd = getDriver().getCurrentUrl();



    }
    @Test(invocationCount = 1, groups = "smoke")
    public void addPositiveTest() {
        ContactDto contact = ContactDto.builder()
                .name(generateString(5))
                .lastName(generateString(5))
                .phone(generatePhoneNumber(10))
                .email(generateEmail(5))
                .address(generateString(10))
                .description(generateString(20))
                .build();
        addPage.fillAddContactForm(contact);
        int sizeAfterAdd = contactPage.getContactsSize();
        Assert.assertTrue(contactPage.isContactInList(contact));
        Assert.assertEquals(sizeAfterAdd, sizeBeforeAdd + 1, "positive add contact test");
    }
    @Test(dataProvider = "addNewContactFromFileNegative", dataProviderClass = ContactsDP.class,groups = "contacts")
    public void addContactNegativeTests(ContactDto contact) {
        logger.info("test data --> " + contact);
        addPage.fillAddContactForm(contact);
        Assert.assertTrue(addPage.validateUrl("/add"));
        Assert.assertTrue(addPage.isAddContactButtonDisplayed());

    }
    @Test(dataProvider = "addNewContactFromFilePositive", dataProviderClass = ContactsDP.class,groups = "contacts")
    public void addContactPositiveTests(ContactDto contact) {
        logger.info("test data --> " + contact);
        addPage.fillAddContactForm(contact);
        int sizeAfterAdd = contactPage.getContactsSize();
        Assert.assertTrue(contactPage.isContactInList(contact));
        Assert.assertEquals(sizeAfterAdd, sizeBeforeAdd + 1, "positive add contact test");
        //Assert.assertTrue(addPage.validateUrl("/add"));
        //Assert.assertTrue(addPage.isAddContactButtonDisplayed());

    }
    @Test(groups = "contacts")
    public void addNegativeTest_NoName(){
       ContactDto contact = ContactDto.builder()
                .name("")
                .lastName(generateString(5))
                .phone(generatePhoneNumber(10))
                .email(generateEmail(5))
                .address(generateString(10))
                .description(generateString(20))
                .build();
        addPage.fillAddContactForm(contact);
        Assert.assertTrue(addPage.validateUrl("/add"),
                "Negative add contact - no name");

    }
    @Test(groups = "contacts")
    public void addNegativeTest_NoLastName(){
        ContactDto contact = ContactDto.builder()
                .name(generateString(5))
                .lastName("")
                .phone(generatePhoneNumber(10))
                .email(generateEmail(5))
                .address(generateString(10))
                .description(generateString(20))
                .build();
        addPage.fillAddContactForm(contact);
        Assert.assertTrue(addPage.validateUrl("/add"),
                "Negative add contact - no last name");
        Assert.assertTrue(addPage.isAddContactButtonDisplayed());

    }
    @Test(groups = "contacts")
    public void addNegativeTest_NoPhone(){
        ContactDto contact = ContactDto.builder()
                .name(generateString(5))
                .lastName(generateString(5))
                .phone("")
                .email(generateEmail(5))
                .address(generateString(10))
                .description(generateString(20))
                .build();
        addPage.fillAddContactForm(contact);
        Assert.assertTrue(addPage.getAlertText()
                .contains("Phone not valid: Phone number must contain only digits! And length min 10, max 15!"),
                "negative add contact test - no phone");

    }
    @Test(groups = "contacts")
    public void addNegativeTest_ExistingPhone(){
        System.out.println(existingPhone);
        ContactDto contact = ContactDto.builder()
                .name(generateString(5))
                .lastName(generateString(5))
                .phone(existingPhone)
                .email(generateEmail(5))
                .address(generateString(10))
                .description(generateString(20))
                .build();
        addPage.fillAddContactForm(contact);
        Assert.assertTrue(addPage.validateUrl("/contacts"));
        //pause(5);
//        Assert.assertTrue(addPage.getAlertText()
//                        .contains("Phone not valid: Phone number must contain only digits! And length min 10, max 15!"),
//                "negative add contact test - no phone");

    }
    @Test(groups = "contacts")
    public void addNegativeTest_invalidPhoneTooShort(){
        ContactDto contact = ContactDto.builder()
                .name(generateString(5))
                .lastName(generateString(5))
                .phone(generatePhoneNumber(1))
                .email(generateEmail(5))
                .address(generateString(10))
                .description(generateString(20))
                .build();
        addPage.fillAddContactForm(contact);
        Assert.assertTrue(addPage.getAlertText()
                        .contains("Phone not valid: Phone number must contain only digits! And length min 10, max 15!"),
                "negative add contact test - phone less than 10 digits");

    }
    @Test(groups = "contacts")
    public void addNegativeTest_invalidPhoneTooLong(){
        ContactDto contact = ContactDto.builder()
                .name(generateString(5))
                .lastName(generateString(5))
                .phone(generatePhoneNumber(16))
                .email(generateEmail(5))
                .address(generateString(10))
                .description(generateString(20))
                .build();
        addPage.fillAddContactForm(contact);
        Assert.assertTrue(addPage.getAlertText()
                        .contains("Phone not valid: Phone number must contain only digits! And length min 10, max 15!"),
                "negative add contact test - phone more than 15 digits");

    }
    @Test(groups = "contacts")
    public void addNegativeTest_NoEmail(){
        ContactDto contact = ContactDto.builder()
                .name(generateString(5))
                .lastName(generateString(5))
                .phone(generatePhoneNumber(8))
                .email("")
                .address(generateString(10))
                .description(generateString(20))
                .build();
        addPage.fillAddContactForm(contact);
        Assert.assertTrue(addPage.validateUrl("/add"),
                "Negative add contact - no email");
        Assert.assertTrue(addPage.urlNotContains("/contacts"),
                "Negative add contact - no email");

    }
    @Test(groups = "contacts")
    public void addNegativeTest_invalidEmailFormat(){
        ContactDto contact = ContactDto.builder()
                .name(generateString(5))
                .lastName(generateString(5))
                .phone(generatePhoneNumber(7))
                .email("invalidEmailFormat")
                .address(generateString(10))
                .description(generateString(20))
                .build();
        addPage.fillAddContactForm(contact);
        Assert.assertTrue(addPage.getAlertText()
                        .contains("Email not valid: must be a well-formed email address"),
                "negative add contact test - email wrong format");


    }
    @Test(groups = "contacts")
    public void addNegativeTest_NoAddress(){
        ContactDto contact = ContactDto.builder()
                .name(generateString(5))
                .lastName(generateString(5))
                .phone(generatePhoneNumber(7))
                .email(generateEmail(5))
                .address(generateString(10))
                .description(generateString(20))
                .build();
        addPage.fillAddContactForm(contact);
        Assert.assertTrue(addPage.validateUrl("/add"),
                "Negative add contact - no Address");
        Assert.assertTrue(addPage.urlNotContains("/contacts"));

    }

}
