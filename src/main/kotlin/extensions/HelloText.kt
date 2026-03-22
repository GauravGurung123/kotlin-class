package extensions

import Command
import Editor

class HelloText: Command {
    override val name = "Hello Text"
    val insertedText = "Hello World"
    override fun execute(editor: Editor) {
        editor.insert(insertedText, 0)
    }
    override fun undo(editor: Editor) {
        editor.replace("", 0..<  insertedText.length)
    }
}