package com.workordercontrol.api.Infra.DTO.Employee;

public record EmployeeRequest(
        String name,
        String number,
        String email
) {
}
