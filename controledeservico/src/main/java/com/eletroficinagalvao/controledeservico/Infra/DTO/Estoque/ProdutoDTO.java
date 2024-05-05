package com.eletroficinagalvao.controledeservico.Infra.DTO.Estoque;

public record ProdutoDTO(
        String produto,
        String referencia,
        int quantidade,
        double precoUnitario
) {}
