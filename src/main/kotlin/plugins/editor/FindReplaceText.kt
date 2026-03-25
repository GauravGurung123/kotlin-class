package plugins.editor

import core.Command
import core.Editor

class FindReplaceText : Command {
    override val name = "Find & Replace"

    // Snapshot of the full text before we changed anything — needed for undo
    private var previousText: String = ""

    override fun execute(editor: Editor) {
        val find    = editor.prompt("Find:")    ?: return   // user cancelled → do nothing
        val replace = editor.prompt("Replace:") ?: return

        previousText = editor.text              // save full snapshot BEFORE mutating

        val updated = editor.text.replace(find, replace)
        editor.replace(updated, 0..< editor.text.length)
    }

    override fun undo(editor: Editor) {
        // Restore the full snapshot — works regardless of how many replacements were made
        editor.replace(previousText, 0..< editor.text.length)
    }
}