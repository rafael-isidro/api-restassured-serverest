package org.rafaelisidro.test;

import org.apache.http.HttpStatus;
import org.rafaelisidro.client.UserClient;
import org.rafaelisidro.data.constants.UserMessages;
import org.rafaelisidro.data.factory.UserFactory;
import org.rafaelisidro.models.request.PostUserRequestModel;
import org.rafaelisidro.models.response.PostUserResponseModel;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class UserPostTest {

    private static final UserClient userClient = new UserClient();

    @Test
    public void testValidarContratoPostUser() {
        PostUserRequestModel validUser = UserFactory.validCommomUser();

        userClient.registerUser(validUser)
                .then()
                    .body(matchesJsonSchemaInClasspath("schemas/post-user-schema.json"));
    }

    @Test
    public void testValidarCadastroComSucesso() {
        PostUserRequestModel validUser = UserFactory.validCommomUser();

        PostUserResponseModel registerResponse = userClient.registerUser(validUser)
                .then()
                    .statusCode(HttpStatus.SC_CREATED)
                    .extract()
                    .response().as(PostUserResponseModel.class);

        Assert.assertEquals(registerResponse.getMessage(), UserMessages.USER_MESSAGES.successRegister());

    }
}
