import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;

import java.time.Duration;

public class PhoneBookIlCarroXPath {

    @Test
    public void xpathTest() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://telranedu.web.app/home");
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        WebElement div = driver.findElement(By.xpath("//div[@id='root']"));
        System.out.println("Div text: " + div.getText());
        WebElement btnAbout = driver.findElement(By.xpath("//a[text()='ABOUT']"));
        System.out.println(btnAbout.getAttribute("href"));

        WebElement btnLogin = driver.findElement(By.xpath("//a[@href='/login']"));
        btnLogin.click();
        WebElement inputEmail = driver.findElement(By.xpath("//input[@name='email']"));
        inputEmail.sendKeys("test1@test1.test");
        WebElement inputPassword = driver.findElement(By.xpath("//input[@name='password']"));
        inputPassword.sendKeys("Qq@123456");
        PhoneBookSelectorsTest.pause(2);
        WebElement btnRegSubmit = driver.findElement(By.xpath("//button[@name='registration']"));
        btnRegSubmit.click();

    driver.quit();

    }
    @Test
    public void iLCarroXpath(){
        WebDriver driver = new ChromeDriver();
        driver.get("https://ilcarro.web.app/");
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        WebElement btnSignUp = driver.findElement(By.xpath("//a[text()=' Sign up ']"));
        btnSignUp.click();
        PhoneBookSelectorsTest.pause(2);

        WebElement inputName = driver.findElement(By.xpath("//input[@id='name']"));
        inputName.sendKeys("TestNameMKII");
        WebElement inputLastName = driver.findElement(By.xpath("//input[@id='lastName']"));
        inputLastName.sendKeys("TestLastNameMKII");
        WebElement inputEmail = driver.findElement(By.xpath("//input[@id='email']"));
        inputEmail.sendKeys("test_mkii@gmail.com");
        WebElement inputPassword = driver.findElement(By.xpath("//input[@id='password']"));
        inputPassword.sendKeys("@Password123");
        WebElement checkboxTOS = driver.findElement(By.xpath("//input[@id='terms-of-use']"));
        //checkboxTOS.click();
        new Actions(driver).click(checkboxTOS).perform();
        WebElement btnYalla = driver.findElement(By.xpath("//button[@type='submit']"));
        btnYalla.click();
        PhoneBookSelectorsTest.pause(5);
        driver.quit();
    }
    //HomeWork - Automate il carro site with xpath
}