package com.jwt.inventoryservice;

import org.springframework.stereotype.Service;

@Service
public class InventoryService {
    final private InventoryRepository inventoryRepository;

    public InventoryService(InventoryRepository inventoryRepository){
        this.inventoryRepository = inventoryRepository;
    }
    public void handleOrderCreated(OrderCreatedEvent event) {
        Product product = inventoryRepository.findById(event.getProductId()).orElseThrow(() -> new RuntimeException("Product not found"));;
        System.out.println("Inventory quantity: " + product.getQuantity());
        System.out.println("Customer order quantity: " + event.getQuantity());
        if (product.getQuantity() >= event.getQuantity()){
            product.setQuantity(product.getQuantity() - event.getQuantity());
            inventoryRepository.save(product);
        }
    }
}
