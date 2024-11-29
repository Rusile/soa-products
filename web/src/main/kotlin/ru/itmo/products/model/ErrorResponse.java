package ru.itmo.products.model;

import jakarta.json.bind.annotation.JsonbCreator;
import jakarta.json.bind.annotation.JsonbProperty;

public class ErrorResponse {

    @JsonbProperty("cause")
    private String cause;

    public ErrorResponse() {

    }

    @JsonbCreator
    public ErrorResponse(String cause) {
        this.cause = cause;
    }

    public String getCause() {
        return cause;
    }
}
