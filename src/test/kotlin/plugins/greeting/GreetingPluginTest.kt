package plugins.greeting

import app.loadFromConfig
import core.Greeter
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.io.File

@DisplayName("Greeting plugins loaded via ConfigLoader")
class GreetingPluginTest {
    private val configFile = "src/test/resources/extensions.config"
    private val packagePrefix = "plugins.greeting"

    @Test
    fun `all plugins load correctly`() {
        val greeters = loadFromConfig<Greeter>(configFile, packagePrefix)

        // Check that all greeters implement the contract
        greeters.forEach { greeter ->
            val greeting = greeter.greet()
            println("${greeter::class.simpleName}: $greeting")

            assertTrue(greeting.isNotBlank(), "${greeter::class.simpleName} returned blank")
        }

        // Check that all greetings are unique
        val greetings = greeters.map { it.greet() }
        assertEquals(greetings.size, greetings.toSet().size, "Expected unique greetings")
    }

    @Test
    fun `all expected plugins are loaded`() {
        val greeters = loadFromConfig<Greeter>(configFile, packagePrefix)
        val classNames = greeters.map { it::class.simpleName }

        val expected = listOf("HelloIt", "HelloNe", "HelloPt")
        assertTrue(expected.all { it in classNames }, "Not all expected plugins loaded")
    }
}