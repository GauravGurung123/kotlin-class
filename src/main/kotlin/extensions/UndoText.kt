package extensions

import Command
import Editor

class UndoText: Command {
    override val name = "Undo"

    override fun execute(editor: Editor) {
        editor.undo()
    }
    override fun undo(editor: Editor) {
    }
}