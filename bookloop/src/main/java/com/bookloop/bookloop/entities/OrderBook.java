package com.bookloop.bookloop.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "order_book")
@Data
public class OrderBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "BIGINT")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false, columnDefinition = "BIGINT")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false, columnDefinition = "BIGINT")
    private Book book;
}