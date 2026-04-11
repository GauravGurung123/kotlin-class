package lab

interface DummyList<T> {
    val empty: Boolean
    fun add(e: T)
    fun first(): T
    fun getLast(): T
    fun getElement(index: Int): T? // we have to throw an error
}

class DummyListImpl<T> : DummyList<T> {
    private var elements: Array<Any?>
    var next: Int

    init {
        elements = Array(10) { null }
        next = 0
    }

    override val empty: Boolean get() = next == 0

    val isNotEmpty: Boolean get() = next != 0

    override fun add(e : T) {
        if(next == elements.size)
            grow()

        elements[next++] = e
    }

    fun grow() {
        elements = elements.copyOf(elements.size * 2)
    }

    fun pushAll(list: ArrayList<T?>) { // We can use list to add elements
        list.forEach {
            if(it != null)
                add(it)
        }
    }

    override fun first(): T = elements[0] as T

    override fun getLast(): T = elements[next-1] as T

    override fun getElement(index: Int): T? =
        if(index < next) elements[index] as T
        else null

    fun filter(predicate: (T) -> Boolean): DummyListImpl<T>? {
        val filter = DummyListImpl<T>()
        for(i in 0 ..< next)
            if(elements[i] != null && predicate(elements[i] as T))
                filter.add(elements[i] as T)
        return if(filter.empty)
            null
        else
            filter
    }
}

fun main() {
    val list = DummyListImpl<Int>()
    (1..5).forEach {
        list.add(it)
    }
    list.pushAll(ArrayList(listOf(6,7,8,9))) // we can use list
    for(i in 0..< list.next)
        println(list.getElement(i))
}