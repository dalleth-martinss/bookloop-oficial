package com.bookloop.bookloop.repositories;

import com.bookloop.bookloop.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IOrderRepository extends JpaRepository<Order, Long> {
       List<Order> findByUserId(Long userId);
}