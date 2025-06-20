package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.HeaderMenuItem;

import java.time.Duration;

public class BasePage {
    static WebDriver driver;
    public Logger logger = LoggerFactory.getLogger(BasePage.class);
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

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(By.xpath(headerMenuItem.getLocator())))
                .click();
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
    public boolean isButtonClickable(WebElement element) {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(5))
                    .until(ExpectedConditions.elementToBeClickable(element));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    public boolean validateUrl(String url) {
        return new WebDriverWait(driver,Duration.ofSeconds(5))
                .until(ExpectedConditions.urlContains(url));
    }
    public boolean urlNotContains(String url) {
        return new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.not(ExpectedConditions.urlContains(url)));
    }

}

