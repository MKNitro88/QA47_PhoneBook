package ui_tests;

import dto.User;
import manager.ApplicationManager;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.ContactPage;
import pages.HomePage;
import pages.LoginPage;

import static pages.BasePage.pause;

public class RegistrationTests extends ApplicationManager {
    String testEmail = "mail@mail.mail";
    String testPassword = "123456Q$qqq";
    @Test
    public void registrationPositiveTest(){
        User user = new User();
        user.generateRandomUser();
        HomePage homePage = new HomePage(getDriver());
        pause(1);
        homePage.clickBtnLoginHeader();
        LoginPage loginPage = new LoginPage(getDriver());
        pause(1);
        loginPage.fillLoginForm(user);
        pause(1);
        loginPage.clickBtnRegistration();
        pause(3);
        ContactPage contactPage = new ContactPage(getDriver());
        Assert.assertTrue(contactPage.isBtnContactsHeaderDisplayed());
    }
    @Test
    public void registrationNegativeTest_ExistingUser(){
        User user = new User(testEmail, testPassword);
        HomePage homePage = new HomePage(getDriver());
        pause(1);
        homePage.clickBtnLoginHeader();
        LoginPage loginPage = new LoginPage(getDriver());
        pause(1);
        loginPage.fillLoginForm(user);
        pause(1);
        loginPage.clickBtnRegistration();
        pause(3);
        Assert.assertTrue(loginPage.isAlertTextContains("User already exist"));
        loginPage.closeAlert();
        Assert.assertTrue(loginPage.isErrorMessageDisplayed("Registration failed with code 409"));

    }
}
