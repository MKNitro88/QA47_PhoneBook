package api_tests;

import dto.*;
import io.restassured.response.Response;
import manager.ContactController;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import utils.TestNGListner;


import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static utils.RandomUtils.generateEmail;
import static utils.RandomUtils.generateString;
@Listeners(TestNGListner.class)

public class AddContactTests extends ContactController {

    SoftAssert softAssert = new SoftAssert();
    ContactDto contact;
    int contactListSize;

    @BeforeMethod(alwaysRun = true)
    public void checkContactListSizeBeforeAdding(){
        ContactsDto contactList = getAllContacts().body().as(ContactsDto.class);
        contactListSize = contactList.getContactsCount();
        System.out.println("Contact list size before test: " + contactListSize);

    }

    @Test(groups = "smoke")
    public void addContactPositiveTest_200() {
        System.out.println(contactListSize);
        contact  = ContactDto.builder()
                .name("testName1_" + generateString(5))
                .lastName("testLastName1_" + generateString(5))
                .email(generateEmail(5))
                .phone("0500101010")
                .address("testAddress1_" + generateString(5))
                .description("testDescription1_" + generateString(5))
                .build();
        Response response = addNewContact(contact, tokenDto);
        response.then().log().ifValidationFails()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("ResponseMessageDtoSchema.json"))
        ;
        System.out.println("Response status code: " + response.getStatusCode());
        ResponseMessageDto msgDto = new ResponseMessageDto();
        if(response.getStatusCode() == 200){
            msgDto = response.getBody().as(ResponseMessageDto.class);
            contact.setId(msgDto.getMessage().split("ID: ")[1]);

        }
        System.out.println(msgDto.getMessage());
        softAssert.assertTrue(msgDto.getMessage().contains("Contact was added!"));
        ContactsDto contactList = getAllContacts().body().as(ContactsDto.class);
        softAssert.assertTrue(contactList.isContactInList(contact));
        softAssert.assertEquals(contactList.getContactsCount(), contactListSize + 1);
        softAssert.assertAll();

    }
    @Test
    public void addContactPositive_200_WOEmail() {
        System.out.println(contactListSize);
        ContactDto contact  = ContactDto.builder()
                .name("testName2_" + generateString(5))
                .lastName("testLastName2_" + generateString(5))
                .email(generateEmail(5))
                .address("testAddress2_" + generateString(5))
                .description("testDescription2_" + generateString(5))
                .build();
        Response response = addNewContact(contact, tokenDto);
        response.then().log().ifValidationFails()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("ResponseMessageDtoSchema.json"))
        ;
        System.out.println("Response status code: " + response.getStatusCode());
        ResponseMessageDto responseMessageDto = response.getBody().as(ResponseMessageDto.class);
        softAssert.assertEquals(response.getStatusCode(), 200);
        System.out.println(responseMessageDto.getMessage());
        softAssert.assertTrue(responseMessageDto.getMessage().contains("Contact was added!"));
        ContactsDto contactList = getAllContacts().body().as(ContactsDto.class);
        softAssert.assertTrue(contactList.isContactInList(contact));
        System.out.println(contactListSize);
        softAssert.assertEquals(contactList.getContactsCount(), contactListSize + 1);
        softAssert.assertAll();
    }
    @Test
    public void addContactPositive_200_WOPhone() {
        ContactDto contact  = ContactDto.builder()
                .name("testName2_" + generateString(5))
                .lastName("testLastName2_" + generateString(5))
                .phone("0500101010")
                .address("testAddress2_" + generateString(5))
                .description("testDescription2_" + generateString(5))
                .build();
        Response response = addNewContact(contact, tokenDto);
        response.then().log().ifValidationFails()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("ResponseMessageDtoSchema.json"))
        ;
        System.out.println("Response status code: " + response.getStatusCode());
        ResponseMessageDto responseMessageDto = response.getBody().as(ResponseMessageDto.class);
        softAssert.assertEquals(response.getStatusCode(), 200);
        System.out.println(responseMessageDto.getMessage());
        softAssert.assertTrue(responseMessageDto.getMessage().contains("Contact was added!"));
        ContactsDto contactList = getAllContacts().body().as(ContactsDto.class);
        softAssert.assertTrue(contactList.isContactInList(contact));
        System.out.println(contactListSize);
        softAssert.assertEquals(contactList.getContactsCount(), contactListSize + 1);
        softAssert.assertAll();
    }

    @Test
    public void addContactNegativeTest_401_invalidToken() {
        ContactDto contact  = ContactDto.builder()
                .name("testName2_" + generateString(5))
                .lastName("testLastName2_" + generateString(5))
                .email(generateEmail(5))
                .phone("0500101010")
                .address("testAddress2_" + generateString(5))
                .description("testDescription2_" + generateString(5))
                .build();
        Response response = addNewContact(contact, TokenDto.builder()
                .token("invalidToken")
                .build());
        response.then().log().ifValidationFails()
                .statusCode(401)
                .body(matchesJsonSchemaInClasspath("ErrorMessageDtoSchema.json"))
        ;
        System.out.println("Response status code: " + response.getStatusCode());
        ErrorMessageDto errorMessageDto = response.getBody().as(ErrorMessageDto.class);
        softAssert.assertEquals(response.getStatusCode(), 401);
        System.out.println(errorMessageDto.getMessage().toString());
        softAssert.assertTrue(errorMessageDto.getError().equals("Unauthorized"));
        softAssert.assertTrue(errorMessageDto.getMessage().toString().contains("JWT strings must contain exactly 2 period characters."));
        ContactsDto contactList = getAllContacts().body().as(ContactsDto.class);
        softAssert.assertFalse(contactList.isContactInList(contact));
        softAssert.assertEquals(contactList.getContactsCount(), contactListSize);
        softAssert.assertAll();
    }
    @Test
    public void addContactNegativeTest_400_WOFirstName() {
        ContactDto contact  = ContactDto.builder()
                .lastName("testLastName2_" + generateString(5))
                .email(generateEmail(5))
                .phone("0500101010")
                .address("testAddress2_" + generateString(5))
                .description("testDescription2_" + generateString(5))
                .build();
        Response response = addNewContact(contact, tokenDto);
        response.then().log().ifValidationFails()
                .statusCode(400)
                .body(matchesJsonSchemaInClasspath("ErrorMessageDtoSchema.json"))
        ;
        System.out.println("Response status code: " + response.getStatusCode());
        ErrorMessageDto errorMessageDto = response.getBody().as(ErrorMessageDto.class);
        softAssert.assertEquals(response.getStatusCode(), 400);
        System.out.println(errorMessageDto.getMessage().toString());
        softAssert.assertTrue(errorMessageDto.getError().equals("Bad Request"));
        softAssert.assertTrue(errorMessageDto.getMessage().toString().contains("must not be blank"));
        ContactsDto contactList = getAllContacts().body().as(ContactsDto.class);
        softAssert.assertFalse(contactList.isContactInList(contact));
        softAssert.assertEquals(contactList.getContactsCount(), contactListSize);
        softAssert.assertAll();
    }
    @Test
    public void addContactNegativeTest_400_WOLastName() {
        ContactDto contact  = ContactDto.builder()
                .name("testName2_" + generateString(5))
                .email(generateEmail(5))
                .phone("0500101010")
                .address("testAddress2_" + generateString(5))
                .description("testDescription2_" + generateString(5))
                .build();
        Response response = addNewContact(contact, tokenDto);
        response.then().log().ifValidationFails()
                .statusCode(400)
                .body(matchesJsonSchemaInClasspath("ErrorMessageDtoSchema.json"))
        ;
        System.out.println("Response status code: " + response.getStatusCode());
        ErrorMessageDto errorMessageDto = response.getBody().as(ErrorMessageDto.class);
        softAssert.assertEquals(response.getStatusCode(), 400);
        System.out.println(errorMessageDto.getMessage().toString());
        softAssert.assertTrue(errorMessageDto.getError().equals("Bad Request"));
        softAssert.assertTrue(errorMessageDto.getMessage().toString().contains("must not be blank"));
        ContactsDto contactList = getAllContacts().body().as(ContactsDto.class);
        softAssert.assertFalse(contactList.isContactInList(contact));
        softAssert.assertEquals(contactList.getContactsCount(), contactListSize);
        softAssert.assertAll();
    }
    @Test
    public void addContactNegativeTest_400_WOAddress() {
        ContactDto contact  = ContactDto.builder()
                .name("testName1_" + generateString(5))
                .lastName("testLastName1_" + generateString(5))
                .email(generateEmail(5))
                .phone("0500101010")
                .description("testDescription1_" + generateString(5))
                .build();
        Response response = addNewContact(contact, tokenDto);
        response.then().log().ifValidationFails()
                .statusCode(400)
                .body(matchesJsonSchemaInClasspath("ErrorMessageDtoSchema.json"))
        ;
        System.out.println("Response status code: " + response.getStatusCode());
        ErrorMessageDto errorMessageDto = response.getBody().as(ErrorMessageDto.class);
        softAssert.assertEquals(response.getStatusCode(), 400);
        System.out.println(errorMessageDto.getMessage().toString());
        softAssert.assertTrue(errorMessageDto.getError().equals("Bad Request"));
        softAssert.assertTrue(errorMessageDto.getMessage().toString().contains("must not be blank"));
        ContactsDto contactList = getAllContacts().body().as(ContactsDto.class);
        softAssert.assertFalse(contactList.isContactInList(contact));
        softAssert.assertEquals(contactList.getContactsCount(), contactListSize);
        softAssert.assertAll();
    }
    //bug in backend allows creation of duplicate contacts since it checks uniqueness by ID
    //and not by other parameters - tried to set ID manually for creating contact
    //but it overwrites it with a new unique ID
    @Test(enabled = false)
    public void addContactNegativeTest_409_DuplicateContact() {
        ContactDto contact  = ContactDto.builder()
                .id("f7c3a2d9-2ffc-4bc9-a3ae-5181336d6747")
                .name("testName1_" + generateString(5))
                .lastName("testLastName1_" + generateString(5))
                .email(generateEmail(5))
                .phone("0500101010")
                .address("testAddress1_" + generateString(5))
                .description("testDescription1_" + generateString(5))
                .build();
        Response response = addNewContact(contact, tokenDto);
        if(response.getStatusCode() == 200){
            ResponseMessageDto msgDto = response.getBody().as(ResponseMessageDto.class);
            contact.setId(msgDto.getMessage().split("ID: ")[1]);
            System.out.println(contact);
            response  = addNewContact(contact, tokenDto);
        }
        response.then().log().ifValidationFails()
                .statusCode(409)
                .body(matchesJsonSchemaInClasspath("ErrorMessageDtoSchema.json"))
        ;
        System.out.println("Response status code: " + response.getStatusCode());
        ErrorMessageDto errorMessageDto = response.getBody().as(ErrorMessageDto.class);
        softAssert.assertEquals(response.getStatusCode(), 409);
        System.out.println(errorMessageDto.getMessage().toString());
        softAssert.assertTrue(errorMessageDto.getError().equals("Conflict"));
        softAssert.assertTrue(errorMessageDto.getMessage().toString().contains("must not be blank"));
        ContactsDto contactList = getAllContacts().body().as(ContactsDto.class);
        softAssert.assertFalse(contactList.isContactInList(contact));
        softAssert.assertEquals(contactList.getContactsCount(), contactListSize);
        softAssert.assertAll();
    }

}
