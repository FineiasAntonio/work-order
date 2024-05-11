package com.workordercontrol.api.Infra.DTO.OS;

import java.sql.Date;

public record WorkOrderSummaryResponse(
        int id,
        String nome,
        String equipamento,
        Date dataSaida,
        String servico,
        String funcionario,
        String situacao
) {
}
