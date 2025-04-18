package com.bookloop.bookloop.interfaces;

import com.bookloop.bookloop.request.BookRequestDTO;
import com.bookloop.bookloop.response.BookResponseDTO;

import java.util.List;

public interface IBookService {
    BookResponseDTO createBook(BookRequestDTO dto);
    BookResponseDTO getBookById(Long id);
    List<BookResponseDTO> getAllBooks();
    BookResponseDTO updateBook(Long id, BookRequestDTO dto);
    void deleteBook(Long id);
}
