package extensions

import Command
import Editor

class UndoText: Command {
    override val name = "Undo"
    val insertedText = "Hello World"

    override fun execute(editor: Editor) {
        editor.undo()
    }
    override fun undo(editor: Editor) {
        editor.replace("", 0..< insertedText.length)
    }
}