package org.rafaelisidro.test;

import org.apache.http.HttpStatus;
import org.rafaelisidro.client.UserClient;
import org.rafaelisidro.data.factory.UserFactory;
import org.rafaelisidro.models.request.PostUserRequestModel;
import org.rafaelisidro.models.response.GetAllUsersResponseModel;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.nullValue;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class UserGetTest {

    private final UserClient userClient = new UserClient();
    private PostUserRequestModel existentUser;

    @BeforeTest
    public void beforeTest() {
        existentUser = UserFactory.validCommomUser();
        userClient.registerUser(existentUser);
    }

    @Test
    public void testValidarContratoGetUser() {

        userClient.getAllUsers()
                .then()
                .body(matchesJsonSchemaInClasspath("schemas/get-users-schema.json"));

    }

    @Test
    public void testValidarListagemDeUsuariosComSucesso() {

        GetAllUsersResponseModel response = userClient.getAllUsers()
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response().as(GetAllUsersResponseModel.class);

        Assert.assertFalse(response.getUsuarios().isEmpty());
        Assert.assertEquals(
                response.getQuantidade(),
                response.getUsuarios().size());

        Assert.assertNotNull(response.getUsuarios().get(0).getNome());
        Assert.assertNotNull(response.getUsuarios().get(0).getEmail());
        Assert.assertNotNull(response.getUsuarios().get(0).getAdministrador());
    }

    @Test
    public void testValidarlistagemDeUsuarioPorNome() {

        GetAllUsersResponseModel response = userClient.getUserByName(existentUser.getNome())
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response().as(GetAllUsersResponseModel.class);

        Assert.assertEquals(response.getUsuarios().get(0).getNome(), existentUser.getNome());
    }

    @Test
    public void testValidarListagemDeUsuarioSemCampoPassword() {

        userClient.getAllUsers()
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("usuarios.password", everyItem(nullValue()));

    }
}
