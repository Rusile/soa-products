package ru.itmo.products.dao

import org.jooq.Field
import ru.itmo.products.model.Product
import ru.itmo.products.model.UnitOfMeasure

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

    fun deleteOneProductByUnitOfMeasure(unitOfMeasure: UnitOfMeasure)

    fun getProductWithMinPrice(): Product?

    fun findProductsBySubstring(substring: String): List<Product>

    fun insertProduct(product: Product): Product

    fun updateProduct(product: Product): Product
}