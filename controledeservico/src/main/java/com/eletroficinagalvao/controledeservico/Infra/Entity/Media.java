package com.eletroficinagalvao.controledeservico.Infra.Entity;

import lombok.Data;

import java.util.List;

@Data
public class Media {

    private String descricao;
    private List<String> links;

}
