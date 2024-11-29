package ru.itmo.products.util

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import ru.itmo.products.exception.InvalidInputData
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class CustomDateDeserializer: JsonDeserializer<LocalDateTime>() {
    override fun deserialize(jsonparser: JsonParser?, p1: DeserializationContext?): LocalDateTime? {
        val date = jsonparser?.text ?: return null
        try {
            return LocalDateTime.parse(date, dateFormat)
        } catch (e: Exception) {
            throw InvalidInputData("Invalid date $date")
        }
    }

    companion object {
        private val dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
    }
}