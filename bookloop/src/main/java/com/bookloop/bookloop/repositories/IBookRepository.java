package com.bookloop.bookloop.repositories;

import com.bookloop.bookloop.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IBookRepository extends JpaRepository<Book, Long> {

    List<Book> findByUserId(Long userId);
    List<Book> findByTitleContainingIgnoreCase(String title);

}
