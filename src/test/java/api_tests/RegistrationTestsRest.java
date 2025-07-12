package api_tests;

import dto.ErrorMessageDto;
import dto.UserDto;
import io.restassured.response.Response;
import manager.AuthentificationController;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import utils.TestNGListner;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static utils.PropertiesReader.getProperty;
import static utils.RandomUtils.generateEmail;
@Listeners(TestNGListner.class)

public class RegistrationTestsRest  extends AuthentificationController {
    SoftAssert softAssert = new SoftAssert();

    @Test(groups = "smoke")
    public void registrationPositiveTest_200() {
        UserDto user = UserDto.builder()
                .username(generateEmail(5))
                .password("Password123!")
                .build();
        logger.info("test data: " + user);
        Response response = requestRegLogin(user, REGISTRATION_URL);
        response.then().log().ifValidationFails()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("TokenDtoSchema.json"))
        ;
        System.out.println("Response status code: " + response.getStatusCode());
        System.out.println("Response body: " + response.getBody().asString());
        softAssert.assertEquals(response.getStatusCode(), 200);
        softAssert.assertAll();
        }
    @Test
    public void registrationNegativeTest_400_WOPassword() {
        UserDto user = UserDto.builder()
                .username(generateEmail(5))
                .build();
        logger.info("test data: " + user);
        Response response = requestRegLogin(user, REGISTRATION_URL);
        response.then().log().ifValidationFails()
                .statusCode(400)
                .body(matchesJsonSchemaInClasspath("ErrorMessageDtoSchema.json"))
        ;
        System.out.println(response.getStatusLine());
        ErrorMessageDto errorMessageDto =response.getBody().as(ErrorMessageDto.class);
        System.out.println("Error message: " + errorMessageDto.getMessage());
        softAssert.assertEquals(errorMessageDto.getError(), "Bad Request");
        softAssert.assertTrue(errorMessageDto.getMessage().toString().contains("password=must not be blank"),
                "{password=must not be blank}");
        softAssert.assertEquals(response.getStatusCode(), 400);
        softAssert.assertAll();
    }
    @Test
    public void registrationNegativeTest_400_WOEmail() {
        UserDto user = UserDto.builder()
                .password("Password123!")
                .build();
        logger.info("test data: " + user);
        Response response = requestRegLogin(user, REGISTRATION_URL);
        response.then().log().ifValidationFails()
                .statusCode(400)
                .body(matchesJsonSchemaInClasspath("ErrorMessageDtoSchema.json"))
        ;
        System.out.println(response.getStatusLine());
        ErrorMessageDto errorMessageDto =response.getBody().as(ErrorMessageDto.class);
        System.out.println("Error message: " + errorMessageDto.getMessage());
        softAssert.assertEquals(errorMessageDto.getError(), "Bad Request");
        softAssert.assertTrue(errorMessageDto.getMessage().toString().contains("{username=must not be blank}"));
        softAssert.assertEquals(response.getStatusCode(), 400);
        softAssert.assertAll();
    }
    //this test fails due to site allowing invalid email formats - invalid@Email
    @Test(enabled = false)
    public void registrationNegativeTest_400_invalidEmail3() {
        UserDto user = UserDto.builder()
                .username("invalid@Email")
                .password("Password123!")
                .build();
        logger.info("test data: " + user);
        Response response = requestRegLogin(user, REGISTRATION_URL);
        response.then().log().ifValidationFails()
                .statusCode(400)
                .body(matchesJsonSchemaInClasspath("ErrorMessageDtoSchema.json"))
        ;
        System.out.println(response.getStatusLine());
        ErrorMessageDto errorMessageDto =response.getBody().as(ErrorMessageDto.class);
        System.out.println("Error message: " + errorMessageDto.getMessage());
        softAssert.assertEquals(errorMessageDto.getError(), "Bad Request");
        softAssert.assertEquals(errorMessageDto.getMessage().toString(),
                "{username=must be a well-formed email address}");
        softAssert.assertEquals(response.getStatusCode(), 400);
        softAssert.assertAll();
    }
    @Test
    public void registrationNegativeTest_400_invalidEmail2() {
        UserDto user = UserDto.builder()
                .username("invalidEmail@")
                .password("Password123!")
                .build();
        logger.info("test data: " + user);
        Response response = requestRegLogin(user, REGISTRATION_URL);
        response.then().log().ifValidationFails()
                .statusCode(400)
                .body(matchesJsonSchemaInClasspath("ErrorMessageDtoSchema.json"))
        ;
        System.out.println(response.getStatusLine());
        ErrorMessageDto errorMessageDto =response.getBody().as(ErrorMessageDto.class);
        System.out.println("Error message: " + errorMessageDto.getMessage());
        softAssert.assertEquals(errorMessageDto.getError(), "Bad Request");
        softAssert.assertEquals(errorMessageDto.getMessage().toString(),
                "{username=must be a well-formed email address}");
        softAssert.assertEquals(response.getStatusCode(), 400);
        softAssert.assertAll();
    }
    @Test
    public void registrationNegativeTest_400_invalidEmail1() {
        UserDto user = UserDto.builder()
                .username("@invalidEmail.com")
                .password("Password123!")
                .build();
        logger.info("test data: " + user);
        Response response = requestRegLogin(user, REGISTRATION_URL);
        response.then().log().ifValidationFails()
                .statusCode(400)
                .body(matchesJsonSchemaInClasspath("ErrorMessageDtoSchema.json"))
        ;
        System.out.println(response.getStatusLine());
        ErrorMessageDto errorMessageDto =response.getBody().as(ErrorMessageDto.class);
        System.out.println("Error message: " + errorMessageDto.getMessage());
        softAssert.assertEquals(errorMessageDto.getError(), "Bad Request");
        softAssert.assertEquals(errorMessageDto.getMessage().toString(),
                "{username=must be a well-formed email address}");
        softAssert.assertEquals(response.getStatusCode(), 400);
        softAssert.assertAll();
    }
    @Test
    public void registrationNegativeTest_400_invalidPassword_tooShort() {
        UserDto user = UserDto.builder()
                .username(generateEmail(5))
                .password("P123!")
                .build();
        logger.info("test data: " + user);
        Response response = requestRegLogin(user, REGISTRATION_URL);
        response.then().log().ifValidationFails()
                .statusCode(400)
                .body(matchesJsonSchemaInClasspath("ErrorMessageDtoSchema.json"))
        ;
        System.out.println(response.getStatusLine());
        ErrorMessageDto errorMessageDto =response.getBody().as(ErrorMessageDto.class);
        System.out.println("Error message: " + errorMessageDto.getMessage());
        softAssert.assertEquals(errorMessageDto.getError(), "Bad Request");
        softAssert.assertEquals(errorMessageDto.getMessage().toString(),
                "{password= At least 8 characters; Must contain at least 1 uppercase letter," +
                        " 1 lowercase letter, and 1 number; Can contain special characters [@$#^&*!]}");
        softAssert.assertEquals(response.getStatusCode(), 400);
        softAssert.assertAll();
    }
    @Test
    public void registrationNegativeTest_400_invalidPassword_lowerCase() {
        UserDto user = UserDto.builder()
                .username(generateEmail(5))
                .password("password123!")
                .build();
        logger.info("test data: " + user);
        Response response = requestRegLogin(user, REGISTRATION_URL);
        response.then().log().ifValidationFails()
                .statusCode(400)
                .body(matchesJsonSchemaInClasspath("ErrorMessageDtoSchema.json"))
        ;
        System.out.println(response.getStatusLine());
        ErrorMessageDto errorMessageDto =response.getBody().as(ErrorMessageDto.class);
        System.out.println("Error message: " + errorMessageDto.getMessage());
        softAssert.assertEquals(errorMessageDto.getError(), "Bad Request");
        softAssert.assertEquals(errorMessageDto.getMessage().toString(),
                "{password= At least 8 characters; Must contain at least 1 uppercase letter," +
                        " 1 lowercase letter, and 1 number; Can contain special characters [@$#^&*!]}");
        softAssert.assertEquals(response.getStatusCode(), 400);
        softAssert.assertAll();
    }
    @Test
    public void registrationNegativeTest_400_invalidPassword_upperCase() {
        UserDto user = UserDto.builder()
                .username(generateEmail(5))
                .password("PASSWORD123!")
                .build();
        logger.info("test data: " + user);
        Response response = requestRegLogin(user, REGISTRATION_URL);
        response.then().log().ifValidationFails()
                .statusCode(400)
                .body(matchesJsonSchemaInClasspath("ErrorMessageDtoSchema.json"))
        ;
        System.out.println(response.getStatusLine());
        ErrorMessageDto errorMessageDto =response.getBody().as(ErrorMessageDto.class);
        System.out.println("Error message: " + errorMessageDto.getMessage());
        softAssert.assertEquals(errorMessageDto.getError(), "Bad Request");
        softAssert.assertEquals(errorMessageDto.getMessage().toString(),
                "{password= At least 8 characters; Must contain at least 1 uppercase letter," +
                        " 1 lowercase letter, and 1 number; Can contain special characters [@$#^&*!]}");
        softAssert.assertEquals(response.getStatusCode(), 400);
        softAssert.assertAll();
    }
    @Test
    public void registrationNegativeTest_400_invalidPassword_noNumbers() {
        UserDto user = UserDto.builder()
                .username(generateEmail(5))
                .password("Passwordxxx!")
                .build();
        logger.info("test data: " + user);
        Response response = requestRegLogin(user, REGISTRATION_URL);
        response.then().log().ifValidationFails()
                .statusCode(400)
                .body(matchesJsonSchemaInClasspath("ErrorMessageDtoSchema.json"))
        ;
        System.out.println(response.getStatusLine());
        ErrorMessageDto errorMessageDto =response.getBody().as(ErrorMessageDto.class);
        System.out.println("Error message: " + errorMessageDto.getMessage());
        softAssert.assertEquals(errorMessageDto.getError(), "Bad Request");
        softAssert.assertEquals(errorMessageDto.getMessage().toString(),
                "{password= At least 8 characters; Must contain at least 1 uppercase letter," +
                        " 1 lowercase letter, and 1 number; Can contain special characters [@$#^&*!]}");
        softAssert.assertEquals(response.getStatusCode(), 400);
        softAssert.assertAll();
    }
    @Test
    public void registrationNegativeTest_409() {
        UserDto user = UserDto.builder()
                .username(getProperty("loginPB.properties", "email"))
                .password("Password123!")
                .build();
        logger.info("test data: " + user);
        Response response = requestRegLogin(user, REGISTRATION_URL);
        response.then().log().ifValidationFails()
                .statusCode(409)
                .body(matchesJsonSchemaInClasspath("ErrorMessageDtoSchema.json"))
        ;
        ErrorMessageDto errorMessageDto =response.getBody().as(ErrorMessageDto.class);
        System.out.println("Error message: " + errorMessageDto.getMessage());
        softAssert.assertEquals(errorMessageDto.getError(), "Conflict");
        softAssert.assertEquals(errorMessageDto.getMessage(),
                "User already exists");
        softAssert.assertEquals(response.getStatusCode(), 409);
        softAssert.assertAll();
        }


    }

