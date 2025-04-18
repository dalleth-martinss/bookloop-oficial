package com.bookloop.bookloop.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "book")
@Data
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Renomeado de book_id para seguir convenção

    @Column(name = "title", length = 255, nullable = false)
    private String title;

    @Column(name = "synopsis", length = 255)
    private String synopsis;

    @Column(name = "price", precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "category", length = 45)
    private String category;

    @Column(name = "condition", length = 45)
    private String condition;

    @Column(name = "observations", length = 255)
    private String observations;

    @Column(name = "book_cover", length = 255) // Renomeado para snake_case
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
    @ManyToMany(mappedBy = "books")
    private List<WishList> wishlists = new ArrayList<>();

    // Correção do relacionamento com Order
    @ManyToMany
    @JoinTable(
            name = "order_book",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "order_id")
    )
    private List<Order> orders = new ArrayList<>();
}
