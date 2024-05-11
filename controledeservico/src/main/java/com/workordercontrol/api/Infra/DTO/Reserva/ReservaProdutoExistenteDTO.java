package com.workordercontrol.api.Infra.DTO.Reserva;

import java.util.UUID;

public record ReservaProdutoExistenteDTO(
        UUID uuidProduto,
        int quantidade
) {
}
