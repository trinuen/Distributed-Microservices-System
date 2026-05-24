package com.jwt.notificationservice;

import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    final private NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository){
        this.notificationRepository = notificationRepository;
    }
    public void handleOrderCreated(OrderCreatedEvent event){
        // log to console
        System.out.println("Order confirmed - orderId: " + event.getOrderId()
                + " productId: " + event.getProductId()
                + " quantity: " + event.getQuantity());

        // save to SQLite
        Notification notification = new Notification();
        notification.setProductId(event.getOrderId());
        notification.setMessage("Order " + event.getOrderId() + " confirmed");
        notificationRepository.save(notification);
    }
}
