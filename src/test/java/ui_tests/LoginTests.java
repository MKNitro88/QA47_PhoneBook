package ui_tests;

import manager.ApplicationManager;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.HomePage;
import pages.LoginPage;

import static pages.BasePage.pause;

public class LoginTests extends ApplicationManager {
    String testEmail = "mail@mail.mail";
    String testPassword = "123456Q$qqq";

    @Test
    public void loginPositiveTest() {
        SoftAssert softAssert = new SoftAssert();
        HomePage homePage = new HomePage(getDriver());
        pause(2);
        homePage.clickBtnLoginHeader();
        LoginPage loginPage = new LoginPage(getDriver());
        pause(2);
        loginPage.fillLoginForm(testEmail, testPassword);
        pause(1);
        loginPage.clickBtnLogin();
        pause(3);
        softAssert.assertTrue(homePage.isBtnSignOutHeaderDisplayed()," Login failed, Sign Out button is not displayed");
        softAssert.assertTrue(homePage.isBtnContactsHeaderDisplayed(),
                "Login failed, Contacts button is not displayed");
        softAssert.assertAll();
        ;

    }
}

