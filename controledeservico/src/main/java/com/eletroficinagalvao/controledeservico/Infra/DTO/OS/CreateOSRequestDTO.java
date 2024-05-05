package com.eletroficinagalvao.controledeservico.Infra.DTO.OS;

import com.eletroficinagalvao.controledeservico.Infra.DTO.Reserva.ReservaDTO;

import java.util.UUID;

public record CreateOSRequestDTO(
        String nome,
        String cpf,
        String endereco,
        String telefone,
        String dataSaida,
        String equipamento,
        String numeroSerie,
        String servico,
        String observacao,
        UUID funcionarioId,
        String comentarios,
        ReservaDTO reserva
) {
}
