package org.rafaelisidro.models.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetUserResponseModel {

    private String nome;
    private String email;
    private String administrador;
    private String _id;

}