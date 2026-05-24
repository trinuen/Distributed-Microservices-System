package com.jwt.inventoryservice;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements ApplicationRunner {
    // Inject your InventoryRepository
    // In the run() method, check if inventory is empty
    // If so, save a few sample Product rows with stock quantities
    private final InventoryRepository inventoryRepository;

    public DataSeeder(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        // Only seed if database is empty
        if (inventoryRepository.count() == 0) {

            Product laptop = new Product(12, "Laptop", 15);
            Product phone = new Product(11, "Phone", 30);
            Product keyboard = new Product(13, "Keyboard", 50);

            inventoryRepository.save(laptop);
            inventoryRepository.save(phone);
            inventoryRepository.save(keyboard);

            System.out.println("Sample inventory seeded!");
        }
    }
}
