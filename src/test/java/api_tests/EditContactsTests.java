package api_tests;

import dto.ContactDto;
import dto.ContactsDto;
import dto.ResponseMessageDto;
import io.restassured.response.Response;
import manager.ContactController;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import utils.TestNGListner;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static pages.BasePage.pause;
import static utils.RandomUtils.generateEmail;
import static utils.RandomUtils.generateString;
@Listeners(TestNGListner.class)

public class EditContactsTests extends ContactController {
    SoftAssert softAssert = new SoftAssert();
    ContactDto contact;


    @BeforeClass(alwaysRun = true)
    public void createContact() {
        contact = ContactDto.builder()
                .name("testName1_" + generateString(5))
                .lastName("testLastName1_" + generateString(5))
                .email(generateEmail(5))
                .phone("0500777010")
                .address("testAddress1_" + generateString(5))
                .description("testDescription1_" + generateString(5))
                .build();
        Response response = addNewContact(contact, tokenDto);
        ResponseMessageDto msgDto = new ResponseMessageDto();
        if(response.getStatusCode() == 200){
            msgDto = response.getBody().as(ResponseMessageDto.class);
            contact.setId(msgDto.getMessage().split("ID: ")[1]);
            System.out.println(contact);

        }
        else
            System.out.println("Failed to create contact. Status code: " + response.getStatusCode());
        System.out.println(msgDto.getMessage());

    }

    @Test(groups = "smoke")
    public void EditContactPositiveTest_200() {
        pause(1);
        contact.setName("another testNamer");
        Response response = editContact(contact,tokenDto);
        System.out.println("Response status code: " + response.getStatusCode());
        System.out.println("Response body: " + response.getBody().asString());
        response.then().log().ifValidationFails()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("ResponseMessageDtoSchema.json"))
        ;
        ResponseMessageDto responseMessageDto = response.body().as(ResponseMessageDto.class);
        softAssert.assertTrue(responseMessageDto.getMessage().contains("Contact was updated"));
        ContactsDto contactList = getAllContacts().body().as(ContactsDto.class);
        softAssert.assertTrue(contactList.isContactInList(contact));
        softAssert.assertAll();
    }

}