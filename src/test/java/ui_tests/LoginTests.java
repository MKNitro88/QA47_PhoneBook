package ui_tests;

import dto.UserDto;
import manager.ApplicationManager;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.ContactPage;
import pages.HomePage;
import pages.LoginPage;
import utils.HeaderMenuItem;
import utils.TestNGListner;

import java.lang.reflect.Method;

import static pages.BasePage.clickButtonsOnHeader;
import static utils.PropertiesReader.getProperty;

@Listeners(TestNGListner.class)


public class LoginTests extends ApplicationManager {
    String testEmail = getProperty("loginPB.properties","email");
    String testPassword = getProperty("loginPB.properties","password");
    SoftAssert softAssert = new SoftAssert();
    HomePage homePage;
    LoginPage loginPage;

    @BeforeMethod(alwaysRun = true)
    public void goToLoginPage() {
        homePage = new HomePage(getDriver());
        loginPage = clickButtonsOnHeader(HeaderMenuItem.LOGIN);

    }

    @Test(groups = "smoke")
    public void loginPositiveTest(Method method) {
        logger.info("start method " + method.getName());
        UserDto user = UserDto.builder()
                .username(testEmail)
                .password(testPassword)
                .build();
        logger.info("test data: " + user);
        loginPage.fillLoginForm(user);
        loginPage.clickBtnLogin();
        ContactPage contactPage = new ContactPage(getDriver());
        softAssert.assertTrue(contactPage.isBtnContactsHeaderDisplayed(),"Login failed");
        softAssert.assertAll();

    }
    @Test
    public void loginNegativeTest_wrongPassword(Method method) {
        logger.info("start method " + method.getName());
        UserDto user = UserDto.builder()
                .username(testEmail)
                .password("wrongPassword")
                .build();
        logger.info("test data: " + user);
        loginPage.fillLoginForm(user);
        loginPage.clickBtnLogin();
        softAssert.assertTrue(loginPage.closeAlertReturnText().contains("Wrong email or password"));
        softAssert.assertTrue(loginPage.isErrorMessageDisplayed("Login Failed with code 401"));
        softAssert.assertAll();



    }
    @Test
    public void LoginNegativeTest_EmptyFields(Method method) {
        logger.info("start method " + method.getName());
        UserDto user = UserDto.builder()
                .username("")
                .password("")
                .build();
        logger.info("test data: " + user);
        loginPage.fillLoginForm(user);
        loginPage.clickBtnLogin();
        softAssert.assertTrue(loginPage.closeAlertReturnText().contains("Wrong email or password"));
        softAssert.assertTrue((loginPage.isErrorMessageDisplayed("Login Failed with code 401")), "Error message is not displayed");
        softAssert.assertAll();
    }
}

