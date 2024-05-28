package com.workordercontrol.api.Infra.Entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID productId;

    private String name;
    private String reference;
    private int quantity;
    private double unitPrice;

    public Product(String name, String reference, int quantity, double unitPrice) {
        this.name = name;
        this.reference = reference;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public Product(Product produto) {
    }
}

