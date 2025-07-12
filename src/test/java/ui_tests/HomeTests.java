package ui_tests;

import manager.ApplicationManager;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.HomePage;
import utils.TestNGListner;

@Listeners(TestNGListner.class)


public class HomeTests extends ApplicationManager {
    SoftAssert softAssert = new SoftAssert();

    @Test(groups = "smoke")
    public void navToHomePagePositiveTest() {
        HomePage homePage = new HomePage(getDriver());
        softAssert.assertTrue(homePage.getDivHomeText().contains("Home Component"));
        softAssert.assertTrue(homePage.isDivHomeDisplayed());
        softAssert.assertAll();
    }
}
