package com.workordercontrol.api.Infra.DTO.OS;

import com.workordercontrol.api.Infra.DTO.ClientDTO.ClientRequest;
import com.workordercontrol.api.Infra.DTO.Reserva.ReservaDTO;
import com.workordercontrol.api.Infra.Entity.Client;

import java.util.UUID;

//TODO: Validation
public record WorkOrderCreateRequest(
        ClientRequest client,
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
