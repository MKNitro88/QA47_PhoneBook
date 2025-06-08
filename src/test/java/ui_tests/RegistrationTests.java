package ui_tests;

import dto.User;
import manager.ApplicationManager;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.ContactPage;
import pages.HomePage;
import pages.LoginPage;

import static pages.BasePage.pause;

public class RegistrationTests extends ApplicationManager {
    String testEmail = "mail@mail.mail";
    String testPassword = "123456Q$qqq";
    HomePage homePage;
    LoginPage loginPage;

    @BeforeMethod
    public void goToRegistrationPage() {
        homePage = new HomePage(getDriver());
        homePage.clickBtnLoginHeader();
        loginPage = new LoginPage(getDriver());
    }

    @Test
    public void registrationPositiveTest() {
        User user = new User();
        user.generateRandomUser();
        loginPage.fillLoginForm(user);
        loginPage.clickBtnRegistration();
        Assert.assertTrue(loginPage.isContactMessageDisplayed("Add new by clicking on Add in NavBar"));
    }

    @Test
    public void registrationNegativeTest_ExistingUser() {
        User user = new User(testEmail, testPassword);
        loginPage.fillLoginForm(user);
        loginPage.clickBtnRegistration();
        Assert.assertTrue(loginPage.isAlertTextContains("User already exist"));
        loginPage.closeAlert();
        Assert.assertTrue(loginPage.isErrorMessageDisplayed("Registration failed with code 409"));

    }

    @Test
    public void registrationNegativeTest_wrongFormat() {
        User user = new User("@!#","@!#");
        loginPage.fillLoginForm(user);
        loginPage.clickBtnRegistration();
        Assert.assertTrue(loginPage.getAlertText().contains("Wrong email or password format"));
    }

}