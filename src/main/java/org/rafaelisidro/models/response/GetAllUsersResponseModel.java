package org.rafaelisidro.models.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAllUsersResponseModel {

    private int quantidade;
    private List<GetUserResponseModel> usuarios;

}