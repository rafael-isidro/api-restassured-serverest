package org.rafaelisidro.test;

import org.apache.http.HttpStatus;
import org.rafaelisidro.client.UserClient;
import org.rafaelisidro.models.response.GetAllUsersResponseModel;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class UserGetTest {

    private static final UserClient userClient = new UserClient();

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
                response.getUsuarios().size()
        );

        Assert.assertNotNull(response.getUsuarios().get(0).getNome());
        Assert.assertNotNull(response.getUsuarios().get(0).getEmail());
        Assert.assertNotNull(response.getUsuarios().get(0).getPassword());
        Assert.assertNotNull(response.getUsuarios().get(0).getAdministrador());
    }
}
