package com.workordercontrol.api.Infra.Entity;

import java.util.Arrays;

public enum Status {
    CONCLUIDO( 0),
    EM_ANDAMENTO(1),
    AGUARDANDO_PECA(2);

    private final int valor;

    Status(int i) {
        this.valor = i;
    }

    public static Status getStatus(int i){
        return Arrays.stream(Status.values()).filter(e -> e.valor == i).findFirst().get();
    }
}
