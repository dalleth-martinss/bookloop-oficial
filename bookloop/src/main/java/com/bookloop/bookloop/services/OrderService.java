package com.bookloop.bookloop.services;

import com.bookloop.bookloop.entities.Book;
import com.bookloop.bookloop.entities.Order;
import com.bookloop.bookloop.entities.OrderBook;
import com.bookloop.bookloop.entities.User;
import com.bookloop.bookloop.interfaces.IOrderService;
import com.bookloop.bookloop.repositories.IBookRepository;
import com.bookloop.bookloop.repositories.IOrderRepository;
import com.bookloop.bookloop.repositories.IUserRepository;
import com.bookloop.bookloop.controllers.request.OrderRequestDTO;
import com.bookloop.bookloop.controllers.response.OrderResponseDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService implements IOrderService {

    private final IOrderRepository orderRepository;
    private final IUserRepository userRepository;
    private final IBookRepository bookRepository;
    private final ModelMapper modelMapper;

    public OrderService(IOrderRepository orderRepository, IUserRepository userRepository,
                       IBookRepository bookRepository, ModelMapper modelMapper) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public OrderResponseDTO createOrder(OrderRequestDTO dto) {
        Order order = new Order();
        order.setStatus("PENDING");
        order.setCreatedAt(LocalDateTime.now());
        order.setTotalAmount(dto.getTotalAmount());

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        order.setUser(user);

        List<Book> books = bookRepository.findAllById(dto.getBookId());

        List<OrderBook> orderBooks = books.stream().map(book -> {
            OrderBook orderBook = new OrderBook();
            orderBook.setBook(book);
            orderBook.setOrder(order);
            return orderBook;
        }).collect(Collectors.toList());

        order.setOrderBooks(orderBooks);

        Order saved = orderRepository.save(order);
        return modelMapper.map(saved, OrderResponseDTO.class);
    }

    @Override
    public OrderResponseDTO getOrderById(Integer id) {
        return orderRepository.findById(id)
                .map(order -> modelMapper.map(order, OrderResponseDTO.class))
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
    }

    @Override
    public List<OrderResponseDTO> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(order -> modelMapper.map(order, OrderResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteOrder(Integer id) {
        orderRepository.deleteById(id);
    }
}
