package com.workordercontrol.api.Infra.DTO.Reserva;

import java.util.UUID;

public record ReserveProductsRequest(
        UUID productId,
        int quantity
) {
}
