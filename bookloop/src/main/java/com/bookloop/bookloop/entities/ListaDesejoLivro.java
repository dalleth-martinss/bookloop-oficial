package com.bookloop.bookloop.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "lista_desejo_livro")
@Data
public class ListaDesejoLivro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "BIGINT")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false, columnDefinition = "BIGINT")
    private Livro livro;

    @ManyToOne
    @JoinColumn(name = "lista_desejo_id", nullable = false, columnDefinition = "BIGINT")
    private ListaDesejo listaDesejo;
}
