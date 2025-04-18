package com.bookloop.bookloop.controllers.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class OrderRequestDTO {
    private List<Long> bookId;
    private Long userId;
    private BigDecimal totalAmount;
}
