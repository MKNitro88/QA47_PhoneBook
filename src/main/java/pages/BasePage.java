package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class BasePage {
    static WebDriver driver;

    public static void setDriver(WebDriver wd) {
        driver = wd;
    }

    public static void pause(int seconds) {
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isElementPreseant(WebElement element){
        return element.isDisplayed();
    }
    public boolean isTextElementPreseant(WebElement element, String text) {
        return element.getText().contains(text);
    }
    public boolean isAlertTextContains(String text){
        try {
            return driver.switchTo().alert().getText().contains(text);
        } catch (Exception e) {
            return false;
        }
    }
}
