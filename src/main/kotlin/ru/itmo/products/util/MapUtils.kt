package ru.itmo.products.util

import org.jooq.Field
import ru.itmo.products.exception.IncorrectFilterException
import ru.itmo.products.generated.jooq.Tables.PERSONS
import ru.itmo.products.generated.jooq.Tables.PRODUCTS
import ru.itmo.products.model.Color
import ru.itmo.products.model.FieldType
import ru.itmo.products.model.UnitOfMeasure
import java.time.OffsetDateTime

object MapUtils {

    fun getProductTableFieldByCode(code: FieldType): Field<*> {
        return when (code) {
            FieldType.ID -> PRODUCTS.ID
            FieldType.NAME -> PRODUCTS.NAME
            FieldType.COORDINATES_X -> PRODUCTS.COORDINATE_X
            FieldType.COORDINATES_Y -> PRODUCTS.COORDINATE_Y
            FieldType.CREATION_DATE -> PRODUCTS.CREATION_DATE
            FieldType.PRICE -> PRODUCTS.PRICE
            FieldType.PART_NUMBER -> PRODUCTS.PART_NUMBER
            FieldType.UNIT_OF_MEASURE -> PRODUCTS.UNIT_OF_MEASURE
            FieldType.OWNER_NAME-> PERSONS.NAME
            FieldType.OWNER_BIRTHDAY -> PERSONS.BIRTHDATE
            FieldType.OWNER_WEIGHT -> PERSONS.WEIGHT
            FieldType.OWNER_EYE_COLOR -> PERSONS.EYE_COLOR
            FieldType.OWNER_HAIR_COLOR -> PERSONS.HAIR_COLOR
        }
    }

    fun getGetValueByTableField(field: Field<*>, value: String): Any? {
        return try {
            when (field) {
                PRODUCTS.ID -> value.toLongOrNull()
                PRODUCTS.NAME -> value
                PRODUCTS.COORDINATE_X -> value.toDoubleOrNull()
                PRODUCTS.COORDINATE_Y -> value.toDoubleOrNull()
                PRODUCTS.CREATION_DATE -> OffsetDateTime.parse(value)
                PRODUCTS.PRICE -> value.toDoubleOrNull()
                PRODUCTS.PART_NUMBER -> value
                PRODUCTS.UNIT_OF_MEASURE -> UnitOfMeasure.valueOf(value)
                PERSONS.NAME -> value
                PERSONS.BIRTHDATE -> OffsetDateTime.parse(value)
                PERSONS.WEIGHT -> value.toDoubleOrNull()
                PERSONS.EYE_COLOR -> Color.valueOf(value)
                PERSONS.HAIR_COLOR -> Color.valueOf(value)
                else -> error("Not all field types provided")
            }
        } catch (e: Exception) {
            throw IncorrectFilterException("Incorrect value for some filter")
        }
    }
}