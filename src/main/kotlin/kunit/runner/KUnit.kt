package kunit.runner

import kunit.annotations.ExpectedException
import kunit.utils.ReflectionUtils
import kotlin.reflect.KClass
import kotlin.system.measureTimeMillis

class KUnit(private val testClass: KClass<*>) {

    // 🎨 ANSI COLORS
    private val GREEN = "\u001B[32m"
    private val RED = "\u001B[31m"
    private val YELLOW = "\u001B[33m"
    private val RESET = "\u001B[0m"

    data class TestResult(
        val name: String,
        val success: Boolean,
        val message: String,
        val timeMs: Long
    )

    fun runTests(): List<TestResult> {

        val instance = ReflectionUtils.createInstance(testClass)

        return ReflectionUtils.getTestFunctions(testClass).map { function ->

            val expectedEx = ReflectionUtils.getAnnotation(
                function,
                ExpectedException::class
            )

            var error: Throwable? = null

            val time = measureTimeMillis {
                error = ReflectionUtils.executeFunction(instance, function)
            }

            val success = when {
                error == null && expectedEx == null -> true

                error == null && expectedEx != null -> false

                error != null && expectedEx != null &&
                        error::class == expectedEx.exceptionClass -> true

                else -> false
            }

            val message = when {
                success -> "SUCCESS"

                error == null && expectedEx != null ->
                    "Expected ${expectedEx.exceptionClass.simpleName}"

                else ->
                    error?.message ?: "Unknown error"
            }

            TestResult(
                name = function.name,
                success = success,
                message = message,
                timeMs = time
            )
        }
    }

    fun runAndPrintSummary() {
        val results = runTests()

        println("----- KUNIT RESULTS -----")

        results.forEach {
            val color = if (it.success) GREEN else RED
            val status = if (it.success) "✔" else "✘"

            println(
                "$color$status ${it.name}: ${it.message} " +
                        "(${it.timeMs} ms)$RESET"
            )
        }

        println("-------------------------")

        val total = results.size
        val passed = results.count { it.success }
        val failed = total - passed

        println("$YELLOW✔ $total tests run$RESET")
        println("$GREEN✔ $passed passed$RESET")
        println("$RED✘ $failed failed$RESET")
    }
}