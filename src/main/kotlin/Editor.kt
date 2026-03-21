import java.awt.BorderLayout
import java.awt.Dimension
import java.io.File
import javax.swing.*
import kotlin.system.exitProcess
import kotlin.reflect.full.createInstance

interface Editor {
    val text: String
    val selection: IntRange
    fun get(range: IntRange): String
    fun insert(text: String, offset: Int)
    fun replace(text: String, selection: IntRange)
    fun setSelection(selection: IntRange)
    fun prompt(text: String): String?
    fun undo()
}

interface Command {
    val name: String
    fun execute(editor: Editor)
    fun undo(editor: Editor)
}

internal class CloseCommand : Command {
    override val name = "Close"

    override fun execute(editor: Editor) {
        exitProcess(0)
    }

    override fun undo(editor: Editor) {
        exitProcess(0)
    }
}
internal class MicroEditorFw : Editor {
    private val frame: JFrame
    private val buttons: JPanel
    private val textArea: JTextArea
    private val commandStack = mutableListOf<Command>()

    init {
        frame = JFrame("Micro Text Editor Framework")
        frame.size = Dimension(400, 200)
        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        textArea = JTextArea()
        buttons = JPanel()
        frame.add(buttons, BorderLayout.NORTH)
        frame.add(textArea)
        val commands = mutableListOf<Command>(CloseCommand())
        commands.addAll(loadCommands("textEditor.config"))
        commands.forEach {
            addButton(it)
        }
    }

    private fun loadCommands(configurationFile: String): List<Command> {
        val fl = File(configurationFile).readLines()
        val commandList = mutableListOf<Command>()


        fl.forEach { className ->
            val clazz = Class.forName("extensions.$className").kotlin
            val res = clazz.createInstance() as Command
            commandList.add(res)
        }
        return commandList
    }

    private fun addButton(command: Command) {
        buttons.add(JButton(command.name).apply {
            addActionListener {
                if (command.name != "Undo" && command.name != "Close") {
                    commandStack.add(command)
                }
                command.execute(this@MicroEditorFw)
            }
        }, BorderLayout.NORTH)
    }

    fun open() {
        frame.isVisible = true
    }

    override val text: String
        get() = textArea.text

    override val selection: IntRange
        get() = textArea.selectionStart..<textArea.selectionEnd

    override fun get(selection: IntRange): String = textArea.text.substring(selection)

    override fun insert(text: String, offset: Int) {
        textArea.insert(text, offset)
    }

    override fun replace(text: String, selection: IntRange) {
        textArea.replaceRange(text, selection.first, selection.last+1)
        textArea.selectionStart = selection.first
        textArea.selectionEnd = selection.first + text.length
    }

    override fun setSelection(selection: IntRange) {
        textArea.selectionStart = selection.first
        textArea.selectionEnd = selection.last
    }

    override fun prompt(text: String): String? {
        return JOptionPane.showInputDialog(frame, text)
    }

    override fun undo() {
        if (commandStack.isNotEmpty()) {
            val command = commandStack.removeAt(commandStack.size - 1)
            command.undo(this)
        }
    }
}

fun main() {
    val window = MicroEditorFw()
    window.open()
}