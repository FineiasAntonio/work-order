package com.workordercontrol.api.Infra.DTO.OS;

import java.sql.Date;

public record WorkOrderSummaryResponse(
        int workOrderId,
        String name,
        String equipment,
        Date exceptedDate,
        String service,
        String employeeId,
        String status
) {
}
