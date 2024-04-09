package com.eletroficinagalvao.controledeservico.Domain.Entity;

import lombok.Data;

import java.util.List;

@Data
public class Media {

    private String descricao;
    private List<String> links;

}
