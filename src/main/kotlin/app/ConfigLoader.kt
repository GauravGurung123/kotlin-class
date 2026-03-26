package app

import java.io.File
import kotlin.reflect.KClass
import kotlin.reflect.full.primaryConstructor

// Generic function to load classes from config and auto-inject dependencies
fun <T : Any> loadFromConfig(configFile: String, packagePrefix: String): List<T> =
    File(configFile).readLines()
        .map {
            it.substringBefore("#")
                .substringBefore("//")
                .trim()
        }
        .filter { it.isNotEmpty() }
        .mapNotNull { className ->
            try {
                // Load KClass from className
                val kClass = Class.forName("$packagePrefix.$className").kotlin
                // Create instance with DI support
                @Suppress("UNCHECKED_CAST")
                createWithDependencies(kClass) as T
            } catch (e: Exception) {
                println("Failed to load '$className': ${e.message}")
                null
            }
        }

/**
 * Recursively create an instance of a KClass,
 * automatically creating constructor dependencies.
 */
fun createWithDependencies(kClass: KClass<*>): Any {
    // Get the primary constructor
    val constructor = kClass.primaryConstructor ?: kClass.constructors.firstOrNull()
    ?: throw IllegalArgumentException("No constructor found for ${kClass.simpleName}")

    // Recursively create parameters
    val params = constructor.parameters.map { param ->
        val paramClass = param.type.classifier as KClass<*>
        createWithDependencies(paramClass)
    }

    // Call constructor with parameters
    return constructor.call(*params.toTypedArray())
}