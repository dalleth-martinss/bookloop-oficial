package com.bookloop.bookloop.services;

import com.bookloop.bookloop.entities.Book;
import com.bookloop.bookloop.entities.User;
import com.bookloop.bookloop.interfaces.IBookService;
import com.bookloop.bookloop.repositories.IBookRepository;
import com.bookloop.bookloop.repositories.IUserRepository;
import com.bookloop.bookloop.controllers.request.BookRequestDTO;
import com.bookloop.bookloop.controllers.response.BookResponseDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService implements IBookService {

    private final IBookRepository bookRepository;
    private final IUserRepository userRepository;
    private final ModelMapper modelMapper;

    public BookService(IBookRepository bookRepository, IUserRepository userRepository
                      , ModelMapper modelMapper) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public BookResponseDTO createBook(BookRequestDTO dto) {
        Book book = modelMapper.map(dto, Book.class);
        User user = userRepository.findById(dto.getUserId() )
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        book.setUser(user);
        return modelMapper.map(bookRepository.save(book), BookResponseDTO.class);
    }

    @Override
    public BookResponseDTO getBookById(Long id) {
        return bookRepository.findById(id)
                .map(book -> modelMapper.map(book, BookResponseDTO.class))
                .orElseThrow(() -> new RuntimeException("Livro não encontrado"));
    }

    @Override
    public List<BookResponseDTO> getAllBooks() {
        return bookRepository.findAll()
                .stream()
                .map(book -> modelMapper.map(book, BookResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public BookResponseDTO updateBook(Long id, BookRequestDTO dto) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Livro não encontrado"));
        modelMapper.map(dto, book);
        return modelMapper.map(bookRepository.save(book), BookResponseDTO.class);
    }

    @Override
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }
}

