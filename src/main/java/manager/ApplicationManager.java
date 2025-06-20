package manager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.time.Duration;
import java.time.LocalDate;

public class ApplicationManager {
    private WebDriver driver;
    public WebDriver getDriver() {
        return driver;
    }

    public Logger logger = LoggerFactory.getLogger(ApplicationManager.class);


    @BeforeMethod
    public void setUp() {
        logger.info("Start test --> " + LocalDate.now());
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }
    @AfterMethod
    public void tearDown() {
        logger.info("Stop test --> " + LocalDate.now());
        if (driver != null) {
            driver.quit();
        }


    }
}
