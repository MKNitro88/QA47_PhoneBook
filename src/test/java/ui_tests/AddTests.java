package ui_tests;

import dto.ContactLombok;
import dto.UserLombok;
import manager.ApplicationManager;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.AddPage;
import pages.ContactPage;
import pages.HomePage;
import pages.LoginPage;
import utils.HeaderMenuItem;

import static pages.BasePage.clickButtonsOnHeader;
import static utils.RandomUtils.*;

public class AddTests extends ApplicationManager {
    HomePage homePage;
    LoginPage loginPage;
    AddPage addPage;
    ContactPage contactPage;

    int sizeBeforeAdd;
    String testEmail = "mail@mail.mail";
    String testPassword = "123456Q$qqq";

    @BeforeMethod
    public void goToLoginPage() {
        homePage = new HomePage(getDriver());
        loginPage = clickButtonsOnHeader(HeaderMenuItem.LOGIN);
        UserLombok user = UserLombok.builder()
                .email(testEmail)
                .password(testPassword)
                .build();
        loginPage.fillLoginForm(user);
        loginPage.clickBtnLogin();
        contactPage =clickButtonsOnHeader(HeaderMenuItem.CONTACTS);
        sizeBeforeAdd = contactPage.getContactsSize();
        addPage = clickButtonsOnHeader(HeaderMenuItem.ADD);


    }
    @Test
    public void addPositiveTest() {
        ContactLombok contact = ContactLombok.builder()
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
        Assert.assertEquals(sizeAfterAdd, sizeBeforeAdd + 1, "Contact was not added");



    }

}
