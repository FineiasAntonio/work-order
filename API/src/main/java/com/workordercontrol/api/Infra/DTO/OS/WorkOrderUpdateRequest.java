package com.workordercontrol.api.Infra.DTO.OS;

import com.workordercontrol.api.Infra.DTO.Reserva.ReserveRequest;

import java.sql.Date;
import java.util.UUID;

public record WorkOrderUpdateRequest(
        Date exceptedDate,
        String equipment,
        String serialNumber,
        String service,
        boolean finished,
        String notes,
        UUID employeeId,
        ReserveRequest reserve
) {
}
