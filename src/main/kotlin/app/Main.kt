package app

import core.Greeter

fun main() {
    loadFromConfig<Greeter>("extensions.config", "plugins.greeting")
        .forEach { println(it.greet() + "!") }
}