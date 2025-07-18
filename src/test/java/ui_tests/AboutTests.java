package ui_tests;

import manager.ApplicationManager;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.AboutPage;
import pages.HomePage;
import utils.HeaderMenuItem;
import utils.TestNGListner;

import static pages.BasePage.clickButtonsOnHeader;
@Listeners(TestNGListner.class)


public class AboutTests extends ApplicationManager {
    HomePage homePage;
    AboutPage aboutPage;
    SoftAssert softAssert = new SoftAssert();

    @BeforeMethod(alwaysRun = true)
    public void goToAboutPage() {
        homePage = new HomePage(getDriver());
        aboutPage = clickButtonsOnHeader(HeaderMenuItem.ABOUT);
    }
    @Test(groups = "smoke")
    public void navToAboutPagePositiveTest() {

        softAssert.assertTrue(aboutPage.isDivAboutDisplayed());
        softAssert.assertTrue(aboutPage.getDivAboutText().contains("Contacts Web Application"));
        softAssert.assertAll();


    }
}
