package app

import java.io.File
import kotlin.reflect.full.createInstance

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
                @Suppress("UNCHECKED_CAST")
                Class.forName("$packagePrefix.$className").kotlin.createInstance() as T
            } catch (e: Exception) {
                println("Failed to load '$className': ${e.message}")
                null
            }
        }