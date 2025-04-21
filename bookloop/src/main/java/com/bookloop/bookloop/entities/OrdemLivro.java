package com.bookloop.bookloop.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "ordem_livro")
@Data
public class OrdemLivro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "BIGINT")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ordem_servico_id", nullable = false, columnDefinition = "BIGINT")
    private OrdemServico ordemServico;

    @ManyToOne
    @JoinColumn(name = "livro_id", nullable = false, columnDefinition = "BIGINT")
    private Livro livro;
}