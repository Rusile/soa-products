package ru.itmo.products.dao.impl

import jakarta.enterprise.context.ApplicationScoped
import org.jooq.DSLContext
import org.jooq.Field
import org.jooq.SQLDialect
import org.jooq.conf.Settings
import org.jooq.impl.DSL
import org.jooq.impl.DSL.noCondition
import org.jooq.impl.DefaultConfiguration
import org.jooq.impl.DefaultConnectionProvider
import ru.itmo.products.dao.ProductDao
import ru.itmo.products.generated.jooq.Tables.PERSONS
import ru.itmo.products.generated.jooq.Tables.PRODUCTS
import ru.itmo.products.mapper.Mappers.toProduct
import ru.itmo.products.model.Product
import java.sql.DriverManager.getConnection


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
        dsl = DSL.using(
                DefaultConfiguration()
                    .set(DefaultConnectionProvider(c))
                    .set(SQLDialect.POSTGRES)
                    .set(
                        Settings()
                            .withExecuteLogging(false)
                    )
            )
    }

    override fun <T : Any> fetchProducts(
        page: Int,
        size: Int,
        sortBy: Set<Field<*>>,
        filters: Map<Field<T>, T>,
    ): List<Product> {
        val query = dsl.select(
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
        ).from(
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

    override fun countProducts(): Int {
        return dsl.fetchCount(dsl.selectFrom(PRODUCTS))
    }
}