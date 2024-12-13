package ru.itmo.products.mapper

import jakarta.ws.rs.core.Response
import jakarta.ws.rs.ext.ExceptionMapper
import jakarta.ws.rs.ext.Provider
import ru.itmo.products.exception.EntityNotFoundException
import ru.itmo.products.exception.IncorrectFilterException
import ru.itmo.products.exception.InvalidInputData
import ru.itmo.products.model.ErrorResponse

@Provider
class CustomExceptionMapper: ExceptionMapper<RuntimeException> {
    override fun toResponse(e: RuntimeException): Response {
        val cause = e.cause
        if (e is EntityNotFoundException || cause is EntityNotFoundException) {
            return Response.status(404)
                .entity(ErrorResponse(cause?.message ?: e.message))
                .build()
        }

        if (e is IncorrectFilterException || e is InvalidInputData
            || cause is IncorrectFilterException || cause is InvalidInputData) {
            return Response.status(400)
                .entity(ErrorResponse(cause?.message ?: e.message))
                .build()
        }

        return Response.status(500)
            .entity(ErrorResponse(cause?.message ?: e.message))
            .build()
    }
}