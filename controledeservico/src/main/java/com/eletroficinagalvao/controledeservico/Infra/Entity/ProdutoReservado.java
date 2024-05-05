package com.eletroficinagalvao.controledeservico.Infra.Entity;

import lombok.*;

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
