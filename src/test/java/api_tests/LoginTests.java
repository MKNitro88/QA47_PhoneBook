package api_tests;

import dto.UserLombok;
import io.restassured.response.Response;
import manager.AuthentificationController;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import utils.TestNGListner;

import static utils.PropertiesReader.getProperty;

@Listeners(TestNGListner.class)

public class LoginTests extends AuthentificationController {
    String validUserName = getProperty("loginPB.properties","email");
    String validPassword = getProperty("loginPB.properties","password");

    @Test
    public void loginPositiveTest_200() {
        UserLombok user = UserLombok.builder()
                .username(validUserName)
                .password(validPassword)
                .build();
        logger.info("test data: " + user);
        Response response = requestRegLogin(user, LOGIN_URL);
        System.out.println("Response status code: " + response.getStatusCode());
        System.out.println("Response body: " + response.getBody().asString());
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertTrue(validateErrorMsg(response, "token"), "Token not found in response");
    }
    @Test
    public void loginNegativeTest_401_invalidPassword() {
        UserLombok user = UserLombok.builder()
                .username(validUserName)
                .password("wrongPassword")
                .build();
        logger.info("test data: " + user);
        Response response = requestRegLogin(user, LOGIN_URL);
        System.out.println("Response status code: " + response.getStatusCode());
        System.out.println("Response body: " + response.getBody().asString());
        Assert.assertEquals(response.getStatusCode(), 401);
        Assert.assertTrue(validateErrorMsg(response, "Login or Password incorrect"), "Error message not found in response");
    }
    @Test
    public void loginNegativeTest_401_invalidEmail() {
        UserLombok user = UserLombok.builder()
                .username("invalidEmail")
                .password(validPassword)
                .build();
        logger.info("test data: " + user);
        Response response = requestRegLogin(user, LOGIN_URL);
        System.out.println("Response status code: " + response.getStatusCode());
        System.out.println("Response body: " + response.getBody().asString());
        Assert.assertEquals(response.getStatusCode(), 401);
        Assert.assertTrue(validateErrorMsg(response, "Login or Password incorrect"), "Error message not found in response");
    }

}
