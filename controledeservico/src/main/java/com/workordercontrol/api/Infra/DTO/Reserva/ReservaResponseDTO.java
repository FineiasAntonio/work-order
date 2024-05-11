package com.workordercontrol.api.Infra.DTO.Reserva;

import com.workordercontrol.api.Infra.Entity.ReservedProduct;

import java.util.List;
import java.util.UUID;

public record ReservaResponseDTO(
    UUID uuid,
    List<ReservedProduct> produtos,
    boolean ativo,
    int os,
    String nomeCliente
) {
    
}
