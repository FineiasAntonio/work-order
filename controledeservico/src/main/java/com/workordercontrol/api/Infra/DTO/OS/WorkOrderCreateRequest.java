package com.workordercontrol.api.Infra.DTO.OS;

import com.workordercontrol.api.Infra.DTO.ClientDTO.ClientRequest;
import com.workordercontrol.api.Infra.DTO.Reserva.ReserveRequest;

import java.sql.Date;
import java.util.UUID;

public record WorkOrderCreateRequest(
        ClientRequest client,
        Date exceptedDate,
        String equipment,
        String serialNumber,
        String service,
        String notes,
        UUID employeeId,
        ReserveRequest reserve
) {
}
