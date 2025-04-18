package com.bookloop.bookloop.controllers.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class BookRequestDTO {
    private String titulo;
    private String autor;
    private String descricao;
    private BigDecimal preco;
    private String estado;
    private Long userId;
}
