package com.jwt.inventoryservice;

import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Product, Integer> {
}
