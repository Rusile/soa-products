package ru.itmo.products.service.impl

import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import jakarta.transaction.Transactional
import org.jooq.Field
import ru.itmo.products.dao.ProductDao
import ru.itmo.products.exception.EntityNotFoundException
import ru.itmo.products.model.*
import ru.itmo.products.service.ProductService
import ru.itmo.products.util.MapUtils.getGetValueByTableField
import ru.itmo.products.util.MapUtils.getProductTableFieldByCode


@ApplicationScoped
open class ProductServiceImpl
@Inject
constructor(
    private val productDao: ProductDao,
) : ProductService {

    @Transactional
    override fun getProducts(
        page: Int,
        size: Int,
        sortBy: List<FieldType>,
        filter: List<Filter>,
    ): PageProduct {
        val products = productDao.fetchProducts(
            page,
            size,
            parseSortBy(sortBy),
            parseFilter(filter)
        )
        val totalElements = productDao.countProducts()
        val totalPages = (totalElements + size - 1) / size

        return PageProduct(
            products,
            Pageable(page, size),
            totalPages,
            totalElements
        )
    }

    override fun getProductById(id: Long): Product? {
        return productDao.getProductById(id)
    }

    @Transactional
    override fun deleteProductById(id: Long) {
        if (!productDao.existsById(id)) {
            throw EntityNotFoundException("Product with id $id not found")
        }
        productDao.deleteProductById(id)
    }

    override fun deleteOneProductByUnitOfMeasure(unitOfMeasure: UnitOfMeasure) {
        productDao.deleteOneProductByUnitOfMeasure(unitOfMeasure)
    }

    override fun getProductWithMinPrice() = productDao.getProductWithMinPrice()

    override fun findProductsBySubstring(substring: String) = productDao.findProductsBySubstring(substring)

    override fun createProduct(product: Product) = productDao.insertProduct(product)

    @Transactional
    override fun updateProduct(product: Product): Product {
        if (!productDao.existsById(product.id)) {
            throw EntityNotFoundException("Product with id ${product.id} not found")
        }
        return productDao.updateProduct(product)
    }

    private fun parseSortBy(sortBy: List<FieldType>): Set<Field<*>> {
        return sortBy.map {
            getProductTableFieldByCode(it)
        }.toSet()
    }

    private fun <T : Any> parseFilter(filters: List<Filter>): Map<Field<T>, T> {
        return filters.associate {
            getProductTableFieldByCode(it.fieldType) to getGetValueByTableField(
                getProductTableFieldByCode(it.fieldType),
                it.value
            )
        } as Map<Field<T>, T>
    }
}