package core

interface Command {
    val name: String
    val mutatesEditor: Boolean get() = true   // false for read-only commands
    fun execute(editor: Editor)
    fun undo(editor: Editor)
}