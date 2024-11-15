package ru.itmo.products.util

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class CustomDateSerializer : JsonSerializer<LocalDateTime>() {

    override fun serialize(date: LocalDateTime?, jsonGenerator: JsonGenerator, serializerProvider: SerializerProvider?) {
        if (date == null) {
            jsonGenerator.writeNull()
            return
        }
        val formattedDate = dateFormat.format(date)
        jsonGenerator.writeString(formattedDate)
    }

    companion object {
        private val dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
    }
}