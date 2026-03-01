package org.rafaelisidro.data.constants;

public record UserMessages(
        String successRegister,
        String existentEmail,
        String emptyNameField,
        String emptyEmailField,
        String emptyPasswordField,
        String emptyAdminField,
        String invalidEmailField
) {
    public static final UserMessages USER_MESSAGES = new UserMessages(
            "Cadastro realizado com sucesso",
            "Este email já está sendo usado",
            "nome não pode ficar em branco",
            "email não pode ficar em branco",
            "password não pode ficar em branco",
            "administrador deve ser 'true' ou 'false'",
            "email deve ser um email válido"
    );
}
