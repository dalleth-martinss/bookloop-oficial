package com.bookloop.bookloop.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "book_wishlist")
@Data
public class BookWishlist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "BIGINT")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false, columnDefinition = "BIGINT")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "wishlist_id", nullable = false, columnDefinition = "BIGINT")
    private WishList wishlist;
}
