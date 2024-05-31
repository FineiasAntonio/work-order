package com.workordercontrol.api.Infra.DTO.ClientDTO;

import jakarta.annotation.Nullable;

import java.util.UUID;

public record ClientRequest(
        @Nullable
        UUID clientId,
        String name,
        String number,
        String identity,
        String email

) {
}
