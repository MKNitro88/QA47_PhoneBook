package ui_tests;

import data_provider.ContactsDP;
import dto.ContactDto;
import dto.ContactsDto;
import dto.User;
import dto.UserDto;
import io.restassured.response.Response;
import manager.ApplicationManager;
import manager.ContactController;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.*;
import utils.HeaderMenuItem;

import static pages.BasePage.clickButtonsOnHeader;
import static utils.PropertiesReader.getProperty;
import static utils.RandomUtils.*;
import static utils.RandomUtils.generateString;

public class AddNewContactsWithApiTest extends ApplicationManager {
    HomePage homePage;
    LoginPage loginPage;
    AddPage addPage;
    ContactPage contactPage;


    String testEmail = getProperty("loginPB.properties","email");;
    String testPassword = getProperty("loginPB.properties","password");;



    @BeforeMethod(alwaysRun = true)
    public void login() {
        UserDto user = UserDto.builder()
                .username(testEmail)
                .password(testPassword)
                .build();

        homePage = new HomePage(getDriver());
        loginPage = clickButtonsOnHeader(HeaderMenuItem.LOGIN);
        loginPage.fillLoginForm(user);
        loginPage.clickBtnLogin();
        contactPage = new ContactPage(getDriver());
        addPage = clickButtonsOnHeader(HeaderMenuItem.ADD);
    }

    @Test()
    public void addNewContactPositiveTest() {
        ContactDto contact = ContactDto.builder()
                .name("123"+generateString(5))
                .lastName(generateString(10))
                .phone("0123456789")
                .email(generateEmail(10))
                .address("Haifa " + generateString(10))
                .description("desc " + generateString(15))
                .build();
        addPage.fillAddContactForm(contact);
        BasePage.pause(10);
        ContactController contactController = new ContactController();
        contactController.login();
        Response response = contactController.getAllContacts();
        System.out.println(response.getStatusLine());
        ContactsDto contactsDto = new ContactsDto();
        if(response.getStatusCode()==200){
            contactsDto = response.body().as(ContactsDto.class);
        }
        for (ContactDto contact1: contactsDto.getContacts()){
            if(contact1.equals(contact)){
                System.out.println(contact1);
                System.out.println(contact);
                Assert.assertEquals(contact1, contact);
            }
        }

    }
}
