package manager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.events.EventFiringDecorator;
import org.openqa.selenium.support.events.WebDriverListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import utils.WDListner;

import java.time.Duration;




public class ApplicationManager {
    static String browser = System.getProperty("browser", "chrome");
    public Logger logger = LoggerFactory.getLogger(ApplicationManager.class);

    public ApplicationManager(){}

    private WebDriver driver;

    public WebDriver getDriver() {
        return driver;
    }




    @BeforeMethod(alwaysRun = true)
    @Parameters("browser")
    public void setUp(@Optional("chrome") String browser) {
        //logger.info("Start test --> " + LocalDate.now());
        //driver = new ChromeDriver();
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--headless");
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        firefoxOptions.addArguments("--headless");
        EdgeOptions edgeOptions = new EdgeOptions();
        edgeOptions.addArguments("--headless");
        switch(browser.toLowerCase()){
            case "chrome":
                System.setProperty("webdriver.chrome.driver", "c:/tools/chromedriver.exe");
                driver = new ChromeDriver(chromeOptions);
                logger.info("running tests in Chrome browser");
                break;
            case "firefox":
                System.setProperty("webdriver.geckodriver.driver", "c:/tools/geckodriver.exe");
                driver = new FirefoxDriver();
                logger.info("running tests in Firefox browser");
                break;
            case "edge":
                System.setProperty("webdriver.edge.driver", "c:/tools/msedgedriver.exe");
                driver = new EdgeDriver();
                logger.info("running tests in Edge browser");
                break;
            default:
                throw new RuntimeException("Browser is not supported: " + browser);
        }
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        WebDriverListener webDriverListner = new WDListner();
        driver = new EventFiringDecorator<>(webDriverListner).decorate(driver);
    }
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        //logger.info("Stop test --> " + LocalDate.now());
        if (driver != null) {
            driver.quit();
        }


    }
}
