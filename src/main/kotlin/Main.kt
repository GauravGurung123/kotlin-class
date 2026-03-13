
import java.io.File
import java.security.cert.Extension
import kotlin.collections.forEach

fun List<File>.distinctExtensions(): Set<String>
{
    val result = mutableSetOf<String>()
    this.forEach { item ->
        if(!item.extension.isEmpty()){
            result.add(item.extension)
        }
    }
    return result
}
fun List<File>.countExtensions(extension: String): Int
{
    var count: Int = 0;
    this.forEach { item ->
        if(item.extension == extension){
            count++
        }
    }
    return count
}

fun List<File>.getFiles(ls: List<String> ): List<String>
{
    var result = mutableListOf<String>();

    this.forEach { item ->
        if(item.extension.isNotEmpty()){
            ls.map { if(item.extension == it) result.add(item.name)}
            println("result is " + result)
        }
    }
    return result
}

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {

    val name = "Kotlin"
    //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
    // to see how IntelliJ IDEA suggests fixing it.
    println("Hello, " + name + "!")

    for (i in 1..5) {
        //TIP Press <shortcut actionId="Debug"/> to start debugging your code. We have set one <icon src="AllIcons.Debugger.Db_set_breakpoint"/> breakpoint
        // for you, but you can always add more by pressing <shortcut actionId="ToggleLineBreakpoint"/>.
        println("i = $i")
    }
}