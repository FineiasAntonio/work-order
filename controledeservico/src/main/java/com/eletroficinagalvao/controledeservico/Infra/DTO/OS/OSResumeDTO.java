package com.eletroficinagalvao.controledeservico.Infra.DTO.OS;

import java.sql.Date;

public record OSResumeDTO(
        int id,
        String nome,
        String equipamento,
        Date dataSaida,
        String servico,
        String funcionario,
        String situacao
) {
}
