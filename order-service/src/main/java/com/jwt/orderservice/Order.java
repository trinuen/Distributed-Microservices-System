package com.jwt.orderservice;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name="orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @JsonProperty("product_id")
    @Column(name="product_id")
    private Integer productId;

    @Column(name="quantity")
    private Integer quantity;

    @Column(name="status")
    private String status;

    @CreationTimestamp
    @Column(name="created_at")
    private LocalDateTime createdAt;

}
