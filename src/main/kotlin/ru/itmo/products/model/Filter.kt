package ru.itmo.products.model

import jakarta.json.bind.annotation.JsonbCreator
import jakarta.json.bind.annotation.JsonbProperty
import jakarta.validation.constraints.NotNull

data class Filter
@JsonbCreator
constructor(
    @JsonbProperty("fieldType")
    @NotNull
    val fieldType: FieldType,
    @JsonbProperty("value")
    @NotNull
    val value: String
)
