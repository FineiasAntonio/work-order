package com.workordercontrol.api.Infra.Entity;

public enum Role {
    ADMIN("Admin"),
    USER("User");

    private final String value;

    Role(String value) {
        this.value = value;
    }
}
