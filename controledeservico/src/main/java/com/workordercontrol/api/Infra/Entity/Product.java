package com.workordercontrol.api.Infra.Entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.Id;

import java.util.UUID;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

