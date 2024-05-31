 package com.workordercontrol.api.Infra.DTO;

import lombok.Builder;

import java.util.UUID;

 @Builder
public record NotificationDTO(
        UUID productId,
        String clientName,
        int workOrderId,
        String product,
        int quantity
) {
}
