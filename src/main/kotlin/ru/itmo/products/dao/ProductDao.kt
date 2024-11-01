package ru.itmo.products.dao

import org.jooq.Field
import ru.itmo.products.model.Product

interface ProductDao {
    fun <T : Any> fetchProducts(
        page: Int,
        size: Int,
        sortBy: Set<Field<*>>,
        filters: Map<Field<T>, T>,
    ): List<Product>

    fun countProducts(): Int

    fun getProductById(id: Long): Product?

    fun deleteProductById(id: Long)

    fun existsById(id: Long): Boolean
}