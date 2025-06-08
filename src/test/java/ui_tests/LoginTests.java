package ui_tests;

import dto.User;
import manager.ApplicationManager;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.ContactPage;
import pages.HomePage;
import pages.LoginPage;

import static pages.BasePage.pause;

public class LoginTests extends ApplicationManager {
    String testEmail = "mail@mail.mail";
    String testPassword = "123456Q$qqq";

    @Test
    public void loginPositiveTest() {
        User user = new User(testEmail, testPassword);
        HomePage homePage = new HomePage(getDriver());
        homePage.clickBtnLoginHeader();
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.fillLoginForm(user);
        loginPage.clickBtnLogin();
        ContactPage contactPage = new ContactPage(getDriver());
        Assert.assertTrue(contactPage.isBtnContactsHeaderDisplayed(),"Login failed");

    }
    @Test
    public void loginNegativeTest_wrongPassword() {
        User user = new User(testEmail, "wrongPassword");
        HomePage homePage = new HomePage(getDriver());
        homePage.clickBtnLoginHeader();
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.fillLoginForm(user);
        loginPage.clickBtnLogin();
        Assert.assertTrue(loginPage.isAlertTextContains("Wrong email or password"));
        loginPage.closeAlert();
        Assert.assertTrue(loginPage.isErrorMessageDisplayed("Login Failed with code 401"));



    }
    @Test
    public void LoginNegativeTest_EmptyFields() {
        User user = new User("","");
        HomePage homePage = new HomePage(getDriver());
        homePage.clickBtnLoginHeader();
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.fillLoginForm(user);
        loginPage.clickBtnLogin();
        loginPage.closeAlert();
        Assert.assertTrue((loginPage.isErrorMessageDisplayed("Login Failed with code 401")), "Error message is not displayed");
    }
}

