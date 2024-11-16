package ru.itmo.products.dao.impl

import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional
import org.jooq.DSLContext
import org.jooq.Field
import org.jooq.SQLDialect
import org.jooq.SelectFieldOrAsterisk
import org.jooq.conf.Settings
import org.jooq.impl.DSL.*
import org.jooq.impl.DefaultConfiguration
import org.jooq.impl.DefaultConnectionProvider
import ru.itmo.products.dao.ProductDao
import ru.itmo.products.generated.jooq.Tables.PERSONS
import ru.itmo.products.generated.jooq.Tables.PRODUCTS
import ru.itmo.products.mapper.Mappers.toPerson
import ru.itmo.products.mapper.Mappers.toProduct
import ru.itmo.products.model.Person
import ru.itmo.products.model.Product
import ru.itmo.products.model.UnitOfMeasure
import java.sql.DriverManager.getConnection
import java.time.LocalDateTime


@ApplicationScoped
open class ProductDaoImpl : ProductDao {
    private val dsl: DSLContext
    init {
        Class.forName("org.postgresql.Driver")
        val c = getConnection(
            "jdbc:postgresql:postgres",
            "postgres",
            "postgres"
        )
        dsl = using(
            DefaultConfiguration()
                .set(DefaultConnectionProvider(c))
                .set(SQLDialect.POSTGRES)
                .set(
                    Settings()
                        .withExecuteLogging(false)
                )
        )
    }

    @Transactional
    override fun <T : Any> fetchProducts(
        page: Int,
        size: Int,
        sortBy: Set<Field<*>>,
        filters: Map<Field<T>, T>
    ): List<Product> {
        val query = dsl.select(ALL_FIELDS).from(
            PRODUCTS.leftJoin(PERSONS).on(PRODUCTS.PERSON_ID.eq(PERSONS.ID))
        )
        var condition = noCondition()

        filters.entries.forEach {
            condition = condition.and(it.key.eq(it.value))
        }

        sortBy.forEach {
            query.orderBy(it)
        }

        return query.where(condition)
            .limit(size)
            .offset(page * size)
            .fetch().map { it.toProduct() }
    }

    @Transactional
    override fun countProducts(): Int {
        return dsl.fetchCount(dsl.selectFrom(PRODUCTS))
    }

    @Transactional
    override fun getProductById(id: Long): Product? {
        return dsl.select(ALL_FIELDS)
            .from(
                PRODUCTS.leftJoin(PERSONS).on(PRODUCTS.PERSON_ID.eq(PERSONS.ID))
            )
            .where(PRODUCTS.ID.eq(id))
            .fetchOne()
            ?.map { it.toProduct() }
    }

    @Transactional
    override fun deleteProductById(id: Long) {
        dsl.deleteFrom(PRODUCTS).where(PRODUCTS.ID.eq(id)).execute()
    }

    @Transactional
    override fun existsById(id: Long): Boolean {
        return dsl.fetchExists(
            selectOne().from(PRODUCTS)
                .where(PRODUCTS.ID.eq(id))
        )
    }

    @Transactional
    override fun deleteOneProductByUnitOfMeasure(unitOfMeasure: UnitOfMeasure) {
        dsl.deleteFrom(PRODUCTS)
            .where(PRODUCTS.UNIT_OF_MEASURE.eq(unitOfMeasure.name))
            .limit(1)
            .execute()
    }

    @Transactional
    override fun existsOneProductByUnitOfMeasure(unitOfMeasure: UnitOfMeasure): Boolean {
        return dsl.fetchExists(
            selectOne().from(PRODUCTS)
                .where(PRODUCTS.UNIT_OF_MEASURE.eq(unitOfMeasure.name))
        )
    }

    @Transactional
    override fun getProductWithMinPrice(): Product? {
        return dsl.select(ALL_FIELDS)
            .from(PRODUCTS.leftJoin(PERSONS).on(PRODUCTS.PERSON_ID.eq(PERSONS.ID)))
            .orderBy(PRODUCTS.PRICE.asc())
            .limit(1)
            .fetchOne()
            ?.map { it.toProduct() }
    }

    @Transactional
    override fun findProductsBySubstring(substring: String): List<Product> {
        return dsl.select(ALL_FIELDS)
            .from(PRODUCTS.leftJoin(PERSONS).on(PRODUCTS.PERSON_ID.eq(PERSONS.ID)))
            .where(lower(PRODUCTS.NAME).contains(substring.lowercase()))
            .fetch()
            .map { it.toProduct() }
    }

    @Transactional
    override fun insertProduct(product: Product): Product {
        val owner: Person? = product.owner

        val persistedOwner = if (owner != null) {
                 dsl.insertInto(PERSONS)
                .set(PERSONS.WEIGHT, owner.weight)
                .set(PERSONS.NAME, owner.name)
                .set(PERSONS.BIRTHDATE, owner.birthday)
                .set(PERSONS.EYE_COLOR, owner.eyeColor?.name)
                .set(PERSONS.HAIR_COLOR, owner.hairColor.name)
                .returning()
                .fetchOne()
                ?.toPerson() ?: error("Person with name ${product.owner.name} could not be inserted")
        } else null

        val persistedProduct = dsl.insertInto(PRODUCTS)
            .set(PRODUCTS.NAME, product.name)
            .set(PRODUCTS.PERSON_ID, persistedOwner?.id)
            .set(PRODUCTS.PRICE, product.price)
            .set(PRODUCTS.PART_NUMBER, product.partNumber)
            .set(PRODUCTS.UNIT_OF_MEASURE, product.unitOfMeasure.name)
            .set(PRODUCTS.CREATION_DATE, LocalDateTime.now())
            .set(PRODUCTS.COORDINATE_X, product.coordinates.x)
            .set(PRODUCTS.COORDINATE_Y, product.coordinates.y)
            .returning()
            .fetchOne()
            ?.toProduct() ?: error("Product with name ${product.name} could not be inserted")

        persistedProduct.owner = persistedOwner
        return persistedProduct
    }

    @Transactional
    override fun updateProduct(product: Product): Product {
        val owner: Person? = product.owner

        val persistedOwner = if (owner != null) {
            dsl.update(PERSONS)
                .set(PERSONS.WEIGHT, owner.weight)
                .set(PERSONS.NAME, owner.name)
                .set(PERSONS.BIRTHDATE, owner.birthday)
                .set(PERSONS.EYE_COLOR, owner.eyeColor?.name)
                .set(PERSONS.HAIR_COLOR, owner.hairColor.name)
                .where(PERSONS.ID.eq(owner.id))
                .returning()
                .fetchOne()
                ?.toPerson() ?: error("Person with name ${product.owner.name} could not be updated")
        } else null

        val persistedProduct = dsl.update(PRODUCTS)
            .set(PRODUCTS.NAME, product.name)
            .set(PRODUCTS.PERSON_ID, persistedOwner?.id)
            .set(PRODUCTS.PRICE, product.price)
            .set(PRODUCTS.PART_NUMBER, product.partNumber)
            .set(PRODUCTS.UNIT_OF_MEASURE, product.unitOfMeasure.name)
            .set(PRODUCTS.COORDINATE_X, product.coordinates.x)
            .set(PRODUCTS.COORDINATE_Y, product.coordinates.y)
            .where(PRODUCTS.ID.eq(product.id))
            .returning()
            .fetchOne()
            ?.toProduct() ?: error("Product with name ${product.name} could not be updated")

        persistedProduct.owner = persistedOwner
        return persistedProduct
    }

    companion object {
        private val ALL_FIELDS: Set<SelectFieldOrAsterisk> = setOf(
            PRODUCTS.ID,
            PRODUCTS.NAME,
            PRODUCTS.COORDINATE_X,
            PRODUCTS.COORDINATE_Y,
            PRODUCTS.CREATION_DATE,
            PRODUCTS.PRICE,
            PRODUCTS.PART_NUMBER,
            PRODUCTS.UNIT_OF_MEASURE,
            PERSONS.ID,
            PERSONS.NAME,
            PERSONS.WEIGHT,
            PERSONS.BIRTHDATE,
            PERSONS.EYE_COLOR,
            PERSONS.HAIR_COLOR
        )
    }
}