package org.rafaelisidro.data.factory;

import net.datafaker.Faker;
import org.rafaelisidro.models.request.PostUserRequestModel;

public class UserFactory {

    private static final Faker faker = new Faker();

    public static PostUserRequestModel validAdminUser() {
        return new PostUserRequestModel(
                faker.name().fullName(),
                faker.internet().emailAddress(),
                faker.credentials().username(),
                "true"
        );
    }

    public static PostUserRequestModel validCommomUser() {
        return new PostUserRequestModel(
                faker.name().fullName(),
                faker.internet().emailAddress(),
                faker.credentials().username(),
                "false"
        );
    }

    public static PostUserRequestModel blankFieldsUser() {
        return new PostUserRequestModel(
                "",
                "",
                "",
                ""
        );
    }

    public static PostUserRequestModel invalidEmailUser() {
        return new PostUserRequestModel(
                faker.name().firstName(),
                faker.name().lastName() + "@testecom",
                faker.credentials().username(),
                "false"
        );
    }
}
