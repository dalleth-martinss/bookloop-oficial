package com.bookloop.bookloop.controllers.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class OrdemServicoRequestDTO {
    private List<Long> livroId;
    private Long usuarioId;
    private BigDecimal montanteTotal;
}
