package com.eletroficinagalvao.controledeservico.Domain.Entity;

import jakarta.persistence.Entity;
import lombok.*;
import org.springframework.data.annotation.Id;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoReservado extends Produto{

    int quantidadeNescessaria;

    public ProdutoReservado(Produto produto, int quantidadeNescessaria){
        super(produto);
        this.quantidadeNescessaria = quantidadeNescessaria;
    }
}
