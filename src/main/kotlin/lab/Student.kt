package lab

import kotlin.reflect.KAnnotatedElement
import kotlin.reflect.KClass
import kotlin.reflect.KProperty
import kotlin.reflect.KType
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.findAnnotation

@Target( AnnotationTarget.PROPERTY, AnnotationTarget.CLASS)
annotation class Dbname(
    val name: String = ""
)

@Dbname("student")
data class Student(
    @Dbname("number")
    val number: Int,
    val name: String,
    val worker: Boolean? = null
)

fun generateCreateTable(clazz: KClass<*>): String {
    return  (
            "create table ${mapAnnotation(clazz,clazz.simpleName!!)} ("
                    + clazz.declaredMemberProperties
                        .joinToString { mapAnnotation(it, it.name) + " "+ mapType(it.returnType) } + ");"
            )
}
fun mapType(type: KType): String
{
    return when(type.classifier){
        Int::class -> "INTEGER"
        String::class -> "VARCHAR"
        Boolean::class -> "BOOLEAN"
        else -> "unknown"
    }
}

fun mapAnnotation(clazz: KAnnotatedElement, default: String) :String {
    return clazz.findAnnotation<Dbname>()?.name ?: default
}
fun insertInto(obj: Any) : String {
    require(obj::class.isData)
    var insertQuery = "insert into " + mapAnnotation(obj::class, obj::class.simpleName!!) +
     "(" + obj::class.declaredMemberProperties
        .joinToString { mapAnnotation(it, it.name)  } + ")"

    var values = "values (" + obj::class.declaredMemberProperties.joinToString {
        addQuotes(mapType(it.returnType),it.call(obj).toString())
    }+ ")"

    return insertQuery + " " + values
}

fun addQuotes(type: String, value: String): String {
    if (type=="VARCHAR"){
        return "'" + value + "'"
    }
    return value
}
fun main(){
//    val result = generateCreateTable(Student::class)
//    println(result)
    val s = Student(26503, "André")
    println(insertInto(s))

}