package com.jwt.inventoryservice;

import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements ApplicationRunner {
    // Inject your InventoryRepository
    // In the run() method, check if inventory is empty
    // If so, save a few sample Product rows with stock quantities
}
