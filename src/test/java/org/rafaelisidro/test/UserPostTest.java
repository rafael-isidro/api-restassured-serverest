package org.rafaelisidro.test;

import io.restassured.response.Response;
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

    @Test
    public void testValidarCadastroAdminComSucesso() {
        PostUserRequestModel validAdminUser = UserFactory.validAdminUser();

        PostUserResponseModel registerResponse = userClient.registerUser(validAdminUser)
                .then()
                    .statusCode(HttpStatus.SC_CREATED)
                    .extract()
                    .response().as(PostUserResponseModel.class);

        Assert.assertEquals(registerResponse.getMessage(), UserMessages.USER_MESSAGES.successRegister());

    }

    @Test
    public void testTentarCadastrarUsuarioEmDuplicidade() {
        PostUserRequestModel validUser = UserFactory.validCommomUser();

        userClient.registerUser(validUser);

        String response = userClient.registerUser(validUser)
                .then()
                    .statusCode(HttpStatus.SC_BAD_REQUEST)
                    .extract().path("message").toString();

        Assert.assertEquals(response, UserMessages.USER_MESSAGES.existentEmail());
    }

    @Test
    public void testTentarCadastrarUsuarioComCamposVazios() {
        PostUserRequestModel emptyFieldsUser = UserFactory.blankFieldsUser();

        Response response = userClient.registerUser(emptyFieldsUser)
                .then()
                    .statusCode(HttpStatus.SC_BAD_REQUEST)
                    .extract().response();

        Assert.assertEquals(response.jsonPath().getString("nome"), UserMessages.USER_MESSAGES.emptyNameField());
        Assert.assertEquals(response.jsonPath().getString("email"), UserMessages.USER_MESSAGES.emptyEmailField());
        Assert.assertEquals(response.jsonPath().getString("password"), UserMessages.USER_MESSAGES.emptyPasswordField());
        Assert.assertEquals(response.jsonPath().getString("administrador"), UserMessages.USER_MESSAGES.emptyAdminField());
    }

    @Test
    public void testTentarCadastrarUsuarioComEmailInvalido() {
        PostUserRequestModel invalidUser = UserFactory.invalidEmailUser();

        String response = userClient.registerUser(invalidUser)
                .then()
                    .statusCode(HttpStatus.SC_BAD_REQUEST)
                    .extract().path("email").toString();

        Assert.assertEquals(response, UserMessages.USER_MESSAGES.invalidEmailField());
    }
}
