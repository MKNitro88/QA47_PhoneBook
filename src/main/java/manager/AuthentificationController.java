package manager;

import dto.UserLombok;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.BaseAPI;

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

    public Response requestRegLogin(UserLombok user, String url) {
        return given().contentType(ContentType.JSON) // content - type :app/json
                .body(user)
                .when()
                .post(BASE_URL+ url)
                .thenReturn();
    }
    public boolean validateErrorMsg(Response response, String errorMsg) {
        return response.getBody().asString().contains(errorMsg);
}}
