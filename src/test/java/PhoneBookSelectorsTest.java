import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;

import java.time.Duration;

public class PhoneBookSelectorsTest {
    WebDriver driver = new ChromeDriver();
    // Test data for login/registration
    // input your own login credentials or use this
    String testEmail = "mail@mail.mail";
    String testPassword = "123456Q$qqq";

    @Test
    public void phoneBookTest() {
        driver.get("https://telranedu.web.app/home");
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        pause(2);

        //navigate to about page
        WebElement navBtnAbout = driver.findElement(By.linkText("ABOUT"));
        navBtnAbout.click();
        pause(2);

        //navigate to login page
        WebElement navBtnLogin = driver.findElement(By.linkText("LOGIN"));
        navBtnLogin.click();
        pause(2);

        //enter the email and password
        WebElement inputEmail = driver.findElement(By.name("email"));
        inputEmail.sendKeys(testEmail);
        WebElement inputPassword = driver.findElement(By.name("password"));
        inputPassword.sendKeys(testPassword);
        pause(3);

        //click on login button
        WebElement btnLogin = driver.findElement(By.name("login"));
        btnLogin.click();

        //or click registration button - uncomment if needed
//       WebElement btnRegister = driver.findElement(By.name("registration"));
//       pause(2);
//       btnRegister.click();
       pause(3);

        //click on sign out button - uncomment if needed
//        WebElement btnSignOut = driver.findElement(By.tagName("Button"));
//        new Actions(driver)
//                .click(btnSignOut)
//                .perform();

        //add contact
        //navigate to add contact page
        WebElement navBtnAddContact = driver.findElement(By.linkText("ADD"));
        navBtnAddContact.click();

        //test contact data
        String name = "TestName";
        String lastName = "TestLastName";
        String phone = "1234567890";
        String email = "someTestMail@mail.com";
        String address = "TestAddress";
        String description = "TestDescription"; // optional

        //input  contact fields
        WebElement inputName = driver.findElement(By.cssSelector("input[placeholder='Name']"));
        inputName.sendKeys(name);
        pause(1);
        WebElement inputLastName = driver.findElement(By.cssSelector("input[placeholder='Last Name']"));
        inputLastName.sendKeys(lastName);
        pause(1);
        WebElement inputPhone = driver.findElement(By.cssSelector("input[placeholder='Phone']"));
        inputPhone.sendKeys(phone);
        pause(1);
        WebElement inputContactEmail = driver.findElement(By.cssSelector("input[placeholder='email']"));
        inputContactEmail.sendKeys(email);
        pause(1);
        WebElement inputAddress = driver.findElement(By.cssSelector("input[placeholder='Address']"));
        inputAddress.sendKeys(address);
        pause(3);

        //click on save button
        WebElement saveBtn = driver.findElement(By.tagName("b"));
        new Actions(driver)
                .click(saveBtn)
                .perform();
        pause(3);

        //navigate to contacts page
        WebElement navBtnContacts = driver.findElement(By.linkText("CONTACTS"));
        navBtnContacts.click();
        pause(3);

        //open contact details
        WebElement contactDetails = driver.findElement(By.tagName("h2"));
        new Actions(driver)
                .click(contactDetails)
                .perform();
        pause(3);
        //quit the driver
        driver.quit();
    }


    public static void pause(int seconds) {
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
