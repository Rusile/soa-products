package ru.itmo.products

import jakarta.ws.rs.ApplicationPath
import jakarta.ws.rs.core.Application

@ApplicationPath("/api")
class HelloApplication : Application() {

}