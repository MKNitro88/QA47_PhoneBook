package ui_tests;

import manager.ApplicationManager;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.AboutPage;
import pages.BasePage;
import pages.HomePage;
import utils.HeaderMenuItem;

import static pages.BasePage.clickButtonsOnHeader;
import static pages.BasePage.pause;


public class AboutTests extends ApplicationManager {
    HomePage homePage;
    AboutPage aboutPage;
    @BeforeMethod
    public void goToAboutPage() {
        homePage = new HomePage(getDriver());
        aboutPage = clickButtonsOnHeader(HeaderMenuItem.ABOUT);
    }
    @Test
    public void navToAboutPagePositiveTest() {
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(aboutPage.isDivAboutDisplayed());
        softAssert.assertTrue(aboutPage.getDivAboutText().contains("Contacts Web Application"));
        softAssert.assertAll();


    }
}
