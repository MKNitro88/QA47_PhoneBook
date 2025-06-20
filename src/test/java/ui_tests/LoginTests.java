package ui_tests;

import dto.UserLombok;
import manager.ApplicationManager;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.ContactPage;
import pages.HomePage;
import pages.LoginPage;
import utils.HeaderMenuItem;

import java.lang.reflect.Method;

import static pages.BasePage.clickButtonsOnHeader;

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
    public void loginPositiveTest(Method method) {
        logger.info("start method " + method.getName());
        UserLombok user = UserLombok.builder()
                .email(testEmail)
                .password(testPassword)
                .build();
        logger.info("test data: " + user);
        loginPage.fillLoginForm(user);
        loginPage.clickBtnLogin();
        ContactPage contactPage = new ContactPage(getDriver());
        Assert.assertTrue(contactPage.isBtnContactsHeaderDisplayed(),"Login failed");

    }
    @Test
    public void loginNegativeTest_wrongPassword(Method method) {
        logger.info("start method " + method.getName());
        UserLombok user = UserLombok.builder()
                .email(testEmail)
                .password("wrongPassword")
                .build();
        logger.info("test data: " + user);
        loginPage.fillLoginForm(user);
        loginPage.clickBtnLogin();
        Assert.assertTrue(loginPage.closeAlertReturnText().contains("Wrong email or password"));
        Assert.assertTrue(loginPage.isErrorMessageDisplayed("Login Failed with code 401"));



    }
    @Test
    public void LoginNegativeTest_EmptyFields(Method method) {
        logger.info("start method " + method.getName());
        UserLombok user = UserLombok.builder()
                .email("")
                .password("")
                .build();
        logger.info("test data: " + user);
        loginPage.fillLoginForm(user);
        loginPage.clickBtnLogin();
        Assert.assertTrue(loginPage.closeAlertReturnText().contains("Wrong email or password"));
        Assert.assertTrue((loginPage.isErrorMessageDisplayed("Login Failed with code 401")), "Error message is not displayed");
    }
}

