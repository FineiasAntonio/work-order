package com.workordercontrol.api.Domain.Service;

import com.workordercontrol.api.Exception.CustomExceptions.NotFoundException;
import com.workordercontrol.api.Infra.DTO.Estoque.ProductRequest;
import com.workordercontrol.api.Infra.Entity.Product;
import com.workordercontrol.api.Infra.Entity.ReservedProduct;
import com.workordercontrol.api.Infra.Repository.StorageRepository;
import com.workordercontrol.api.Infra.Repository.ReservaRepository;
import com.workordercontrol.api.Util.DataUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Qualifier("EstoqueService")
@Log4j2
@RequiredArgsConstructor
public class StorageService {

    private StorageRepository storageRepository;
    private ReservaRepository reservaRepository;

    public List<Product> getAll() {
        return storageRepository.findAll();
    }

    public Product getById(UUID productId) {
        return storageRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Product hasn't been found"));
    }

    @Transactional
    public Product create(ProductRequest productRequest) {
        Product mappedProduct = Product.builder()
                .name(productRequest.name())
                .reference(productRequest.reference())
                .unitPrice(productRequest.unitPrice())
                .quantity(productRequest.quantity())
                .build();

        Product createdProduct = storageRepository.save(mappedProduct);
        log.info("Product {} sucessful registered with {} units", mappedProduct.getName(), mappedProduct.getQuantity());
        return createdProduct;
    }

    @Transactional
    public void update(UUID productId, ProductRequest productRequest) {
        Product selectedProduct = storageRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Product hasn't been found"));
        DataUtils.copyData(productRequest, selectedProduct, "productId");

        storageRepository.save(selectedProduct);
        log.info("Product {} sucessful updated", selectedProduct.getName());
    }

    @Transactional
    public Product update(UUID productId, Product productRequest) {
        Product selectedProduct = storageRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Product hasn't been found"));
        DataUtils.copyData(productRequest, selectedProduct, "productId");

        Product updatedProduct = storageRepository.save(selectedProduct);
        log.info("Product {} sucessful updated", selectedProduct.getName());
        return updatedProduct;
    }

    @Transactional
    public void delete(UUID productId) {

        reservaRepository.findAll().forEach(reserve -> {

            if (reserve.getReservedProducts().entrySet().removeIf(entry -> entry.getValue().getProductId().equals(productId))) {
                throw new RuntimeException("Error occurred while attempting to remove products from reservations");
            }

            reservaRepository.save(reserve);

        });

        storageRepository.deleteById(productId);
        log.info("Product {} sucessful removed", productId);
    }

    public ReservedProduct reserve(UUID productUuid, int requiredQuantity) {
        Product product = storageRepository.findById(productUuid)
                .orElseThrow(() -> new NotFoundException("Product hasn't been found"));
        product.setQuantity(0);
        return new ReservedProduct(product, requiredQuantity);
    }
}
