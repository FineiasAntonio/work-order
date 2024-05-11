package com.workordercontrol.api.Infra.DTO.OS;

import com.workordercontrol.api.Infra.DTO.Reserva.ReservaDTO;

import java.sql.Date;
import java.util.UUID;
//TODO: Validation

public record WorkOrderUpdateRequest(
        Date dataSaida,
        String equipamento,
        String numeroSerie,
        String servico,
        String observacao,
        UUID funcionarioId,
        String comentarios,
        boolean concluido,
        ReservaDTO reserva
) {
}
