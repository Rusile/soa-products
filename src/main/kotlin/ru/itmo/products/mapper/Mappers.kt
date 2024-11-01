package ru.itmo.products.mapper

import org.jooq.Record
import ru.itmo.products.generated.jooq.Tables.PERSONS
import ru.itmo.products.generated.jooq.Tables.PRODUCTS
import ru.itmo.products.model.*
import java.time.ZoneOffset

object Mappers {

    fun Record.toPerson() : Person {
        return Person(
            this[PERSONS.ID],
            this[PERSONS.NAME],
            this[PERSONS.BIRTHDATE]?.atOffset(ZoneOffset.UTC),
            this[PERSONS.WEIGHT],
            this[PERSONS.EYE_COLOR]?.let { Color.valueOf(it) },
            this[PERSONS.EYE_COLOR].let { Color.valueOf(it) }
        )
    }

    fun Record.toProduct(): Product {
        return Product(
            this[PRODUCTS.ID],
            this[PRODUCTS.NAME],
            Coordinates(
                this[PRODUCTS.COORDINATE_X],
                this[PRODUCTS.COORDINATE_Y],
            ),
            this[PRODUCTS.CREATION_DATE].atOffset(ZoneOffset.UTC),
            this[PRODUCTS.PRICE],
            this[PRODUCTS.PART_NUMBER],
            this[PRODUCTS.UNIT_OF_MEASURE].let { UnitOfMeasure.valueOf(it) },
            this[PERSONS.ID]?.let { this.toPerson() }
        )
    }
}