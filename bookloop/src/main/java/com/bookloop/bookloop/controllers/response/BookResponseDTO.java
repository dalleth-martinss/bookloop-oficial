package com.bookloop.bookloop.controllers.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class BookResponseDTO {
    private Long id;
    private String titulo;
    private String autor;
    private String descricao;
    private BigDecimal preco;
    private String estado;
    private String nomeDono;
}
