package api_tests;

import dto.ContactDto;
import dto.ResponseMessageDto;
import dto.TokenDto;
import io.restassured.response.Response;
import manager.ContactController;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import utils.TestNGListner;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static utils.RandomUtils.generateEmail;
import static utils.RandomUtils.generateString;

@Listeners(TestNGListner.class)
public class DeleteContactByIdTests extends ContactController {
    ContactDto contact;

    @BeforeClass(alwaysRun = true)
    public void createContact() {
        contact = ContactDto.builder()
                .name(generateString(5))
                .lastName(generateString(10))
                .phone("0123456789")
                .email(generateEmail(10))
                .address("Haifa " + generateString(10))
                .description("desc " + generateString(15))
                .build();
        Response response = addNewContact(contact, tokenDto);
        ResponseMessageDto responseMessageDto;
        if(response.getStatusCode() != 200)
            System.out.println("Contact doesn't create");
        else{
            responseMessageDto = response.body().as(ResponseMessageDto.class);
            contact.setId(responseMessageDto.getMessage().split("ID: ")[1]);
        }
    }
    @Test(groups = "smoke")
    public void deleteContactByIdPositiveTest(){
        Response response = deleteContactById(contact, tokenDto);
        response
                .then()
                .log().ifValidationFails()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("ResponseMessageDtoSchema.json"))//Contact was deleted!
        ;
    }

    @Test(groups = "contacts")
    public void deleteContactByIdNegativeTest_401_invalidToken(){
        TokenDto tokenDto1 = TokenDto.builder().token("invalid").build();
        Response response = deleteContactById(contact, tokenDto1);
        response
                .then()
                .log().all()
                .statusCode(401)
                .body(matchesJsonSchemaInClasspath("ErrorMessageDtoSchema.json"))
        ;
    }
}
