package org.rafaelisidro.test;

import jdk.jfr.Description;
import org.apache.http.HttpStatus;
import org.rafaelisidro.client.UserClient;
import org.rafaelisidro.data.factory.UserFactory;
import org.rafaelisidro.models.request.PostUserRequestModel;
import org.rafaelisidro.models.response.PostUserResponseModel;
import org.rafaelisidro.utils.messages.UserMessages;
import org.testng.Assert;
import org.testng.annotations.Test;

public class UserPostTest {

    private static final UserClient userClient = new UserClient();

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
