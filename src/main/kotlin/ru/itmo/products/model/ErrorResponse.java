package ru.itmo.products.model;

import jakarta.json.bind.annotation.JsonbCreator;
import jakarta.json.bind.annotation.JsonbProperty;

public class ErrorResponse {

    @JsonbProperty("cause")
    private final String cause;

    @JsonbCreator
    public ErrorResponse(String cause) {
        this.cause = cause;
    }

    public String getCause() {
        return cause;
    }
}
