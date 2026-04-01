package kunit.asserts

fun assertEquals(expected: Any?, actual: Any?) {
    if (expected != actual) {
        throw AssertionError("Expected $expected but got $actual")
    }
}

fun assertTrue(condition: Boolean) {
    if (!condition) {
        throw AssertionError("Condition is false")
    }
}