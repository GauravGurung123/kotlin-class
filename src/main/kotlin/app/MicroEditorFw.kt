package app

import core.Command
import core.Editor
import java.awt.BorderLayout
import java.awt.Dimension
import java.awt.FlowLayout
import java.io.File
import javax.swing.*
import kotlin.reflect.full.createInstance
import kotlin.system.exitProcess

private class CloseCommand : Command {
    override val name = "Close"
    override fun execute(editor: Editor) = exitProcess(0)
    override fun undo(editor: Editor) = exitProcess(0)
}

class MicroEditorFw : Editor {
    private val frame     = JFrame("Micro Text Editor Framework")
    private val configFile = "textEditor.config"
    private val packagePrefix = "plugins.editor"
    private val buttons = JPanel(FlowLayout(FlowLayout.CENTER))
    private val textArea  = JTextArea()
    private val commandStack = mutableListOf<Command>()
    init {
        frame.size = Dimension(600, 400)
        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        frame.add(buttons, BorderLayout.NORTH)
        frame.add(textArea)

        val commands = mutableListOf<Command>(CloseCommand())
        commands.addAll(loadCommands())
        commands.forEach { addButton(it) }
    }

    private fun loadCommands(): List<Command> = loadFromConfig(configFile, packagePrefix)
    private fun addButton(command: Command) {
        buttons.add(JButton(command.name).apply {
            addActionListener {
                if (command.mutatesEditor) commandStack.add(command)  // ← no more name hack
                command.execute(this@MicroEditorFw)
            }
        })
    }

    fun open() { frame.isVisible = true }

    override val text: String get() = textArea.text
    override val selection: IntRange get() = textArea.selectionStart ..< textArea.selectionEnd
    override fun get(range: IntRange) = textArea.text.substring(range)
    override fun insert(text: String, offset: Int) = textArea.insert(text, offset)
    override fun replace(text: String, selection: IntRange) {
        textArea.replaceRange(text, selection.first, selection.last + 1)
        textArea.selectionStart = selection.first
        textArea.selectionEnd   = selection.first + text.length
    }
    override fun setSelection(selection: IntRange) {
        textArea.selectionStart = selection.first
        textArea.selectionEnd   = selection.last
    }
    override fun prompt(text: String): String? = JOptionPane.showInputDialog(frame, text)
    override fun undo() {
        if (commandStack.isNotEmpty())
            commandStack.removeAt(commandStack.size - 1).undo(this)
    }
    override fun message(text: String) {
        JOptionPane.showMessageDialog(frame, text)
    }
}

fun main() {
    MicroEditorFw().open()
}