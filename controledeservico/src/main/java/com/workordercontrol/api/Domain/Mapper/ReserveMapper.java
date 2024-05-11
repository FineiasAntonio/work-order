package com.workordercontrol.api.Domain.Mapper;

import com.workordercontrol.api.Infra.DTO.Reserva.ReservaDTO;
import com.workordercontrol.api.Infra.DTO.Reserva.ReservaProdutoExistenteDTO;
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
    private ProductMapper productMapper;
    @Autowired
    private ReservaRepository reservaRepository;

    public Reserve criarReserva(ReservaDTO reserva) {

        if (reserva.produtosExistentes().isEmpty()) {
            return new Reserve(reserva.maoDeObra());
        }

        Map<UUID, ReservedProduct> produtosReservados = new HashMap<>(
                reserva.produtosExistentes()
                        .stream()
                        .collect(Collectors.toMap(
                                ReservaProdutoExistenteDTO::uuidProduto,
                                e -> productMapper.reservar(e.uuidProduto(), e.quantidade())
                        ))
        );

        return new Reserve(produtosReservados, reserva.maoDeObra());
    }

    public Reserve atualizarReserva(Reserve reserve, ReservaDTO reservaAtualizada) {

        if (!reservaAtualizada.produtosExistentes().isEmpty()) {
            reservaAtualizada.produtosExistentes()
                    .stream()
                    .map(e -> productMapper.reservar(e.uuidProduto(), e.quantidade()))
                    .forEach(product -> {
                        reserve.getReservedProducts().put(product.getProductId(), product);
                    });
        }

        reserve.setMaoDeObra(reservaAtualizada.maoDeObra());

        reserve.setActive(!reserve.getReservedProducts().isEmpty() && !reserve.getReservedProducts().values().stream().allMatch(e -> e.getQuantity() == e.getQuantidadeNescessaria()));
        return reserve;
    }

}
