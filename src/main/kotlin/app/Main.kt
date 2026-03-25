package app

import core.Greeter
import java.io.File
import kotlin.reflect.full.createInstance

fun main() {
    File("extensions.config").readLines().forEach { className ->
        val greeter = Class.forName("plugins.greeting.$className").kotlin
            .createInstance() as Greeter
        println(greeter.greet() + "!")
    }
}