package ui_tests;

import dto.User;
import dto.UserLombok;
import manager.ApplicationManager;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.ContactPage;
import pages.HomePage;
import pages.LoginPage;
import utils.HeaderMenuItem;

import static pages.BasePage.clickButtonsOnHeader;
import static pages.BasePage.pause;

public class LoginTests extends ApplicationManager {
    String testEmail = "mail@mail.mail";
    String testPassword = "123456Q$qqq";

    HomePage homePage;
    LoginPage loginPage;
    @BeforeMethod
    public void goToLoginPage() {
        homePage = new HomePage(getDriver());
        loginPage = clickButtonsOnHeader(HeaderMenuItem.LOGIN);

    }

    @Test
    public void loginPositiveTest() {
        UserLombok user = UserLombok.builder()
                .email(testEmail)
                .password(testPassword)
                .build();
        loginPage.fillLoginForm(user);
        loginPage.clickBtnLogin();
        ContactPage contactPage = new ContactPage(getDriver());
        Assert.assertTrue(contactPage.isBtnContactsHeaderDisplayed(),"Login failed");

    }
    @Test
    public void loginNegativeTest_wrongPassword() {
        UserLombok user = UserLombok.builder()
                .email(testEmail)
                .password("wrongPassword")
                .build();
        loginPage.fillLoginForm(user);
        loginPage.clickBtnLogin();
        pause(2);
        Assert.assertTrue(loginPage.isAlertTextContains("Wrong email or password"));
        loginPage.closeAlert();
        Assert.assertTrue(loginPage.isErrorMessageDisplayed("Login Failed with code 401"));



    }
    @Test
    public void LoginNegativeTest_EmptyFields() {
        UserLombok user = UserLombok.builder()
                .email("")
                .password("")
                .build();
        loginPage.fillLoginForm(user);
        loginPage.clickBtnLogin();
        loginPage.closeAlert();
        Assert.assertTrue((loginPage.isErrorMessageDisplayed("Login Failed with code 401")), "Error message is not displayed");
    }
}

