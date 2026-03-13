package lab2

sealed class Element(
    val name: String,
    val parent: DirectoryElement?
) {
    init {
        println("form element class name " + this.name)
        println("form element class parent " + this.parent)

        if (parent != null) {
            parent.children.add(this)
        }
        println("Depth $depth")
        println("path $path")
    }

    val depth: Int
        get() = if (parent == null) {
            0
        } else {
            //checkParent(parent)
            1 + parent.depth
        }
    val path: String
        get() = if (parent == null) {
            "/$name"
        } else {
            parent.path + "/" + name
        }

    fun checkParent(p: DirectoryElement?): Int {
        return if (p?.parent != null) {
            1 + checkParent(p.parent)
        } else {
            1
        }
    }


}

//class FileElement(
//    name: String,
//    parent: DirectoryElement? = null
//) : Element(name, parent)
//{
//
//}

//class DirectoryElement(
//    name: String,
//    parent: DirectoryElement? = null
//) : Element(name, parent)
//{
//    val children: MutableList<Element> = mutableListOf<Element>()
//
//}