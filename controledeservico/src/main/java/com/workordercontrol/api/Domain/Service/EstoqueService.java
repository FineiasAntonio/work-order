package com.workordercontrol.api.Domain.Service;

import com.workordercontrol.api.Exception.CustomExceptions.NotFoundException;
import com.workordercontrol.api.Infra.DTO.Estoque.ProductRequest;
import com.workordercontrol.api.Infra.Entity.Product;
import com.workordercontrol.api.Domain.Mapper.ProductMapper;
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
public class EstoqueService {

    private StorageRepository repository;
    private ProductMapper productMapper;
    private ReservaRepository reservaRepository;

    public List<Product> getAll() {
        return repository.findAll();
    }

    public Product getById(UUID productId) {
        return repository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Product hasn't been found"));
    }

    @Transactional
    public void create(ProductRequest productRequest) {
        Product mappedProduct = productMapper.map(productRequest);

        repository.save(mappedProduct);
        log.info("Product {} sucessful registered with {} units", mappedProduct.getName(), mappedProduct.getQuantity());
    }

    @Transactional
    public void update(UUID productId, ProductRequest productRequest) {
        Product selectedProduct = repository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Product hasn't been found"));
        DataUtils.copyData(productRequest, selectedProduct, "productId");

        repository.save(selectedProduct);
        log.info("Product {} sucessful updated", selectedProduct.getName());
    }

    @Transactional
    public void update(UUID productId, Product productRequest) {
        Product selectedProduct = repository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Product hasn't been found"));
        DataUtils.copyData(productRequest, selectedProduct, "productId");

        repository.save(selectedProduct);
        log.info("Product {} sucessful updated", selectedProduct.getName());
    }

    @Transactional
    public void delete(UUID productId) {

        reservaRepository.findAll().forEach(reserve -> {

            if (reserve.getReservedProducts().entrySet().removeIf(entry -> entry.getValue().getProductId().equals(productId))) {
                throw new RuntimeException("Error occurred while attempting to remove products from reservations");
            }

            reservaRepository.save(reserve);

        });

        repository.deleteById(productId);
        log.info("Product {} sucessful removed", productId);
    }
}
