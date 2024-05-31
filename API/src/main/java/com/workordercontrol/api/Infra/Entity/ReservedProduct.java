package com.workordercontrol.api.Infra.Entity;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
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
