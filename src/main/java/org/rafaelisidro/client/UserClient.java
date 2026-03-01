package org.rafaelisidro.client;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.rafaelisidro.models.request.PostUserRequestModel;

import static io.restassured.RestAssured.given;

public class UserClient extends BaseClient {

    private final String USER = "/usuarios";

    public Response registerUser(PostUserRequestModel user) {
        return
                given()
                        .spec(super.set())
                        .contentType(ContentType.JSON)
                        .body(user)
                .when()
                        .post(USER);
    }

    public Response getAllUsers() {
        return
                given()
                        .spec(super.set())
                .when()
                        .get(USER);
    }

    public Response getUserByName(String name) {
        return
                given()
                        .spec(super.set())
                        .queryParam("nome", name)
                .when()
                        .get(USER);
    }
}
