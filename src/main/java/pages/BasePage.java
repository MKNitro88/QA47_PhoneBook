package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.HeaderMenuItem;

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
    public static <T extends BasePage> T clickButtonsOnHeader(HeaderMenuItem headerMenuItem) {
        WebElement element = driver.findElement(By.xpath(headerMenuItem.getLocator()));
        element.click();
        switch (headerMenuItem) {
            case HOME -> {
                return (T) new HomePage(driver);
            }
            case ABOUT -> {
                return (T) new AboutPage(driver);
            }
            case CONTACTS -> {
                return (T) new ContactPage(driver);
            }
            case ADD -> {
                return (T) new AddPage(driver);
            }
            case LOGIN -> {
                return (T) new LoginPage(driver);
            }
            case SIGN_OUT -> {
                return (T) new LoginPage(driver);
            }
            default -> throw new IllegalArgumentException("Invalid parameter headerMenuItem");
        }
    }

    public boolean isElementPreseant(WebElement element){
        return element.isDisplayed();
    }
    public boolean isTextElementPreseant(WebElement element, String text) {
        return element.getText().contains(text);
    }
    public boolean isAlertTextContains(String text){
            return driver.switchTo().alert().getText().contains(text);
    }
}
