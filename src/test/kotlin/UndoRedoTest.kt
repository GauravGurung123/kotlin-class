import extensions.HelloText
import extensions.UndoText
import kotlin.test.Test
import kotlin.test.assertEquals

class UndoRedoTest {

    class MockEditor : Editor {
        private var _text = ""
        override val text: String get() = _text
        override val selection: IntRange get() = 0..0
        override fun get(range: IntRange): String = _text.substring(range)
        override fun insert(text: String, offset: Int) {
            _text = _text.substring(0, offset) + text + _text.substring(offset)
        }
        override fun replace(text: String, selection: IntRange) {
            _text = _text.substring(0, selection.first) + text + _text.substring(selection.last + 1)
        }
        override fun setSelection(selection: IntRange) {}
        override fun prompt(text: String): String? = null
        
        val commandStack = mutableListOf<Command>()
        
        override fun undo() {
            if (commandStack.isNotEmpty()) {
                val command = commandStack.removeAt(commandStack.size - 1)
                command.undo(this)
            }
        }
        
        fun executeCommand(command: Command) {
            if (command.name != "Undo" && command.name != "Close") {
                commandStack.add(command)
            }
            command.execute(this)
        }
    }

    @Test
    fun testHelloTextUndo() {
        val editor = MockEditor()
        val helloCommand = HelloText()
        val undoCommand = UndoText()

        editor.executeCommand(helloCommand)
        assertEquals("Hello World", editor.text, "Text should be 'Hello World' after execute")

        editor.executeCommand(undoCommand)
        assertEquals("", editor.text, "Text should be empty after undo")
    }
}
