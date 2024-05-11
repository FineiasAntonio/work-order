package com.workordercontrol.api.Infra.DTO.Reserva;

import com.workordercontrol.api.Infra.DTO.Estoque.ProductRequest;

import java.util.List;

public record ReservaDTO(
        List<ReservaProdutoExistenteDTO> produtosExistentes,
        List<ProductRequest> produtosNovos,
        int maoDeObra
) {
}
