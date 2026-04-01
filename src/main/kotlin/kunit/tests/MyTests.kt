package kunit.tests

import kunit.annotations.ExpectedException
import kunit.annotations.TestCase
import kunit.asserts.assertEquals

class MyTests {

    @TestCase
    fun testSize() {
        val list = listOf(1, 2, 3)
        assertEquals(33, list.size)
    }

    @TestCase
    @ExpectedException(NoSuchElementException::class)
    fun testFirst() {
        val list = listOf<Int>()
        list.first()
    }
}