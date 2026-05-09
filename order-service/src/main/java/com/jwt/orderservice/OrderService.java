package com.jwt.orderservice;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final Publisher publisher;
    private final ObjectMapper objectMapper; // Jackson, for converting to JSON

    @Value("${aws.sqs.inventory-queue-url}")
    private String inventoryQueueUrl;

    @Value("${aws.sqs.notifications-queue-url}")
    private String notificationQueueUrl;

    public OrderService(OrderRepository orderRepository,
                        Publisher publisher,
                        ObjectMapper objectMapper) {
        this.orderRepository = orderRepository;
        this.publisher = publisher;
        this.objectMapper = objectMapper;
    }
    public Order addOrder(Order order){
        // Step 1: save to DB
        Order saved = orderRepository.save(order);

        // Step 2: build the event
        OrderCreatedEvent event = new OrderCreatedEvent(
                saved.getId(),
                saved.getProductId(),
                saved.getQuantity()
        );

        // Step 3: serialize to JSON and publish to both queues
        String message = objectMapper.writeValueAsString(event);
        publisher.publish(inventoryQueueUrl, message, order.getId().toString());
        publisher.publish(notificationQueueUrl, message, order.getId().toString());
        System.out.println(saved);
        return saved;
    }
}
