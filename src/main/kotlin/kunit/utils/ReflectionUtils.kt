package kunit.utils

import java.lang.reflect.InvocationTargetException
import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.full.createInstance
import kotlin.reflect.full.declaredMemberFunctions
import kotlin.reflect.full.hasAnnotation

object ReflectionUtils {

    /**
     * Create instance of a class (assumes no-arg constructor)
     */
    fun <T : Any> createInstance(clazz: KClass<T>): T {
        return clazz.createInstance()
    }

    /**
     * Get all functions annotated with @TestCase
     */
    fun getTestFunctions(clazz: KClass<*>): List<KFunction<*>> {
        return clazz.declaredMemberFunctions
            .filter { it.hasAnnotation<kunit.annotations.TestCase>() }
    }

    /**
     * Get annotation safely
     */
    fun <T : Annotation> getAnnotation(
        function: KFunction<*>,
        annotationClass: KClass<T>
    ): T? {
        return function.annotations
            .firstOrNull { it.annotationClass == annotationClass } as? T
    }

    /**
     * Execute function and unwrap InvocationTargetException
     */
    fun executeFunction(instance: Any, function: KFunction<*>): Throwable? {
        return try {
            function.call(instance)
            null
        } catch (e: InvocationTargetException) {
            e.cause
        }
    }
}