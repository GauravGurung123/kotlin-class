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

    // Map plugin class names to sample initial editor text and expected results
    private val testData = mapOf(
        "FindReplaceText" to PluginTestCase(
            initialText = "hello world",
            promptResponses = listOf("hello", "replaced"),
            expectedText = "replaced world"
        ),
        "HelloText" to PluginTestCase(
            initialText = "",
            promptResponses = listOf("Hello World"),
            expectedText = "Hello World" // depends on your plugin behavior
        ),
        "UndoText" to PluginTestCase(
            initialText = "abc",
            promptResponses = emptyList(),
            expectedText = "abc" // just ensure no crash
        ),
        "WordCountText" to PluginTestCase(
            initialText = "one two three",
            promptResponses = emptyList(),
            expectedMessages = listOf("Word count: 3")
        )
    )

    @Test
    fun `all editor plugins load and execute correctly`() {
        val commands = loadFromConfig<Command>(configFile, packagePrefix)
        val loadedNames = commands.map { it::class.simpleName }

        // Check all expected plugins are loaded
        val expectedNames = testData.keys.toList()
        assertTrue(expectedNames.all { it in loadedNames }, "Not all expected plugins loaded")

        // Execute each plugin using MockEditor
        commands.forEach { command ->
            val name = command::class.simpleName ?: return@forEach
            val case = testData[name] ?: return@forEach

            val editor = MockEditor(case.initialText)
            editor.promptResponses = case.promptResponses.toMutableList()

            command.execute(editor)

            // Check mutating plugins update text
            case.expectedText?.let { expected ->
                assertEquals(expected, editor.text, "$name produced unexpected text")
            }

            // Check read-only plugins produce messages
            case.expectedMessages?.let { expectedMessages ->
                assertEquals(expectedMessages, editor.messages, "$name produced unexpected messages")
            }
        }
    }
}

// Encapsulates a test case for a plugin
data class PluginTestCase(
    val initialText: String = "",
    val promptResponses: List<String> = emptyList(),
    val expectedText: String? = null,
    val expectedMessages: List<String>? = null
)

// MockEditor captures text, messages, and simulates prompts
class MockEditor(initialText: String = "") : Editor {
    private val _text = StringBuilder(initialText)
    private var _selection: IntRange = 0..0
    val messages = mutableListOf<String>()
    var promptResponses = mutableListOf<String>()
    private var promptIndex = 0

    override val text: String get() = _text.toString()
    override val selection: IntRange get() = _selection
    override fun get(range: IntRange) = _text.substring(range)
    override fun insert(text: String, offset: Int) { _text.insert(offset, text) }
    override fun replace(text: String, selection: IntRange) {
        _text.replace(selection.first, selection.last + 1, text)
        _selection = selection.first..(selection.first + text.length - 1)
    }
    override fun setSelection(selection: IntRange) { _selection = selection }
    override fun prompt(text: String): String? =
        if (promptIndex < promptResponses.size) promptResponses[promptIndex++]
        else null
    override fun message(text: String) { messages.add(text) }
    override fun undo() {}
}