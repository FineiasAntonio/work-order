package com.workordercontrol.api.Domain.Service;

import com.workordercontrol.api.Exception.CustomExceptions.BadRequestException;
import com.workordercontrol.api.Infra.DTO.Reserva.ReserveProductsRequest;
import com.workordercontrol.api.Infra.Entity.Product;
import com.workordercontrol.api.Infra.Entity.ReservedProduct;
import com.workordercontrol.api.Infra.Entity.Reserve;
import com.workordercontrol.api.Infra.Repository.ReserveRepository;

import java.util.List;

import com.workordercontrol.api.Infra.Repository.StorageRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Log4j2
public class ReserveService {

    @Autowired
    private ReserveRepository reserveRepository;
    @Autowired
    private StorageRepository storageRepository;

    public List<Reserve> getAll() {
        return reserveRepository.findAll();
    }

    @Transactional
    public void reserveProductFromStorage(int workOrderId, ReserveProductsRequest productRequest) {

        Reserve reserve = reserveRepository.findByWorkOrderId(workOrderId);

        if (!reserve.isActive())
            throw new BadRequestException("Couldn't make a reserve: work order reserve is closed");

        Product storagedProduct = storageRepository.findById(productRequest.productId()).get();
        if (storagedProduct.getQuantity() < productRequest.quantity()) {
            throw new BadRequestException("There isn't enough quantity of %s for to reserve".formatted(storagedProduct.getName()));

        } else if (reserve.getReservedProducts().values()
                .stream()
                .anyMatch(x -> (x.getProductId().equals(productRequest.productId()) && x.getQuantity() + productRequest.quantity() > x.getRequiredQuantity()))) {
            throw new BadRequestException("You're trying to reserve more than required quantity");
        } else {
            storagedProduct.setQuantity(storagedProduct.getQuantity() - productRequest.quantity());
            storageRepository.save(storagedProduct);
        }

        ReservedProduct reservedProduct = reserve.getReservedProducts().get(productRequest.productId());
        reservedProduct.setQuantity(reservedProduct.getQuantity() + productRequest.quantity());

        reserveRepository.save(reserve);
        log.info("Reserve for work order {} sucessful updated", workOrderId);
    }

}
