package com.bookloop.bookloop.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "wishlist")
@Data
public class WishList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "BIGINT")
    private Long id;

    @Column(name = "create_time", nullable = false)
    private LocalDateTime createTime;

       @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "wishlist", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BookWishlist> bookWishlists = new ArrayList<>();

     public List<Book> getBooks() {
        return bookWishlists.stream().map(BookWishlist::getBook).collect(Collectors.toList());
    }

    public void addBook(Book book) {
        BookWishlist bookWishlist = new BookWishlist();
        bookWishlist.setWishlist(this);
        bookWishlist.setBook(book);
        bookWishlists.add(bookWishlist);
    }

    public void removeBook(Book book) {
        bookWishlists.removeIf(bw -> bw.getBook().equals(book));
    }
}
