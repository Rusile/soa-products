package ru.itmo.products.controller

import jakarta.inject.Inject
import jakarta.validation.Valid
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.ws.rs.Consumes
import jakarta.ws.rs.DELETE
import jakarta.ws.rs.GET
import jakarta.ws.rs.POST
import jakarta.ws.rs.PUT
import jakarta.ws.rs.Path
import jakarta.ws.rs.PathParam
import jakarta.ws.rs.Produces
import jakarta.ws.rs.QueryParam
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import ru.itmo.products.exception.EntityNotFoundException
import ru.itmo.products.model.FieldType
import ru.itmo.products.model.Filter
import ru.itmo.products.model.Product
import ru.itmo.products.model.UnitOfMeasure
import ru.itmo.products.service.JndiLookupService
import ru.itmo.products.service.ProductService

@Path("/products")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
open class ProductResource {

    @Inject
    private lateinit var jndiLookupService: JndiLookupService

    @Path("/bulk")
    @POST
    open fun getProducts(
        @Valid @Min(0) @QueryParam("page") page: Int?,
        @Valid @Min(0) @QueryParam("size") size: Int?,
        @QueryParam("sortBy") sortBy: List<FieldType>?,
        @Valid filter: List<Filter>?
    ): Response {
        val productService: ProductService = jndiLookupService.getRemoteBean(ProductService::class.java)
        val productsPage = productService.getProducts(
            page ?: 0,
            size ?: 10,
            sortBy ?: listOf(FieldType.ID),
            filter ?: emptyList(),
        )
        return Response.ok(productsPage).build()
    }

    @POST
    open fun createProduct(@Valid product: Product): Response {
        val productService: ProductService = jndiLookupService.getRemoteBean(ProductService::class.java)
        val createdProduct = productService.createProduct(product)
        return Response.ok(createdProduct).build()
    }

    @Path("/{id}")
    @GET
    open fun getProductById(@PathParam("id") id: Long): Response {
        val productService: ProductService = jndiLookupService.getRemoteBean(ProductService::class.java)
        val result = productService.getProductById(id)
            ?: return Response.status(Response.Status.NOT_FOUND).build()

        return Response.ok(result).build()
    }

    @Path("/{id}")
    @DELETE
    open fun deleteProductById(@PathParam("id") id: Long): Response {
        val productService: ProductService = jndiLookupService.getRemoteBean(ProductService::class.java)
        productService.deleteProductById(id)

        return Response.ok().build()
    }

    @Path("/{id}")
    @PUT
    open fun updateProductById(@PathParam("id") id: Long, @Valid product: Product): Response {
        val productService: ProductService = jndiLookupService.getRemoteBean(ProductService::class.java)
        product.id = id
        val result = productService.updateProduct(product)
        return Response.ok(result).build()
    }

    @Path("/unit-of-measure/{unit-of-measure}")
    @DELETE
    open fun deleteByUnitOfMeasure(@PathParam("unit-of-measure") unitOfMeasure: UnitOfMeasure): Response {
        val productService: ProductService = jndiLookupService.getRemoteBean(ProductService::class.java)
        productService.deleteOneProductByUnitOfMeasure(unitOfMeasure)
        return Response.ok().build()
    }

    @Path("/min-price")
    @GET
    open fun getProductWithMinPrice(): Response {
        val productService: ProductService = jndiLookupService.getRemoteBean(ProductService::class.java)
        val result = productService.getProductWithMinPrice()
            ?: throw EntityNotFoundException("No products were found")
        return Response.ok(result).build()
    }

    @Path("/search")
    @GET
    open fun findProductsBySubstring(@QueryParam("substring") @NotNull @NotBlank substring: String): Response {
        val productService: ProductService = jndiLookupService.getRemoteBean(ProductService::class.java)
        val result = productService.findProductsBySubstring(substring)
        if (result.isEmpty()) {
            throw EntityNotFoundException("No products contain such substring")
        }
        return Response.ok(result).build()
    }
}