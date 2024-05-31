package com.workordercontrol.api.Infra.DTO.Estoque;

public record ProductRequest(
        String name,
        String reference,
        int quantity,
        double unitPrice
) {}
