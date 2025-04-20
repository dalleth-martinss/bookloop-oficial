package com.bookloop.bookloop.entities;

import com.bookloop.bookloop.enums.ConditionStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "BIGINT")
    private Long id;

    @Column(name = "title", length = 255, nullable = false)
    private String title;

    @Column(name = "autor", length = 255, nullable = false)
    private String autor;

    @Column(name = "synopsis", length = 255)
    private String synopsis;

    @Column(name = "price", precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "category", length = 45)
    private String category;

    @Column(name = "book_condition", length = 45)
    private ConditionStatus condition;

    @Column(name = "book_observations", length = 255)
    private String observations;

    @Column(name = "book_cover", length = 255)
    private String bookCover;

    @Column(name = "available_for_trade")
    private Boolean availableForTrade;

    @Column(name = "available_for_sale")
    private Boolean availableForSale;

    // Relationships
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    // Correção do relacionamento com CartItem
    @OneToMany(mappedBy = "book")
    private List<CartItem> cartItems = new ArrayList<>();

    // Correção do relacionamento com WishList
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BookWishlist> bookWishlists = new ArrayList<>();

    // Correção do relacionamento com Order
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderBook> orderBooks = new ArrayList<>();

    // Método de conveniência para acessar as wishlists
    public List<WishList> getWishlists() {
        return bookWishlists.stream().map(BookWishlist::getWishlist).collect(Collectors.toList());
    }

    // Método de conveniência para acessar os pedidos
    public List<Order> getOrders() {
        return orderBooks.stream().map(OrderBook::getOrder).collect(Collectors.toList());
    }

    public Book( String title, String autor , String synopsis, BigDecimal price, String category, ConditionStatus condition, String observations
               , String bookCover, Boolean availableForTrade, Boolean availableForSale, User user, List<CartItem> cartItems
               , List<BookWishlist> bookWishlists, List<OrderBook> orderBooks, Long id) {
        this.title = title;
        this.autor = autor;
        this.synopsis = synopsis;
        this.price = price;
        this.category = category;
        this.condition = condition;
        this.observations = observations;
        this.bookCover = bookCover;
        this.availableForTrade = availableForTrade;
        this.availableForSale = availableForSale;
        this.user = user;
        this.cartItems = cartItems;
        this.bookWishlists = bookWishlists;
        this.orderBooks = orderBooks;
        this.id = id;
    }
    public Book(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public ConditionStatus getCondition() {
        return condition;
    }

    public void setCondition(ConditionStatus condition) {
        this.condition = condition;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public String getBookCover() {
        return bookCover;
    }

    public void setBookCover(String bookCover) {
        this.bookCover = bookCover;
    }

    public Boolean getAvailableForTrade() {
        return availableForTrade;
    }

    public void setAvailableForTrade(Boolean availableForTrade) {
        this.availableForTrade = availableForTrade;
    }

    public Boolean getAvailableForSale() {
        return availableForSale;
    }

    public void setAvailableForSale(Boolean availableForSale) {
        this.availableForSale = availableForSale;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public List<BookWishlist> getBookWishlists() {
        return bookWishlists;
    }

    public void setBookWishlists(List<BookWishlist> bookWishlists) {
        this.bookWishlists = bookWishlists;
    }

    public List<OrderBook> getOrderBooks() {
        return orderBooks;
    }

    public void setOrderBooks(List<OrderBook> orderBooks) {
        this.orderBooks = orderBooks;
    }
}