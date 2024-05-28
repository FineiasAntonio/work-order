package com.workordercontrol.api.Infra.Entity;

import com.workordercontrol.api.Exception.CustomExceptions.InternalServerErrorException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "reserves")
public class Reserve {

    @Transient
    @Schema(hidden = true)
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private int workOrderId;

    @Transient
    private Map<UUID, ReservedProduct> reservedProducts = new HashMap<>();

    @Column(columnDefinition = "jsonb", name = "reserved_products")
    @Getter(AccessLevel.PROTECTED)
    @Setter(AccessLevel.PROTECTED)
    private String jsonbReservedProducts = "";

    private boolean active;
    private int labor;

    public Reserve(int maoDeObra) {
        this.active = false;
        this.labor = maoDeObra;
    }

    public Reserve(Map<UUID, ReservedProduct> reservedProducts, int maoDeObra) {
        this.reservedProducts = reservedProducts;
        this.active = true;
        this.labor = maoDeObra;
    }

    @PostLoad
    private void instantiateReservedProducts() {
        try {
            this.reservedProducts = objectMapper.readValue(this.jsonbReservedProducts, objectMapper.getTypeFactory().constructMapType(Map.class, UUID.class, ReservedProduct.class));
        } catch (JsonProcessingException e) {
            throw new InternalServerErrorException("Error while parsing attribute");
        }
    }

    @PrePersist
    @PreUpdate
    private void setReservedProducts() {
        try {
            this.jsonbReservedProducts = objectMapper.writeValueAsString(this.reservedProducts);
        } catch (JsonProcessingException e) {
            throw new InternalServerErrorException("Error while parsing attribute");
        }
    }
}
