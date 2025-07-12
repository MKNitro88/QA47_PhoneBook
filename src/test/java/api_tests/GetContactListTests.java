package api_tests;

import dto.ContactsDto;
import io.restassured.response.Response;
import manager.ContactController;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static pages.BasePage.pause;


public class GetContactListTests extends ContactController {
    SoftAssert softAssert = new SoftAssert();

        @Test(groups = "smoke")
        public void getContactListPositiveTest_200 () {
            pause(1);
            Response response = getAllContacts();
            response.then().log().ifValidationFails()
                    .statusCode(200)
                    .body(matchesJsonSchemaInClasspath("ContactsDtoSchema.json"))
            ;
            System.out.println("Response status code: " + response.getStatusCode());
            System.out.println("Response body: " + response.getBody().asString());
            ContactsDto contactsDto = new ContactsDto();
            if (response.getStatusCode() == 200) {
                contactsDto = response.body().as(ContactsDto.class);
            }
            System.out.println("Contacts: " + contactsDto.getContacts().length);
            softAssert.assertEquals(response.getStatusCode(), 200, "Expected status code 200");
            softAssert.assertTrue(response.getBody().asString().contains("contacts"), "Contacts not found in response");
            softAssert.assertAll();
        }

    }
