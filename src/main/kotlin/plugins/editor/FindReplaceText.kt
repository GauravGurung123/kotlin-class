package plugins.editor

import core.Command
import core.Editor

class FindReplaceText : Command {
    override val name = "Find & Replace"

    private var previousText: String = ""

    override fun execute(editor: Editor) {
        val find = editor.prompt("Find:") ?: return
        val replace = editor.prompt("Replace:") ?: return

        previousText = editor.text

        // Replace all occurrences
        val updated = editor.text.replace(find, replace)
        editor.replace(updated, 0 until editor.text.length)
    }

    override fun undo(editor: Editor) {
        editor.replace(previousText, 0 until editor.text.length)
    }
}