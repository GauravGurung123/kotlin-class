package lab

import kotlin.collections.forEach

class DirectoryElement(
    name: String,
    parent: DirectoryElement? = null
) : Element(name, parent)
{
    val children: MutableList<Element> = mutableListOf<Element>()
}

fun DirectoryElement.allFiles(): List<FileElement>
{
    val allFiles = mutableListOf<FileElement>()

    children.forEach { item ->
        if (item is FileElement) {
            allFiles.add(item)
        }
        if(item is DirectoryElement){
            allFiles.addAll(item.allFiles())
        }
    }

    return allFiles;
}
fun DirectoryElement.allDirectories(): List<DirectoryElement>
{
    val allDirectories = mutableListOf<DirectoryElement>()

    children.forEach { item ->
        if(item is DirectoryElement){
            allDirectories.add(item)
            allDirectories.addAll(item.allDirectories())
        }
    }

    return allDirectories;
}