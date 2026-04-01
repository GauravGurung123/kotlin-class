package lab

import java.io.File
import kotlin.test.Test
import kotlin.test.assertEquals

class CollectionTests {

    val fileList = listOf(
        File("README"),
        File("Test.kt"),
        File("Example.kt"),
        File("Script.kts")
    )

    /**
     * 3. Define an extension function that counts how many files with a given extension (String)
     *  exist in a List<File>. For the above fileList,
     *  the function should return 2 for the extension "kt".
     **/
    @Test
    fun testRepeatedExtensionCount()
    {
        assertEquals(2, fileList.countExtensions("kt"))
    }

    /**
     * 4.  Define an extension function that returns a subset of a List<File>
     *     with the files that have certain extensions. Using the list above,
     *     the function should return ["Test.kt", "Example.kt","Script.kts"]
     *     for the extensions "kt" and "kts".
     */
    @Test
    fun testExtensionsExist(){
        val reqFiles = listOf<String>("kt", "kts")
        val expectedResult = listOf<String>("Test.kt", "Example.kt", "Script.kts")
//        fileList.getFiles(reqFiles)
        assertEquals( expectedResult, fileList.getFiles(reqFiles))
    }

    @Test
    fun testFileByDistinctExtension(){
        val expectedExtensions = setOf<String>("kt", "kts")
        assertEquals(expectedExtensions, fileList.distinctExtensions())
    }

    @Test
    fun testFileExtension() {
//        assertEquals("", File("random").extension)
        assertEquals("kt", File("Test.kt").extension)
        assertEquals("kt", File("build.gradle.kts").extension)
    }

   @Test
   fun `list concatenation`() {
       val listA = listOf("A", "B", "C")
       val listMA = mutableListOf("A", "B", "C")
       val listB = listOf("D", "E")
       val listAB =  listA + listB // listB.plus(listA)
       assertEquals(listOf("A", "B", "C"), listA) // listA does not change
       assertEquals(listOf("D", "E"), listB) // listB does not change
       assertEquals(listOf("A","B","C","D","E"), listAB)

       assertEquals(listOf("A", "C"), listA.filter { it != "B" })
       assertEquals(listOf("DA", "EA"), listB.map {s -> s + 'A' })
       assertEquals(true, listMA.removeIf { it != "B" })
   }


}
