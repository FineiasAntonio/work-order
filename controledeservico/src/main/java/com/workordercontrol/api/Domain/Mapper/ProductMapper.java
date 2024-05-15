package com.workordercontrol.api.Domain.Mapper;

import com.workordercontrol.api.Exception.CustomExceptions.BadRequestException;
import com.workordercontrol.api.Exception.CustomExceptions.NotFoundException;
import com.workordercontrol.api.Infra.DTO.Estoque.ProductRequest;
import com.workordercontrol.api.Infra.Repository.StorageRepository;
import com.workordercontrol.api.Infra.Entity.Product;
import com.workordercontrol.api.Infra.Entity.ReservedProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ProductMapper {

    @Autowired
    private StorageRepository produtoRepository;

    // mapeia pro estoque
    public Product map(ProductRequest dto) {
        return new Product(
                dto.name(),
                dto.reference(),
                dto.quantity(),
                dto.unitPrice()
        );
    }

    // mapeia para a reserva
//    public ReservedProduct mapReserva(ProductRequest dto) {
//        Product produto = produtoRepository.findByNameLike(dto.name())
//                .orElseGet(() -> {
//                    Product supplier = map(dto);
//                    supplier.setQuantity(0);
//                    produtoRepository.save(supplier);
//                    return supplier;
//                });
//
//        int quantidadeNescessaria = dto.quantity();
//
//        return new ReservedProduct(produto, quantidadeNescessaria);
//    }

    // pega do estoque e faz uma nova instancia de produto reservado
    public ReservedProduct reservar(UUID uuidProduto, int quantidadeNescessaria) {
        Product produto = produtoRepository.findById(uuidProduto)
                .orElseThrow(() -> new NotFoundException("Produto não encontrado"));
        produto.setQuantity(0);
        return new ReservedProduct(produto, quantidadeNescessaria);
    }

}
