package lab

fun main(){
    val s = "Hello world"
    greeting(s)
}

fun greeting(s: String = "Hello"){
    if (s.isEmpty())
        println("empty")
    print(s)
}
