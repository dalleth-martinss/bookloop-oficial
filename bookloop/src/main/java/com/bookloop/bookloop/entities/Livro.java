package com.bookloop.bookloop.entities;

import com.bookloop.bookloop.enums.condicaoLivroStatus;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "livro")
public class Livro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "BIGINT")
    private Long id;

    @Column(name = "titulo", length = 255, nullable = false)
    private String titulo;

    @Column(name = "autor", length = 255, nullable = false)
    private String autor;

    @Column(name = "sinopse", length = 255)
    private String sinopse;

    @Column(name = "preco", precision = 10, scale = 2)
    private BigDecimal preco;

    @Column(name = "categoria", length = 45)
    private String categoria;

    @Column(name = "condicao_livro", length = 45)
    private condicaoLivroStatus condicaoLivro;

    @Column(name = "observacao_livro", length = 1048)
    private String observacaoLivro;

    @Column(name = "book_cover", length = 255)
    private String capaLivro;

    @Column(name = "disponivel_para_compra")
    private Boolean disponivelParaCompra;

    @Column(name = "disponivel_para_venda")
    private Boolean disponivelParaVenda;

    // Relationships
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Usuario usuario;

    // Correção do relacionamento com CartItem
    @OneToMany(mappedBy = "livro")
    private List<CarrinhoItem> carrinhoItems = new ArrayList<>();

    // Correção do relacionamento com WishList
    @OneToMany(mappedBy = "livro", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ListaDesejoLivro> listaDesejoLivros = new ArrayList<>();

    // Correção do relacionamento com Order
    @OneToMany(mappedBy = "livro", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrdemLivro> OrdemLivros = new ArrayList<>();

    // Método de conveniência para acessar as wishlists
    public List<ListaDesejo> getListaDesejo() {
        return listaDesejoLivros.stream().map(ListaDesejoLivro::getListaDesejo).collect(Collectors.toList());
    }

    // Método de conveniência para acessar os pedidos
    public List<OrdemServico> getOrders() {
        return OrdemLivros.stream().map(OrdemLivro::getOrdemServico).collect(Collectors.toList());
    }

    public Livro(String titulo, String autor , String sinopse, BigDecimal preco, String categoria, condicaoLivroStatus condicaoLivro, String observacaoLivro
               , String capaLivro, Boolean disponivelParaCompra, Boolean disponivelParaVenda, Usuario usuario, List<CarrinhoItem> carrinhoItems
               , List<ListaDesejoLivro> listaDesejoLivros, List<OrdemLivro> OrdemLivros, Long id) {
        this.titulo = titulo;
        this.autor = autor;
        this.sinopse = sinopse;
        this.preco = preco;
        this.categoria = categoria;
        this.condicaoLivro = condicaoLivro;
        this.observacaoLivro = observacaoLivro;
        this.capaLivro = capaLivro;
        this.disponivelParaCompra = disponivelParaCompra;
        this.disponivelParaVenda = disponivelParaVenda;
        this.usuario = usuario;
        this.carrinhoItems = carrinhoItems;
        this.listaDesejoLivros = listaDesejoLivros;
        this.OrdemLivros = OrdemLivros;
        this.id = id;
    }
    public Livro(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getSinopse() {
        return sinopse;
    }

    public void setSinopse(String sinopse) {
        this.sinopse = sinopse;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public condicaoLivroStatus getCondicaoLivro() {
        return condicaoLivro;
    }

    public void setCondicaoLivro(condicaoLivroStatus condicaoLivro) {
        this.condicaoLivro = condicaoLivro;
    }

    public String getObservacaoLivro() {
        return observacaoLivro;
    }

    public void setObservacaoLivro(String observacaoLivro) {
        this.observacaoLivro = observacaoLivro;
    }

    public String getCapaLivro() {
        return capaLivro;
    }

    public void setCapaLivro(String capaLivro) {
        this.capaLivro = capaLivro;
    }

    public Boolean getDisponivelParaCompra() {
        return disponivelParaCompra;
    }

    public void setDisponivelParaCompra(Boolean disponivelParaCompra) {
        this.disponivelParaCompra = disponivelParaCompra;
    }

    public Boolean getDisponivelParaVenda() {
        return disponivelParaVenda;
    }

    public void setDisponivelParaVenda(Boolean disponivelParaVenda) {
        this.disponivelParaVenda = disponivelParaVenda;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<CarrinhoItem> getCarrinhoItems() {
        return carrinhoItems;
    }

    public void setCarrinhoItems(List<CarrinhoItem> carrinhoItems) {
        this.carrinhoItems = carrinhoItems;
    }

    public List<ListaDesejoLivro> getLivroDesejoLivros() {
        return listaDesejoLivros;
    }

    public void setLivroDesejoLivros(List<ListaDesejoLivro> listaDesejoLivros) {
        this.listaDesejoLivros = listaDesejoLivros;
    }

    public List<OrdemLivro> getOrdemLivros() {
        return OrdemLivros;
    }

    public void setOrdemLivros(List<OrdemLivro> OrdemLivros) {
        this.OrdemLivros = OrdemLivros;
    }
}