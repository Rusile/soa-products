package ru.itmo.products.controller

import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import ru.itmo.products.model.FieldType
import ru.itmo.products.model.Filter
import ru.itmo.products.service.ProductService

@Path("/products")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
open class ProductResource {

    @Inject
    private lateinit var productService: ProductService

    @GET
    open fun getProducts(
        @QueryParam("page") page: Int?,
        @QueryParam("size") size: Int?,
        @QueryParam("sortBy") sortBy: List<FieldType>?,
        filter: List<Filter>?
    ): Response {
        val productsPage = productService.getProducts(
            page ?: 0,
            size ?: 10,
            sortBy ?: emptyList(),
            filter ?: emptyList(),
        )
        return Response.ok(productsPage).build()
    }

    @Path("/{id}")
    @GET
    open fun getProductById(@PathParam("id") id: Long): Response {
        val result = productService.getProductById(id)
            ?: return Response.status(Response.Status.NOT_FOUND).build()

        return Response.ok(result).build()
    }
}