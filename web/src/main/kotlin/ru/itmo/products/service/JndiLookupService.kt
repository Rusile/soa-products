package ru.itmo.products.service

import jakarta.enterprise.context.ApplicationScoped
import javax.naming.InitialContext


@ApplicationScoped
open class JndiLookupService {

    private val context = InitialContext()

    /**
     * @param clazz remote интерфейс EJB бина
     * @return Реализация бина с постфиксом Impl
     */
    open fun <T> getRemoteBean(clazz: Class<T>): T {
        val moduleName = "products-ejb-1.0-SNAPSHOT"
        val beanName = clazz.simpleName
        val viewClassName = clazz.name
        val toLookup = "ejb:/${moduleName}/${beanName}Impl!${viewClassName}"
        return context.lookup(toLookup) as T
    }
}