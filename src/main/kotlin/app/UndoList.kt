package app

class UndoList<T>(
    private val list: MutableList<T> = mutableListOf()
) : MutableList<T> by list {


    private var undoAction: (() -> (Unit)) = {}

    override fun toString(): String {
        return list.toString()
    }
    fun undoLast() {
        undoAction()
        undoAction = {}
    }

    override fun add(element: T): Boolean {
        list.add(element)
        undoAction = {
            list.removeLast()
        }
        return true
    }

    override fun set(index: Int, element: T): T {
        val prev = list.set(index, element)

        undoAction = {
            list.set(index, prev)
        }
        return prev
    }

    override fun removeAt(index: Int): T {
        val prev = list.removeAt(index)
        undoAction = {
            list.add(index, prev)
        }
        return prev
    }
}

fun main() {
    val list = UndoList<Int>()
    list.add(1)
    list.add(2)
    list.add(3)
    list.undoLast()
    println(list.toString())
    list.set(0, 10)
    println(list.toString())
    println(list.undoLast())
    list.remove(1)
    println(list.toString())
    println(list.undoLast())
}
