package kunit.annotations

import kotlin.reflect.KClass

@Target(AnnotationTarget.FUNCTION)
annotation class ExpectedException(
    val exceptionClass: KClass<out Exception>
)