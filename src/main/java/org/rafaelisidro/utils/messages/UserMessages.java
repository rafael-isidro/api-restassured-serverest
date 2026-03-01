package org.rafaelisidro.utils.messages;

public record UserMessages(String successRegister) {
    public static final UserMessages USER_MESSAGES = new UserMessages(
            "Cadastro realizado com sucesso"
    );
}
