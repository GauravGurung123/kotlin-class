package lab
import java.io.File
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
//    val fl = File("extensions.config").readLines()
//    println(fl)
//
//    fl.forEach { className ->
//        val clazz = Class.forName("extensions.$className").kotlin
//        val hello = clazz.createInstance() as Hello
//        println(hello.sayHello() + "!")
//    }

}