package com.jwt.orderservice;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class OrderCreatedEvent {
    private Integer orderId;
    private Integer productId;
    private Integer quantity;
}
