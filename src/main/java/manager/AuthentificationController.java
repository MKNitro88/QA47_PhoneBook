package manager;

import dto.UserLombok;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
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
    public Response requestRegLogin(UserLombok user, String url) {
        return given().contentType(ContentType.JSON) // content - type :app/json
                .body(user)
                .when()
                .post(BASE_URL+ url)
                .thenReturn();
    }
}
