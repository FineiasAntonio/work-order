package com.workordercontrol.api.Infra.DTO.Reserva;

import java.util.List;

public record ReserveRequest(
        List<ReserveProductsRequest> products,
        int labor
) {
}
