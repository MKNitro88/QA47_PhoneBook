package ui_tests;

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

import java.lang.reflect.Method;
import static pages.BasePage.clickButtonsOnHeader;
import static utils.RandomUtils.generateEmail;
import static utils.RandomUtils.generateString;

@Listeners(TestNGListner.class)

public class ContactsTests extends ApplicationManager {
    HomePage homePage;
    LoginPage loginPage;
    ContactPage contactPage;
    String testEmail = "mail@mail.mail";
    String testPassword = "123456Q$qqq";
    int sizeBeforeEdit;

    ContactDto forTesting = ContactDto.builder()
            .name("o9cz4")
            .lastName("mm3ir")
            .phone("0541985766")
            .email("mwohd@yahoo.com")
            .address("3umrghmddm")
            .description("l31v31yycnpbdct06juj")
            .build();

    @BeforeMethod(alwaysRun = true)
    public void goToContactsPage() {
        homePage = new HomePage(getDriver());
        loginPage = clickButtonsOnHeader(HeaderMenuItem.LOGIN);
        UserDto user = UserDto.builder()
                .username(testEmail)
                .password(testPassword)
                .build();
        loginPage.fillLoginForm(user);
        loginPage.clickBtnLogin();
        contactPage =clickButtonsOnHeader(HeaderMenuItem.CONTACTS);
        sizeBeforeEdit = contactPage.getContactsSize();
    }

    @Test(groups = "smoke")
    public void positiveContactEditTest(Method method) {
        logger.info("start method " + method.getName());
        contactPage.clickOnFirstContactInList();
        ContactDto contactEdit = ContactDto.builder()
                .name(generateString(5))
                .lastName("edited Last Name")
                .phone("1234567890")
                .email(generateEmail(5))
                .address("edited address")
                .description("edited description")
                .build();
        logger.info("test data: " + contactEdit);
        contactPage.clickBtnEditContact();
        contactPage.fillEditForm(contactEdit);
        Assert.assertTrue(contactPage.isContactInList(contactEdit));


    }
    @Test(groups = "smoke")
    public void positiveContactRemoveTest(Method method) {
        logger.info("start method " + method.getName());
        contactPage.clickOnFirstContactInList();
        contactPage.clickBtnRemoveContact();
        int sizeAfterRemove = contactPage.getContactsSize();
        Assert.assertEquals(sizeBeforeEdit, sizeAfterRemove +1, "Contact was not removed");
    }

}
