package com.bookloop.bookloop.controllers.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
public class OrdemServiceResponseDTO {
    private Long id;
    private String status;
    private BigDecimal montanteTotal;
    private LocalDateTime createdAt;
    private Long usuarioId;
    private List<Long> livroIds;
}