package com.bookloop.bookloop.controllers.request;


import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class UsuarioUpdateDTO {
    private String nomeCompleto;
    private LocalDateTime dataNascimento;
    private String genero;
    private String numeroTelefone;
    private String email;
    private String senha;
}