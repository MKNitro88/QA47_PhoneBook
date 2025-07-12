package manager;

import dto.UserDto;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.BaseAPI;
import static utils.PropertiesReader.*;

import static io.restassured.RestAssured.given;

public class AuthentificationController implements BaseAPI {
    /* given()
      .header("Authorization", "value")
      .body(user)
        .when()
        .post(url)
    .then()  thhen Return() ---> response
    */
    public Logger logger = LoggerFactory.getLogger(AuthentificationController.class);

    public Response requestRegLogin(UserDto user, String url) {
        return given().baseUri(getProperty("loginPB.properties","baseUri"))
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)// content - type :app/json
                .body(user)
                .when()
                .post(url)
                .thenReturn();
    }

}
