package kunit.runner

import kunit.annotations.ExpectedException
import kunit.utils.ReflectionUtils
import kotlin.reflect.KClass

class KUnit(private val testClass: KClass<*>) {

    fun testResults(): List<String> {

        val instance = ReflectionUtils.createInstance(testClass)

        return ReflectionUtils.getTestFunctions(testClass).map { function ->

            val expectedEx = ReflectionUtils.getAnnotation(
                function,
                ExpectedException::class
            )

            val error = ReflectionUtils.executeFunction(instance, function)

            when {
                error == null && expectedEx == null ->
                    "${function.name}: SUCCESS"

                error == null && expectedEx != null ->
                    "${function.name}: FAIL: Expected ${expectedEx.exceptionClass.simpleName}"

                error != null && expectedEx != null &&
                        error::class == expectedEx.exceptionClass ->
                    "${function.name}: SUCCESS"

                else ->
                    "${function.name}: FAIL: ${error?.message}"
            }
        }
    }
}