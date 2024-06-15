package com.workordercontrol.api.Infra.DTO;

public record LoginRequest(
        String email,
        String password
) {
}
