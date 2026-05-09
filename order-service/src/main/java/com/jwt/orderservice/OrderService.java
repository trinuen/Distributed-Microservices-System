package com.jwt.orderservice;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrderService {

    private OrderRepository orderRepository;

    public Order addOrder(Order order){
        return orderRepository.save(order);
    }
}
