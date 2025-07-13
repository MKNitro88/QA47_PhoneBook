package experiments;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;

import static utils.PropertiesReader.getProperty;

public class ParametersTests {
    private WebDriver driver;

    @BeforeClass(alwaysRun = true)
    @Parameters("browser")
    public void openPage(@Optional("chrome") String browser){
        switch(browser.toLowerCase()){
            case "chrome":
                System.setProperty("webdriver.chrome.driver", "c:/tools/chromedriver.exe");
                driver = new ChromeDriver();
                break;
            case "firefox":
                System.setProperty("webdriver.geckodriver.driver", "c:/tools/geckodriver.exe");
                driver = new FirefoxDriver();
                break;
            case "edge":
                System.setProperty("webdriver.edge.driver", "c:/tools/msedgedriver.exe");
                driver = new EdgeDriver();
                break;
            default:
                throw new RuntimeException("Browser is not supported: " + browser);
        }
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get(getProperty("loginPB.properties", "url"));
    }
    @Test
    @Parameters({"value1", "value2"})
    public void test1(@Optional("-1000") int v1,@Optional("1001") int v2) {
        System.out.println("v1 = " + v1 +"\n" + "v2 = " + v2);
        int result = v1 + v2;
        Assert.assertTrue(result > 0);

    }
    @AfterClass
    public void closeBrowser() {
        if (driver != null) {
            driver.quit();
        }
    }
}
