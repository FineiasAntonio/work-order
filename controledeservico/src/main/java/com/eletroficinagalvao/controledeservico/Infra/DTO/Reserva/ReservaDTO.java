package com.eletroficinagalvao.controledeservico.Infra.DTO.Reserva;

import com.eletroficinagalvao.controledeservico.Infra.DTO.Estoque.ProdutoDTO;

import java.util.List;

public record ReservaDTO(
        List<ReservaProdutoExistenteDTO> produtosExistentes,
        List<ProdutoDTO> produtosNovos,
        int maoDeObra
) {
}
