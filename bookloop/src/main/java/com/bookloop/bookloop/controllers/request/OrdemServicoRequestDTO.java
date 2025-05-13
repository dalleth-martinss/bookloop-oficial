package com.bookloop.bookloop.controllers.request;

import java.math.BigDecimal;
import java.util.List;

public class OrdemServicoRequestDTO {
    private List<Long> livroId;

    private Long usuarioId;

    private BigDecimal montanteTotal;

    public List<Long> getLivroId() {
        return livroId;
    }

    public void setLivroId(List<Long> livroId) {
        this.livroId = livroId;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public BigDecimal getMontanteTotal() {
        return montanteTotal;
    }

    public void setMontanteTotal(BigDecimal montanteTotal) {
        this.montanteTotal = montanteTotal;
    }
}
