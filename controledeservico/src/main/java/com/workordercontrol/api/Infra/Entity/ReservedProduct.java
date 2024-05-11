package com.workordercontrol.api.Infra.Entity;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservedProduct extends Product {

    private int quantidadeNescessaria;

    public ReservedProduct(Product produto, int quantidadeNescessaria){
        super(produto);
        this.quantidadeNescessaria = quantidadeNescessaria;
    }
}
