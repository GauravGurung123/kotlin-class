package lab

import kotlin.reflect.KAnnotatedElement
import kotlin.reflect.KClass
import kotlin.reflect.KType
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.hasAnnotation

@Target( AnnotationTarget.PROPERTY, AnnotationTarget.CLASS)
annotation class Dbname(
    val name: String = ""
)
@Target(AnnotationTarget.PROPERTY)
annotation class PrimaryKey

@Target(AnnotationTarget.PROPERTY)
annotation class DbIgnore
@Dbname("student")
data class Student(
    @PrimaryKey
    @Dbname("number")
    val number: Int,

    val name: String,

    val worker: Boolean? = null,

    @DbIgnore
    val temp: String = "ignore me"
)
fun generateCreateTable(clazz: KClass<*>): String {
    val columns = clazz.declaredMemberProperties
        .filter { !it.hasAnnotation<DbIgnore>() }
        .joinToString(", ") { prop ->

            val name = mapAnnotation(prop, prop.name)
            val type = mapType(prop.returnType)

            val nullable = if (prop.returnType.isMarkedNullable) "" else " NOT NULL"
            val pk = if (prop.hasAnnotation<PrimaryKey>()) " PRIMARY KEY" else ""

            "$name $type$nullable$pk"
        }

    return "CREATE TABLE ${mapAnnotation(clazz, clazz.simpleName!!)} ($columns);"
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
fun insertInto(obj: Any): String {
    require(obj::class.isData)

    val props = obj::class.declaredMemberProperties
        .filter { !it.hasAnnotation<DbIgnore>() }

    val insertQuery = "INSERT INTO ${mapAnnotation(obj::class, obj::class.simpleName!!)} (" +
            props.joinToString { mapAnnotation(it, it.name) } + ")"

    val values = "VALUES (" + props.joinToString {
        addQuotes(mapType(it.returnType), it.call(obj).toString())
    } + ")"

    return "$insertQuery $values"
}
fun addQuotes(type: String, value: String): String {
    if (type=="VARCHAR"){
        return "'$value'"
    }
    return value
}
fun main(){
    val result = generateCreateTable(Student::class)
    println(result)
    val s = Student(26503, "André")
    println(insertInto(s))

}