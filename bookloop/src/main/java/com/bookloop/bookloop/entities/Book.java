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
@Data
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "BIGINT")
    private Long id;

    @Column(name = "title", length = 255, nullable = false)
    private String title;

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
}