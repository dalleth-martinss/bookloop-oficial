package com.bookloop.bookloop.controllers.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class OrderResponseDTO {
    private Integer id;
    private String status;
    private BigDecimal totalAmount;
    private LocalDateTime createdAt;
    private Long userId;
    private List<Long> bookIds;
}
