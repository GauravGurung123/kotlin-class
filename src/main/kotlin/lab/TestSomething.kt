package lab;

class TestSomething {
    var x: Int = 0;
    var y: Boolean = false;

    override fun toString(): String {
        return "We are in TestSomething";
    }

    @TestCase("from inc method")
    fun inc() {
        checkEqual(2,6)
    }
    @TestCase("from set method")
    fun set() {
        checkEqual(true, true)
    }
}
