package api_tests;

import dto.UserLombok;
import io.restassured.response.Response;
import manager.AuthentificationController;
import org.testng.Assert;
import org.testng.annotations.Test;

import static utils.PropertiesReader.getProperty;
import static utils.RandomUtils.generateEmail;


public class RegistrationTestsRest  extends AuthentificationController {

    @Test
    public void registrationPositiveTest_200() {
        UserLombok user = UserLombok.builder()
                .username(generateEmail(5))
                .password("Password123!")
                .build();
        Response response = requestRegLogin(user, REGISTRATION_URL);
        System.out.println("Response status code: " + response.getStatusCode());
        System.out.println("===========" +response.headers() + "===========\n");
        System.out.println("===========" +response.body().prettyPrint()+ "===========");
        Assert.assertEquals(response.getStatusCode(), 200);
        }
    @Test
    public void registrationNegativeTest_400() {
        UserLombok user = UserLombok.builder()
                .username("invalidEmail")
                .password("short")
                .build();
        Response response = requestRegLogin(user, REGISTRATION_URL);
        System.out.println("Response status code: " + response.getStatusCode());
        Assert.assertEquals(response.getStatusCode(), 400);
    }
    @Test
    public void registrationNegativeTest_409() {
        UserLombok user = UserLombok.builder()
                .username(getProperty("login.properties", "email"))
                .password("Password123!")
                .build();
        Response response = requestRegLogin(user, REGISTRATION_URL);
        System.out.println("Response status code: " + response.getStatusCode());
        Assert.assertEquals(response.getStatusCode(), 409);
        }


    }

