package manager;

import dto.ContactDto;
import dto.TokenDto;
import dto.UserDto;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import utils.BaseAPI;


import static io.restassured.RestAssured.given;
import static utils.PropertiesReader.*;

public class ContactController implements BaseAPI {

    String validUserName = getProperty("LoginPB.properties", "email");
    String validPassword = getProperty("LoginPB.properties", "password");
    protected  TokenDto tokenDto ;

    @BeforeSuite(alwaysRun = true)
    public void login() {
        UserDto user = UserDto.builder()
                .username(validUserName)
                .password(validPassword)
                .build();
        Response response =  new AuthentificationController().requestRegLogin(user, LOGIN_URL);
        System.out.println(response.getStatusLine());
        if(response.getStatusCode() == 200) {
            tokenDto = response.as(TokenDto.class);
            System.out.println("Login successful");
        } else {
            System.out.println("Login failed with status code: " + response.getStatusCode());
        }



    }
    protected Response addNewContact(ContactDto contact, TokenDto tokenDto) {
        return given()
                .log().all()
                .baseUri(getProperty("LoginPB.properties", "baseUri"))
                .header("Authorization", tokenDto.getToken())
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(contact)
                .when()
                .post(CONTACTS_URL)
                .thenReturn();
    }
    protected Response editContact(ContactDto contact, TokenDto tokenDto) {
        return given()
                .log().all()
                .baseUri(getProperty("LoginPB.properties", "baseUri"))
                .header("Authorization", tokenDto.getToken())
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(contact)
                .when()
                .put(CONTACTS_URL)
                .thenReturn();
    }
    public Response getAllContacts() {
        return given()
                .log().all()
                .baseUri(getProperty("LoginPB.properties", "baseUri"))
                .header("Authorization", tokenDto.getToken())
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .get(CONTACTS_URL)
                .thenReturn();
    }
    protected Response deleteContactById(ContactDto contact, TokenDto tokenDto) {
        return given()
                .log().all()
                .baseUri(getProperty("loginPB.properties", "baseUri"))
                .accept(ContentType.JSON)
                .header("Authorization", tokenDto.getToken())
                .delete(CONTACTS_URL+"/"+contact.getId())
                .thenReturn()
                ;
    }

}