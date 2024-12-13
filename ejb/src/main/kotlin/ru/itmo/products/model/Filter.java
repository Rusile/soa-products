package ru.itmo.products.model;

import jakarta.json.bind.annotation.JsonbCreator;
import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

public class Filter implements Serializable {

    @JsonbProperty("fieldType")
    @NotNull
    private FieldType fieldType;

    @JsonbProperty("value")
    private String value;

    public Filter() {

    }

    @JsonbCreator
    public Filter(FieldType fieldType, String value) {
        this.fieldType = fieldType;
        this.value = value;
    }

    public @NotNull FieldType getFieldType() {
        return fieldType;
    }

    public String getValue() {
        return value;
    }
}

