package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import utils.PropertiesReader.*;

import static utils.PropertiesReader.getProperty;


public class HomePage extends BasePage {
    public HomePage(WebDriver driver)  {
        setDriver(driver);
//        driver.get("https://telranedu.web.app/home");
        driver.get(getProperty("loginPB.properties", "url"));
        PageFactory.initElements(new AjaxElementLocatorFactory(driver,10), this);
    }
    @FindBy(xpath = "//a[text()='HOME']")
    WebElement btnHomeHeader;
    @FindBy(xpath = "//a[text()='ABOUT']")
    WebElement btnAboutHeader;
    @FindBy(xpath = "//a[text()='LOGIN']")
    WebElement btnLoginHeader;
    @FindBy(xpath = "//a[text()='CONTACTS']")
    WebElement btnContactsHeader;
    @FindBy(xpath = "//a[text()='ADD']")
    WebElement btnAddHeader;
    @FindBy(xpath = "//button[text()='Sign Out']")
    WebElement btnSignOutHeader;
    @FindBy(xpath = "//*[@id='root']/div[2]/div[1]/div[1]")
    WebElement divHomeText;

    public void clickBtnHomeHeader() {
        btnHomeHeader.click();
    }
    public void clickBtnAboutHeader() {
        btnAboutHeader.click();
    }
    public void clickBtnLoginHeader() {
        btnLoginHeader.click();
    }
    public void clickBtnContactsHeader() {
        btnContactsHeader.click();
    }
    public void clickBtnAddHeader() {
        btnAddHeader.click();
    }
    public void clickBtnSignOutHeader() {
        btnSignOutHeader.click();
    }
    public String getDivHomeText() {
        return divHomeText.getText();
    }
    public boolean isBtnSignOutHeaderDisplayed() {
        return btnSignOutHeader.isDisplayed();
    }
    public boolean isDivHomeDisplayed() {
        return divHomeText.isDisplayed();

    }
    public boolean isBtnContactsHeaderDisplayed() {
        return btnContactsHeader.isDisplayed();
    }

}
