package com.bookloop.bookloop.services;

import com.bookloop.bookloop.controllers.request.OrderRequestDTO;
import com.bookloop.bookloop.controllers.response.OrderResponseDTO;
import com.bookloop.bookloop.entities.Book;
import com.bookloop.bookloop.entities.Order;
import com.bookloop.bookloop.entities.User;
import com.bookloop.bookloop.repositories.IBookRepository;
import com.bookloop.bookloop.repositories.IOrderRepository;
import com.bookloop.bookloop.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final IOrderRepository orderRepository;
    private final IUserRepository userRepository;
    private final IBookRepository bookRepository;

    @Autowired
    public OrderService(IOrderRepository orderRepository, IUserRepository userRepository, IBookRepository bookRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    @Transactional
    public OrderResponseDTO createOrder(OrderRequestDTO dto) {
        // Validar usuário
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        // Validar livros
        List<Book> books = new ArrayList<>();
        for (Long bookId : dto.getBookId()) {
            Book book = bookRepository.findById(bookId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                            "Livro não encontrado com ID: " + bookId));
            books.add(book);
        }

        // Criar nova entidade Order
        Order order = new Order();
        order.setUser(user);
        order.setTotalAmount(dto.getTotalAmount());
        order.setStatus("PENDING");
        order.setCreatedAt(LocalDateTime.now());

        // Adicionar livros ao pedido
        for (Book book : books) {
            order.addBook(book);
        }

        // Salvar no repositório
        Order savedOrder = orderRepository.save(order);

        // Converter para DTO de resposta
        return convertToResponseDTO(savedOrder);
    }

    @Transactional(readOnly = true)
    public OrderResponseDTO getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido não encontrado"));

        return convertToResponseDTO(order);
    }

    @Transactional(readOnly = true)
    public List<OrderResponseDTO> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public OrderResponseDTO updateOrder(Long id, OrderRequestDTO dto) {
        // Buscar pedido existente
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido não encontrado"));

        // Validar usuário
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        // Validar livros
        List<Book> books = new ArrayList<>();
        for (Long bookId : dto.getBookId()) {
            Book book = bookRepository.findById(bookId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                            "Livro não encontrado com ID: " + bookId));
            books.add(book);
        }

        // Atualizar dados do pedido
        order.setUser(user);
        order.setTotalAmount(dto.getTotalAmount());

        // Limpar e adicionar novos livros
        order.getBooks().clear();
        for (Book book : books) {
            order.addBook(book);
        }

        // Salvar alterações
        Order updatedOrder = orderRepository.save(order);

        // Converter para DTO de resposta
        return convertToResponseDTO(updatedOrder);
    }

    @Transactional
    public void deleteOrder(Long id) {
        if (!orderRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido não encontrado");
        }

        orderRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<OrderResponseDTO> getOrdersByUserId(Long userId) {
        // Validar se o usuário existe
        if (!userRepository.existsById(userId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado");
        }

        return orderRepository.findByUserId(userId).stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    // Método de auxílio para converter entidade para DTO
    private OrderResponseDTO convertToResponseDTO(Order order) {
        OrderResponseDTO dto = new OrderResponseDTO();
        dto.setId(order.getId());
        dto.setStatus(order.getStatus());
        dto.setTotalAmount(order.getTotalAmount());
        dto.setCreatedAt(order.getCreatedAt());
        dto.setUserId(order.getUser().getId());

        // Extrair IDs dos livros
        List<Long> bookIds = order.getBooks().stream()
                .map(Book::getId)
                .collect(Collectors.toList());
        dto.setBookIds(bookIds);

        return dto;
    }
}