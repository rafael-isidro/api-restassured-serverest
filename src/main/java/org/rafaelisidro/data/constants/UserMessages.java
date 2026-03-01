package org.rafaelisidro.data.constants;

public record UserMessages(String successRegister) {
    public static final UserMessages USER_MESSAGES = new UserMessages(
            "Cadastro realizado com sucesso"
    );
}
