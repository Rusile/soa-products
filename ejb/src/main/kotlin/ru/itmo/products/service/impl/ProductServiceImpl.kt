package ru.itmo.products.service.impl

import jakarta.ejb.Stateless
import jakarta.inject.Inject
import jakarta.transaction.Transactional
import org.jooq.Field
import ru.itmo.products.dao.ProductDao
import ru.itmo.products.exception.EntityNotFoundException
import ru.itmo.products.exception.InvalidInputData
import ru.itmo.products.model.FieldType
import ru.itmo.products.model.Filter
import ru.itmo.products.model.PageProduct
import ru.itmo.products.model.Pageable
import ru.itmo.products.model.Product
import ru.itmo.products.model.UnitOfMeasure
import ru.itmo.products.service.ProductService
import ru.itmo.products.util.MapUtils.getGetValueByTableField
import ru.itmo.products.util.MapUtils.getProductTableFieldByCode


@Stateless
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

    @Transactional
    override fun deleteOneProductByUnitOfMeasure(unitOfMeasure: UnitOfMeasure) {
        if (!productDao.existsOneProductByUnitOfMeasure(unitOfMeasure)) {
            throw EntityNotFoundException("Product with unitOfMeasure $unitOfMeasure not found")
        }
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
        val persisted = productDao.getProductById(product.id)!!

        if (persisted.owner == null) {
            product.owner = null
        }

        if (product.owner != null && product.owner.id == null) {
            throw InvalidInputData("Can not update product's owner with empty owner.id")
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