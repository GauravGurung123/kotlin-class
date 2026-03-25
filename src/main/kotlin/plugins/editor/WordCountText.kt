package plugins.editor

import core.Command
import core.Editor

class WordCountText : Command {
    override val name = "Word Count"
    override val mutatesEditor = false

    override fun execute(editor: Editor) {
        val count = editor.text
            .trim()
            .split(Regex("\\s+"))
            .count { it.isNotEmpty() }

        editor.message("Word count: $count")
    }

    override fun undo(editor: Editor) {
        // Read-only command → nothing to undo
    }
}