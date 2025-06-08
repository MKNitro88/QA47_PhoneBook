package ui_tests;

import manager.ApplicationManager;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.HomePage;


public class HomeTests extends ApplicationManager {
    @Test
    public void navToHomePagePositiveTest() {
        HomePage homePage = new HomePage(getDriver());
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(homePage.getDivHomeText().contains("Home Component"));
        softAssert.assertTrue(homePage.isDivHomeDisplayed());
        softAssert.assertAll();
    }
}
