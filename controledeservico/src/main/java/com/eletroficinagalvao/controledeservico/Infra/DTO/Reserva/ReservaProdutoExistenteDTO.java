package com.eletroficinagalvao.controledeservico.Infra.DTO.Reserva;

import java.util.UUID;

public record ReservaProdutoExistenteDTO(
        UUID uuidProduto,
        int quantidade
) {
}
