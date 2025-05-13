package com.bookloop.bookloop.controllers.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class OrdemServiceResponseDTO {
    private Long id;
    private String status;
    private BigDecimal montanteTotal;
    private LocalDateTime createdAt;
    private Long usuarioId;
    private List<Long> livroIds;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getMontanteTotal() {
        return montanteTotal;
    }

    public void setMontanteTotal(BigDecimal montanteTotal) {
        this.montanteTotal = montanteTotal;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public List<Long> getLivroIds() {
        return livroIds;
    }

    public void setLivroIds(List<Long> livroIds) {
        this.livroIds = livroIds;
    }
}