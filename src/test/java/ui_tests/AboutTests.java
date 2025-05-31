package ui_tests;

import manager.ApplicationManager;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.AboutPage;
import pages.HomePage;

import static pages.BasePage.pause;

public class AboutTests extends ApplicationManager {
    @Test
    public void navToAboutPagePositiveTest() {
        SoftAssert softAssert = new SoftAssert();
        HomePage homePage = new HomePage(getDriver());
        pause(2);
        homePage.clickBtnAboutHeader();
        AboutPage aboutPage = new AboutPage(getDriver());
        pause(2);
        softAssert.assertTrue(aboutPage.isDivAboutDisplayed());
        softAssert.assertTrue(aboutPage.getDivAboutText().contains("Contacts Web Application"));
        softAssert.assertAll();


    }
}
