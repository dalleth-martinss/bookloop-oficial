package com.bookloop.bookloop.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "ordem_servico")
public class OrdemServico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "BIGINT")
    private Long id;

    @Column(name = "montante_total", precision = 10, scale = 2)
    private BigDecimal montanteTotal;

    @Column(name = "status", length = 45)
    private String status;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @OneToMany(mappedBy = "ordemServico", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrdemLivro> ordemLivros = new ArrayList<>();

    public List<Livro> getLivros() {
        return ordemLivros.stream()
                          .map(OrdemLivro::getLivro)
                          .collect(Collectors.toList());
    }

    @OneToOne(mappedBy = "ordemServico", cascade = CascadeType.ALL, orphanRemoval = true)
    private Pagamento pagamento;

    public void addLivro(Livro livro) {
        OrdemLivro ordemLivro = new OrdemLivro();
        ordemLivro.setOrdemServico(this);
        ordemLivro.setLivro(livro);
        ordemLivros.add(ordemLivro);
    }
    public void removeLivro(Livro livro) {
        ordemLivros.removeIf(ordemLivro -> ordemLivro.getLivro().equals(livro));
    }

    public OrdemServico( BigDecimal montanteTotal, String status, LocalDateTime createdAt, Usuario usuario
                       , List<OrdemLivro> ordemLivros, Pagamento pagamento, Long id) {
        this.montanteTotal = montanteTotal;
        this.status = status;
        this.createdAt = createdAt;
        this.usuario = usuario;
        this.ordemLivros = ordemLivros;
        this.pagamento = pagamento;
        this.id = id;
    }

    public OrdemServico() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getMontanteTotal() {
        return montanteTotal;
    }

    public void setMontanteTotal(BigDecimal montanteTotal) {
        this.montanteTotal = montanteTotal;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<OrdemLivro> getOrdemLivros() {
        return ordemLivros;
    }

    public void setOrdemLivros(List<OrdemLivro> ordemLivros) {
        this.ordemLivros = ordemLivros;
    }

    public Pagamento getPagamento() {
        return pagamento;
    }

    public void setPagamento(Pagamento pagamento) {
        this.pagamento = pagamento;
    }
}

