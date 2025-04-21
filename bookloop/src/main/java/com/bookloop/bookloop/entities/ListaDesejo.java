package com.bookloop.bookloop.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "lista_desejo")
@Data
public class ListaDesejo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "BIGINT")
    private Long id;

    @Column(name = "create_time", nullable = false)
    private LocalDateTime createTime;

       @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Usuario usuario;

    @OneToMany(mappedBy = "listaDesejo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ListaDesejoLivro> listaDesejoLivros = new ArrayList<>();

     public List<Livro> getLivros() {
        return listaDesejoLivros.stream().map(ListaDesejoLivro::getLivro).collect(Collectors.toList());
    }

    public void addLivro(Livro livro) {
        ListaDesejoLivro listaDesejoLivro = new ListaDesejoLivro();
        listaDesejoLivro.setListaDesejo(this);
        listaDesejoLivro.setLivro(livro);
        listaDesejoLivros.add(listaDesejoLivro);
    }

    public void removeLivro(Livro livro) {
        listaDesejoLivros.removeIf(bw -> bw.getLivro().equals(livro));
    }
}
