package ru.itmo.products.controller

import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces

@Path("/ping")
open class PingResource {
    @GET
    @Produces("text/plain")
    open fun ping(): String {
        return "pong"
    }
}