package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;


public class AboutPage extends BasePage {
    public AboutPage(WebDriver driver) {
        setDriver(driver);
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 10), this);
    }
    @FindBy(xpath = "//div[@class='about_main__2Uv5W']")
    WebElement divAboutText;

    public String getDivAboutText() {
        return divAboutText.getText();
    }
    public boolean isDivAboutDisplayed() {
        return divAboutText.isDisplayed();
    }
}
