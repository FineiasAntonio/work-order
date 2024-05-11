package com.workordercontrol.api.Domain.Service;

import com.workordercontrol.api.Exception.CustomExceptions.BadRequestException;
import com.workordercontrol.api.Infra.DTO.Reserva.ReservaProdutoExistenteDTO;
import com.workordercontrol.api.Infra.Entity.Product;
import com.workordercontrol.api.Infra.Entity.ReservedProduct;
import com.workordercontrol.api.Infra.Entity.Reserve;
import com.workordercontrol.api.Exception.CustomExceptions.NotFoundException;
import com.workordercontrol.api.Infra.Repository.StorageRepository;
import com.workordercontrol.api.Infra.Repository.ReservaRepository;

import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Log4j2
@RequiredArgsConstructor
public class ReservaService {

    private ReservaRepository reservaRepository;
    private EstoqueService storageService;

    public List<Reserve> getAll() {
        return reservaRepository.findAll();
    }

    @Transactional
    public void reservarProdutoDoEstoque(int workOrderId, ReservaProdutoExistenteDTO produto) {

        Reserve reserva = reservaRepository.findByWorkOrderId(workOrderId);

        if (!reserva.isActive())
            throw new BadRequestException("Couldn't make a reserve: work order reserve closed");

        //verificação pra ver se há a quantidade no estoque
        Product storagedProduct = storageService.getById(produto.uuidProduto());

        if (storagedProduct.getQuantity() < produto.quantidade()) {
            throw new BadRequestException("Não há quantidade suficiente de %s para ser reservado".formatted(storagedProduct.getName()));

        } else if (reserva.getReservedProducts().values().stream()
                .anyMatch(x -> (x.getProductId().equals(produto.uuidProduto()) && x.getQuantity() + produto.quantidade() > x.getQuantidadeNescessaria()))) {

            throw new BadRequestException("Você está tentando reservar mais do que é nescessário");
        } else {
            storagedProduct.setQuantity(storagedProduct.getQuantity() - produto.quantidade());
            storageService.update(storagedProduct.getProductId(), storagedProduct);
        }

        ReservedProduct reservedProduct = reserva.getReservedProducts().get(produto.uuidProduto());
        reservedProduct.setQuantity(reservedProduct.getQuantity() + produto.quantidade());

        reservaRepository.save(reserva);
        log.info("Reserve for work order {} sucessful updated", workOrderId);
    }

}
