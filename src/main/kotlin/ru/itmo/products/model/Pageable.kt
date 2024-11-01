package ru.itmo.products.model

import jakarta.json.bind.annotation.JsonbProperty

data class Pageable(
    @JsonbProperty("pageNumber")
    val pageNumber: Int,
    @JsonbProperty("pageSize")
    val pageSize: Int
)
