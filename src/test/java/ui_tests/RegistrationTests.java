package ui_tests;

import dto.UserDto;
import manager.ApplicationManager;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;
import utils.HeaderMenuItem;
import utils.RetryAnalyzer;
import utils.TestNGListner;

import static pages.BasePage.clickButtonsOnHeader;
import static utils.PropertiesReader.getProperty;
import static utils.RandomUtils.generateEmail;
import static utils.RandomUtils.generateString;

@Listeners(TestNGListner.class)

public class RegistrationTests extends ApplicationManager {
    String testEmail = getProperty("loginPB.properties","email");
    String testPassword = getProperty("loginPB.properties","password");
    HomePage homePage;
    LoginPage loginPage;

    @BeforeMethod(alwaysRun = true)
    public void goToRegistrationPage() {
        homePage = new HomePage(getDriver());
        loginPage = clickButtonsOnHeader(HeaderMenuItem.LOGIN);
    }

    @Test(groups = "smoke")
    public void registrationPositiveTest() {
        UserDto user = UserDto.builder()
                .username(generateEmail(5))
                .password(generateString(6) + "1Z!")
                .build();
        loginPage.fillLoginForm(user);
        loginPage.clickBtnRegistration();
        Assert.assertTrue(loginPage.isContactMessageDisplayed("Add new by clicking on Add in NavBar"));
    }

    @Test
    public void registrationNegativeTest_ExistingUser() {
        UserDto user = UserDto.builder()
                .username(testEmail)
                .password(testPassword)
                .build();
        loginPage.fillLoginForm(user);
        loginPage.clickBtnRegistration();
        Assert.assertTrue(loginPage.closeAlertReturnText().contains("User already exist"));
        Assert.assertTrue(loginPage.isErrorMessageDisplayed("Registration failed with code 409"));

    }

    @Test
    public void registrationNegativeTest_wrongFormat() {
        UserDto user = UserDto.builder()
                .username("@!#")
                .password("@!#")
                .build();
        loginPage.fillLoginForm(user);
        loginPage.clickBtnRegistration();
        Assert.assertTrue(loginPage.closeAlertReturnText().contains("Wrong email or password format"));
    }

}