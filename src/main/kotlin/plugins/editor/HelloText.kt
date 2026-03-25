package plugins.editor

import core.Command
import core.Editor

class HelloText : Command {
    override val name = "Hello Text"
    private val insertedText = "Hello World"

    override fun execute(editor: Editor) {
        editor.insert(insertedText, 0)
    }
    override fun undo(editor: Editor) {
        editor.replace("", 0 ..< insertedText.length)
    }
}