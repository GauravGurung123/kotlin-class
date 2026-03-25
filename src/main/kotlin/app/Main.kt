package app

import core.Greeter

fun main() {
    val configFile = "extensions.config"
    val packagePrefix = "plugins.greeting"

    loadFromConfig<Greeter>(configFile, packagePrefix)
        .forEach { println(it.greet() + "!") }
}