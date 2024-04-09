package com.eletroficinagalvao.controledeservico.Domain.DTO.OS;

import com.eletroficinagalvao.controledeservico.Domain.DTO.Reserva.ReservaDTO;
import com.eletroficinagalvao.controledeservico.Domain.Entity.SubSituacao;

import java.sql.Date;
import java.util.UUID;

public record UpdateOSRequestDTO(
        String nome,
        String cpf,
        String endereco,
        String telefone,
        Date dataSaida,
        String equipamento,
        String numeroSerie,
        String servico,
        String observacao,
        UUID funcionarioId,
        String comentarios,
        boolean concluido,
        SubSituacao subSituacao,
        ReservaDTO reserva
) {
}
