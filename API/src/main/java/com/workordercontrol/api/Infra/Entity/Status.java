package com.workordercontrol.api.Infra.Entity;

import java.util.Arrays;

public enum Status {
    FINISHED( 0),
    ONGOING(1),
    AWATING_PARTS(2);

    private final int value;

    Status(int i) {
        this.value = i;
    }

    public static Status getStatus(int i){
        return Arrays.stream(Status.values()).filter(e -> e.value == i).findFirst().get();
    }
}
