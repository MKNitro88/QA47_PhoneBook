package api_tests;

import dto.UserLombok;
import io.restassured.response.Response;
import manager.AuthentificationController;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import utils.TestNGListner;

import static utils.PropertiesReader.getProperty;
import static utils.RandomUtils.generateEmail;
@Listeners(TestNGListner.class)

public class RegistrationTestsRest  extends AuthentificationController {

    @Test
    public void registrationPositiveTest_200() {
        UserLombok user = UserLombok.builder()
                .username(generateEmail(5))
                .password("Password123!")
                .build();
        logger.info("test data: " + user);
        Response response = requestRegLogin(user, REGISTRATION_URL);
        System.out.println("Response status code: " + response.getStatusCode());
        System.out.println("Response body: " + response.getBody().asString());
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertTrue(validateErrorMsg(response,"token"));
        }
    @Test
    public void registrationNegativeTest_400_invalidEmail() {
        UserLombok user = UserLombok.builder()
                .username("invalidEmail")
                .password("Password123!")
                .build();
        logger.info("test data: " + user);
        Response response = requestRegLogin(user, REGISTRATION_URL);
        System.out.println("Response status code: " + response.getStatusCode());
        System.out.println("Response body: " + response.getBody().asString());
        Assert.assertEquals(response.getStatusCode(), 400);
        Assert.assertTrue(validateErrorMsg(response,"must be a well-formed email address"));
    }
    @Test
    public void registrationNegativeTest_400_invalidPassword_tooShort() {
        UserLombok user = UserLombok.builder()
                .username(generateEmail(5))
                .password("P123!")
                .build();
        logger.info("test data: " + user);
        Response response = requestRegLogin(user, REGISTRATION_URL);
        System.out.println("Response status code: " + response.getStatusCode());
        System.out.println("Response body: " + response.getBody().asString());
        Assert.assertEquals(response.getStatusCode(), 400);
        Assert.assertTrue(validateErrorMsg(response,"At least 8 characters"));
    }
    @Test
    public void registrationNegativeTest_400_invalidPassword_lowerCase() {
        UserLombok user = UserLombok.builder()
                .username(generateEmail(5))
                .password("password123!")
                .build();
        logger.info("test data: " + user);
        Response response = requestRegLogin(user, REGISTRATION_URL);
        System.out.println("Response status code: " + response.getStatusCode());
        System.out.println("Response body: " + response.getBody().asString());
        Assert.assertEquals(response.getStatusCode(), 400);
        Assert.assertTrue(validateErrorMsg(response,"contain at least 1 uppercase letter"));
    }
    @Test
    public void registrationNegativeTest_400_invalidPassword_upperCase() {
        UserLombok user = UserLombok.builder()
                .username(generateEmail(5))
                .password("PASSWORD123!")
                .build();
        logger.info("test data: " + user);
        Response response = requestRegLogin(user, REGISTRATION_URL);
        System.out.println("Response status code: " + response.getStatusCode());
        System.out.println("Response body: " + response.getBody().asString());
        Assert.assertEquals(response.getStatusCode(), 400);
        Assert.assertTrue(validateErrorMsg(response,"1 lowercase letter"));
    }
    @Test
    public void registrationNegativeTest_400_invalidPassword_noNumbers() {
        UserLombok user = UserLombok.builder()
                .username(generateEmail(5))
                .password("Passwordxxx!")
                .build();
        logger.info("test data: " + user);
        Response response = requestRegLogin(user, REGISTRATION_URL);
        System.out.println("Response status code: " + response.getStatusCode());
        System.out.println("Response body: " + response.getBody().asString());
        Assert.assertEquals(response.getStatusCode(), 400);
        Assert.assertTrue(validateErrorMsg(response,"and 1 number"));
    }
    @Test
    public void registrationNegativeTest_409() {
        UserLombok user = UserLombok.builder()
                .username(getProperty("login.properties", "email"))
                .password("Password123!")
                .build();
        logger.info("test data: " + user);
        Response response = requestRegLogin(user, REGISTRATION_URL);
        System.out.println("Response status code: " + response.getStatusCode());
        Assert.assertEquals(response.getStatusCode(), 409);
        }


    }

