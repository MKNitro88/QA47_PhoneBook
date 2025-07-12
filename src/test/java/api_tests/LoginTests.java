package api_tests;

import dto.ErrorMessageDto;
import dto.TokenDto;
import dto.UserDto;
import io.restassured.response.Response;
import manager.AuthentificationController;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import utils.TestNGListner;

import java.time.LocalDate;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static utils.PropertiesReader.getProperty;

@Listeners(TestNGListner.class)

public class LoginTests extends AuthentificationController {
    String validUserName = getProperty("loginPB.properties","email");
    String validPassword = getProperty("loginPB.properties","password");
    SoftAssert softAssert = new SoftAssert();

    @Test(groups = "smoke")
    public void loginPositiveTest_200() {
        UserDto user = UserDto.builder()
                .username(validUserName)
                .password(validPassword)
                .build();
        logger.info("test data: " + user);
        Response response = requestRegLogin(user, LOGIN_URL);
        response.then().log().ifValidationFails()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("TokenDtoSchema.json"))
        ;
        System.out.println(response.getStatusLine());
        System.out.println("Response body: " + response.getBody().asString());
        softAssert.assertEquals(response.getStatusCode(), 200);
        TokenDto tokenDto = response.as(TokenDto.class);
        System.out.println("Token: " + tokenDto.getToken());
        softAssert.assertNotNull(tokenDto.getToken(), "Token is null");
        softAssert.assertTrue(tokenDto.toString().contains("token"));
        softAssert.assertAll();
    }
    @Test
    public void loginNegativeTest_401_invalidPassword() {
        UserDto user = UserDto.builder()
                .username(validUserName)
                .password("wrongPassword")
                .build();
        logger.info("test data: " + user);
        Response response = requestRegLogin(user, LOGIN_URL);
        response.then().log().ifValidationFails()
                .statusCode(401)
                .body(matchesJsonSchemaInClasspath("ErrorMessageDtoSchema.json"))
        ;
        System.out.println("Response status code: " + response.getStatusCode());
        System.out.println("Response body: " + response.getBody().asString());
        ErrorMessageDto errorMessageDto =response.getBody().as(ErrorMessageDto.class);
        softAssert.assertEquals(errorMessageDto.getTimestamp().substring(0,10), LocalDate.now().toString());
        softAssert.assertTrue(errorMessageDto.getMessage().toString().contains("Login or Password incorrect"));
        softAssert.assertTrue(errorMessageDto.getError().equals("Unauthorized"));
        softAssert.assertEquals(response.getStatusCode(), 401);
        softAssert.assertAll();
    }

    @Test
    public void loginNegativeTest_401_invalidEmail() {
        UserDto user = UserDto.builder()
                .username("invalidEmail")
                .password(validPassword)
                .build();
        logger.info("test data: " + user);
        Response response = requestRegLogin(user, LOGIN_URL);
        response.then().log().ifValidationFails()
                .statusCode(401)
                .body(matchesJsonSchemaInClasspath("ErrorMessageDtoSchema.json"))
        ;
        System.out.println("Response status code: " + response.getStatusCode());
        System.out.println("Response body: " + response.getBody().asString());
        ErrorMessageDto errorMessageDto =response.getBody().as(ErrorMessageDto.class);
        softAssert.assertEquals(errorMessageDto.getTimestamp().substring(0,10), LocalDate.now().toString());
        softAssert.assertTrue(errorMessageDto.getMessage().toString().contains("Login or Password incorrect"));
        softAssert.assertTrue(errorMessageDto.getError().equals("Unauthorized"));
        softAssert.assertEquals(response.getStatusCode(), 401);
        softAssert.assertAll();
    }
    @Test
    public void loginNegativeTest_401_WOEmail() {
        UserDto user = UserDto.builder()
                .password(validPassword)
                .build();
        logger.info("test data: " + user);
        Response response = requestRegLogin(user, LOGIN_URL);
        response.then().log().ifValidationFails()
                .statusCode(401)
                .body(matchesJsonSchemaInClasspath("ErrorMessageDtoSchema.json"))
        ;
        System.out.println("Response status code: " + response.getStatusCode());
        System.out.println("Response body: " + response.getBody().asString());
        ErrorMessageDto errorMessageDto =response.getBody().as(ErrorMessageDto.class);
        softAssert.assertEquals(errorMessageDto.getTimestamp().substring(0,10), LocalDate.now().toString());
        softAssert.assertTrue(errorMessageDto.getMessage().toString().contains("Login or Password incorrect"));
        softAssert.assertTrue(errorMessageDto.getError().equals("Unauthorized"));
        softAssert.assertEquals(response.getStatusCode(), 401);
        softAssert.assertAll();
    }
    @Test
    public void loginNegativeTest_401_WOPassword() {
        UserDto user = UserDto.builder()
                .username(validUserName)
                .build();
        logger.info("test data: " + user);
        Response response = requestRegLogin(user, LOGIN_URL);
        response.then().log().ifValidationFails()
                .statusCode(401)
                .body(matchesJsonSchemaInClasspath("ErrorMessageDtoSchema.json"))
        ;
        System.out.println("Response status code: " + response.getStatusCode());
        System.out.println("Response body: " + response.getBody().asString());
        ErrorMessageDto errorMessageDto =response.getBody().as(ErrorMessageDto.class);
        softAssert.assertEquals(errorMessageDto.getTimestamp().substring(0,10), LocalDate.now().toString());
        softAssert.assertTrue(errorMessageDto.getMessage().toString().contains("Login or Password incorrect"));
        softAssert.assertTrue(errorMessageDto.getError().equals("Unauthorized"));
        softAssert.assertEquals(response.getStatusCode(), 401);
        softAssert.assertAll();
    }

}
