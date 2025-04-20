package com.bookloop.bookloop.services;

import com.bookloop.bookloop.controllers.request.BookRequestDTO;
import com.bookloop.bookloop.controllers.response.BookResponseDTO;
import com.bookloop.bookloop.entities.Book;
import com.bookloop.bookloop.entities.User;
import com.bookloop.bookloop.enums.ConditionStatus;
import com.bookloop.bookloop.repositories.IBookRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {

    private final IBookRepository bookRepository;

    public BookService(IBookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public BookResponseDTO createBook(BookRequestDTO dto) {
        Book book = new Book();
        book.setTitle(dto.getTitulo());
        book.setAutor(dto.getAutor());
        book.setSynopsis(dto.getDescricao());
        book.setPrice(dto.getPreco());
        book.setCategory("Geral"); // valor padrão
        book.setCondition(ConditionStatus.valueOf(dto.getEstado().toUpperCase()));
        book.setAvailableForSale(true);
        book.setAvailableForTrade(false);

        if (dto.getUserId() != null) {
            User user = new User();
            user.setId(dto.getUserId());
            book.setUser(user);
        }

        Book saved = bookRepository.save(book);
        return toDTO(saved);
    }

    public BookResponseDTO getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Livro não encontrado"));
        return toDTO(book);
    }

    public List<BookResponseDTO> getAllBooks() {
        return bookRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public BookResponseDTO updateBook(Long id, BookRequestDTO dto) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Livro não encontrado"));

        book.setTitle(dto.getTitulo());
        book.setAutor(dto.getAutor());
        book.setSynopsis(dto.getDescricao());
        book.setPrice(dto.getPreco());
        book.setCondition(ConditionStatus.valueOf(dto.getEstado().toUpperCase()));

        if (dto.getUserId() != null) {
            User user = new User();
            user.setId(dto.getUserId());
            book.setUser(user);
        }

        Book updated = bookRepository.save(book);
        return toDTO(updated);
    }

    public void deleteBook(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Livro não encontrado"));
        bookRepository.delete(book);
    }

    private BookResponseDTO toDTO(Book book) {
        BookResponseDTO dto = new BookResponseDTO();
        dto.setId(book.getId());
        dto.setTitulo(book.getTitle());
        dto.setAutor(book.getAutor());
        dto.setDescricao(book.getSynopsis());
        dto.setPreco(book.getPrice());
        dto.setEstado(book.getCondition().toString());
        dto.setNomeDono(book.getUser() != null ? book.getUser().getFullName() : null);
        return dto;
    }
}