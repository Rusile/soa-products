package ru.itmo.products.service

import jakarta.ejb.Remote
import ru.itmo.products.model.FieldType
import ru.itmo.products.model.Filter
import ru.itmo.products.model.PageProduct
import ru.itmo.products.model.Product
import ru.itmo.products.model.UnitOfMeasure

@Remote
interface ProductService {
    fun getProducts(
        page: Int,
        size: Int,
        sortBy: List<FieldType>,
        filter: List<Filter>,
    ): PageProduct

    fun getProductById(id: Long): Product?

    fun deleteProductById(id: Long)

    fun deleteOneProductByUnitOfMeasure(unitOfMeasure: UnitOfMeasure)

    fun getProductWithMinPrice(): Product?

    fun findProductsBySubstring(substring: String): List<Product>

    fun createProduct(product: Product): Product

    fun updateProduct(product: Product): Product
}