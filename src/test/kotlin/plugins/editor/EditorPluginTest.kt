package plugins.editor

import app.loadFromConfig
import core.Command
import core.Editor
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Editor plugins loaded via ConfigLoader")
class EditorPluginTest {

    private val configFile = "src/test/resources/textEditor.config"
    private val packagePrefix = "plugins.editor"

    @Test
    fun `all editor plugins load correctly`() {
        val commands = loadFromConfig<Command>(configFile, packagePrefix)

        // Check that all commands implement the contract
        commands.forEach { command ->
            assertTrue(command.name.isNotBlank(), "${command::class.simpleName} has blank name")
            assertDoesNotThrow { command.execute(MockEditor()) }
        }

        // Check that all commands have unique names
        val names = commands.map { it.name }
        assertEquals(names.size, names.toSet().size, "Expected unique command names")
    }

    @Test
    fun `all expected editor plugins are loaded`() {
        val commands = loadFromConfig<Command>(configFile, packagePrefix)
        val classNames = commands.map { it::class.simpleName }

        val expected = listOf("FindReplaceText", "HelloText", "UndoText", "WordCountText")
        assertTrue(expected.all { it in classNames }, "Not all expected plugins loaded")
    }
}

// Simple in-memory mock of Editor for testing purposes
class MockEditor : Editor {
    private val _text = StringBuilder()
    private var _selection: IntRange = 0..0
    override val text: String get() = _text.toString()
    override val selection: IntRange get() = _selection
    override fun get(range: IntRange) = _text.substring(range)
    override fun insert(text: String, offset: Int) {
        _text.insert(offset, text)
    }
    override fun replace(text: String, selection: IntRange) {
        _text.replace(selection.first, selection.last + 1, text)
        _selection = selection.first..(selection.first + text.length - 1)
    }
    override fun setSelection(selection: IntRange) { _selection = selection }
    override fun prompt(text: String) = null
    override fun message(text: String) {}
    override fun undo() {}
}