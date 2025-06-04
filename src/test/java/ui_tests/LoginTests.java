package ui_tests;

import dto.User;
import manager.ApplicationManager;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
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
        pause(2);
        homePage.clickBtnLoginHeader();
        LoginPage loginPage = new LoginPage(getDriver());
        pause(2);
        loginPage.fillLoginForm(user);
        pause(1);
        loginPage.clickBtnLogin();
        pause(3);
        ContactPage contactPage = new ContactPage(getDriver());
        Assert.assertTrue(contactPage.isBtnContactsHeaderDisplayed(),"Login failed");

    }
    @Test
    public void loginNegativeTest_wrongPassword() {
        User user = new User(testEmail, "wrongPassword");
        HomePage homePage = new HomePage(getDriver());
        pause(2);
        homePage.clickBtnLoginHeader();
        LoginPage loginPage = new LoginPage(getDriver());
        pause(2);
        loginPage.fillLoginForm(user);
        pause(1);
        loginPage.clickBtnLogin();
        pause(3);
        loginPage.closeAlert();
        Assert.assertTrue(loginPage.isErrorMessageDisplayed("Login Failed with code 401"), "Error message is not displayed");



    }
}

