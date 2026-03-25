package core

interface Editor {
    val text: String
    val selection: IntRange
    fun get(range: IntRange): String
    fun insert(text: String, offset: Int)
    fun replace(text: String, selection: IntRange)
    fun setSelection(selection: IntRange)
    fun prompt(text: String): String?
    fun undo()
}