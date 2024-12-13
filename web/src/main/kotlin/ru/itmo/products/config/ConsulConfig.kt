package ru.itmo.products.config

import com.google.common.net.HostAndPort
import com.orbitz.consul.Consul
import com.orbitz.consul.model.agent.ImmutableRegistration
import com.orbitz.consul.model.agent.Registration
import jakarta.annotation.PostConstruct
import jakarta.annotation.PreDestroy
import jakarta.enterprise.context.ApplicationScoped
import java.util.*


@ApplicationScoped
class ConsulConfig {
    private val serviceId = "products-service"
    private var client: Consul = Consul.builder()
        .withHostAndPort(HostAndPort.fromParts("consul", 8500)).build()

    @PostConstruct
    fun init() {
        val agentClient = client.agentClient()
        val service: Registration = ImmutableRegistration.builder()
            .id(serviceId)
            .name(serviceId)
            .port(8443)
            .address("products-service")
            .tags(listOf("products-service"))
            .meta(Collections.singletonMap("version", "1.0"))
            .build()

        agentClient.register(service)
    }

    @PreDestroy
    private fun deregisterService() {
        client.agentClient().deregister(serviceId)
    }
}