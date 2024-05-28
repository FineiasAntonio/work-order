package com.workordercontrol.api.Domain.Mapper;

import com.workordercontrol.api.Domain.Service.StorageService;
import com.workordercontrol.api.Infra.DTO.Reserva.ReserveRequest;
import com.workordercontrol.api.Infra.DTO.Reserva.ReserveProductsRequest;
import com.workordercontrol.api.Infra.Repository.ReservaRepository;
import com.workordercontrol.api.Infra.Entity.ReservedProduct;
import com.workordercontrol.api.Infra.Entity.Reserve;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class ReserveMapper {

    @Autowired
    private StorageService storageService;
    @Autowired
    private ReservaRepository reservaRepository;

    public Reserve createReserve(ReserveRequest reserve) {

        if (reserve.products().isEmpty()) {
            return new Reserve(reserve.labor());
        }

        Map<UUID, ReservedProduct> produtosReservados = new HashMap<>(
                reserve.products()
                        .stream()
                        .collect(Collectors.toMap(
                                ReserveProductsRequest::productId,
                                e -> storageService.reserve(e.productId(), e.quantity())
                        ))
        );

        return new Reserve(produtosReservados, reserve.labor());
    }

    public Reserve updateReserve(Reserve reserve, ReserveRequest updatedReserveRequest) {

        if (!updatedReserveRequest.products().isEmpty()) {
            updatedReserveRequest.products()
                    .stream()
                    .map(e -> storageService.reserve(e.productId(), e.quantity()))
                    .forEach(product -> {
                        reserve.getReservedProducts().put(product.getProductId(), product);
                    });
        }

        reserve.setLabor(updatedReserveRequest.labor());

        reserve.setActive(!reserve.getReservedProducts().isEmpty() && !reserve.getReservedProducts().values().stream().allMatch(e -> e.getQuantity() == e.getRequiredQuantity()));
        return reserve;
    }

}
