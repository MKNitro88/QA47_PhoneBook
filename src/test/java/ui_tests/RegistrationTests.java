package ui_tests;

import dto.User;
import dto.UserLombok;
import manager.ApplicationManager;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;
import utils.HeaderMenuItem;

import static pages.BasePage.clickButtonsOnHeader;
import static pages.BasePage.pause;
import static utils.RandomUtils.generateEmail;
import static utils.RandomUtils.generateString;

public class RegistrationTests extends ApplicationManager {
    String testEmail = "mail@mail.mail";
    String testPassword = "123456Q$qqq";
    HomePage homePage;
    LoginPage loginPage;

    @BeforeMethod
    public void goToRegistrationPage() {
        homePage = new HomePage(getDriver());
        loginPage = clickButtonsOnHeader(HeaderMenuItem.LOGIN);
    }

    @Test
    public void registrationPositiveTest() {
        UserLombok user = UserLombok.builder()
                .email(generateEmail(5))
                .password(generateString(6) + "1Z!")
                .build();
        loginPage.fillLoginForm(user);
        loginPage.clickBtnRegistration();
        Assert.assertTrue(loginPage.isContactMessageDisplayed("Add new by clicking on Add in NavBar"));
    }

    @Test
    public void registrationNegativeTest_ExistingUser() {
        UserLombok user = UserLombok.builder()
                .email(testEmail)
                .password(testPassword)
                .build();
        loginPage.fillLoginForm(user);
        loginPage.clickBtnRegistration();
        Assert.assertTrue(loginPage.closeAlertReturnText().contains("User already exist"));
        Assert.assertTrue(loginPage.isErrorMessageDisplayed("Registration failed with code 409"));

    }

    @Test
    public void registrationNegativeTest_wrongFormat() {
        UserLombok user = UserLombok.builder()
                .email("@!#")
                .password("@!#")
                .build();
        loginPage.fillLoginForm(user);
        loginPage.clickBtnRegistration();
        Assert.assertTrue(loginPage.closeAlertReturnText().contains("Wrong email or password format"));
    }

}