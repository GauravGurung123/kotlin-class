package app

import core.Greeter

interface Printer {
    fun print(text: String)
}
class ConsolePrinter : Printer {
    override fun print(text: String) {
        println(text)
    }
}

class BracketDecorator(val printer: Printer) : Printer {
    override fun print(text: String) {
        printer.print("[$text]")
    }
}
class BlankTextSkipDecorator(val printer: Printer) : Printer {
    override fun print(text: String) {
        if (!text.isBlank()){
            printer.print(text)
        }
    }
}
fun main() {
//    val configFile = "extensions.config"
//    val packagePrefix = "plugins.greeting"
//
//    loadFromConfig<Greeter>(configFile, packagePrefix)
//        .forEach { println(it.greet() + "!") }
//
    val console = ConsolePrinter()
    console.print("hello")
    val bracket = BracketDecorator(console)
    bracket.print("hello!")
    val blank = BlankTextSkipDecorator(bracket)
    blank.print("")
    blank.print("with not blank")
}