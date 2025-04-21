package com.bookloop.bookloop.controllers.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UsuarioResponseDTO {
    private Long id;
    private String nomeCompleto;
    private String email;
    private String numeroTelefone;

}
