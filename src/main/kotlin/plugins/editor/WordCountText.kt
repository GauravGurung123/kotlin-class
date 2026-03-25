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
            .filter { it.isNotEmpty() }
            .size
        editor.message("Word count: $count")
    }

    override fun undo(editor: Editor) {
        // read-only command — nothing to undo
    }
}