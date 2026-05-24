package com.jwt.inventoryservice;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "product")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @JsonProperty("product_id")
    @Column(name="product_id")
    private Integer productId;

    @Column(name="name")
    private String name;

    @Column(name="quantity")
    private Integer quantity;
}
