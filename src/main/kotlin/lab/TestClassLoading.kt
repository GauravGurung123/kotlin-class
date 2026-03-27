package lab

import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance
import kotlin.reflect.full.declaredFunctions
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.hasAnnotation
import kotlin.reflect.full.valueParameters

@Target(AnnotationTarget.FUNCTION)
annotation class TestCase(
    val desc: String = ""
)

fun main(){
    val testClass: KClass<*> = Class.forName("lab.TestSomething").kotlin
    val testObject = testClass.createInstance()

//    testClass.declaredFunctions.forEach {
//        println(it.name + " : " + it.call(testObject))
//    }

    val kUnit = KUnit(testClass)
    val results = kUnit.runTests()
    println("=============================================")
    println(results.forEach {
        println("${it.name} : ${if (it.passed) "Passed" else "Failed"}")
    })


}
fun checkEqual(expected: Any?, actual: Any?)
{
    if (expected != actual) {
        throw AssertionError("Expected $expected, but was $actual")
    }

}

class KUnit (val classWithTests: KClass<*>)
{
    fun runTests(): List<TestResult> {
        val testObject = classWithTests.createInstance()
        val testResults = mutableListOf<TestResult>()

        classWithTests.declaredFunctions.forEach {
            if(
                it.hasAnnotation<TestCase>()
                && it.valueParameters.isEmpty()
                )
            {
                val annotation = it.findAnnotation<TestCase>()!!
                println("Running ${annotation.desc}")

                try {
                    it.call(testObject)
                    testResults.add(TestResult(it.name, true))

                } catch (e: Exception){
                    testResults.add(TestResult(it.name, false))
                }
            }
        }
        return testResults
    }
}

data class TestResult(
    val name: String,
    val passed: Boolean,
//    val time: String?
)