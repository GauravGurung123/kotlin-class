package extensions

import Command
import Editor

class HelloText: Command {
    override val name = "Hello Text"

    override fun execute(editor: Editor) {
        editor.insert("Hello World", 0)
    }
    override fun undo(editor: Editor) {
        editor.insert(name, 0)
    }
}