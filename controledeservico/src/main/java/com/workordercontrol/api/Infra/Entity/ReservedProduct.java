package com.workordercontrol.api.Infra.Entity;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservedProduct extends Product {

    private int requiredQuantity;

    public ReservedProduct(Product produto, int requiredQuantity){
        super(produto);
        this.requiredQuantity = requiredQuantity;
    }
}
