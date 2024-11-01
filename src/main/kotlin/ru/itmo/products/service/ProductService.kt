package ru.itmo.products.service

import ru.itmo.products.model.FieldType
import ru.itmo.products.model.Filter
import ru.itmo.products.model.PageProduct
import ru.itmo.products.model.Product

interface ProductService {
    fun getProducts(
        page: Int,
        size: Int,
        sortBy: List<FieldType>,
        filter: List<Filter>,
    ): PageProduct

    fun getProductById(id: Long): Product?
}