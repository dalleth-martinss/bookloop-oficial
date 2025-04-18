package com.bookloop.bookloop.interfaces;

import com.bookloop.bookloop.controllers.request.OrderRequestDTO;
import com.bookloop.bookloop.controllers.response.OrderResponseDTO;

import java.util.List;

public interface IOrderService {
    OrderResponseDTO createOrder(OrderRequestDTO dto);
    OrderResponseDTO getOrderById(Integer id);
    List<OrderResponseDTO> getAllOrders();
    void deleteOrder(Integer id);
}
