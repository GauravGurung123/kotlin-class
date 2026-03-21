package extensions

import Command
import Editor

class HelloText: Command {
    override val name = "Hello Text"

    override fun execute(editor: Editor) {
        editor.insert("Hello World", 0)
    }
    override fun undo(editor: Editor) {
        editor.replace("", 0..<"Hello World".length)
    }
}