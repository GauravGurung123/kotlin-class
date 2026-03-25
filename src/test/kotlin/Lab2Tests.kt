import lab.DirectoryElement
import lab.Element
import lab.FileElement
import lab.allDirectories
import lab.allFiles
import kotlin.test.Test
import kotlin.test.assertEquals

class Lab2Tests {
    val artists = DirectoryElement("artists")
    val beatles = DirectoryElement("beatles", artists)
    val help = DirectoryElement("help", beatles)
    val iNeedYou = FileElement("i need you", help)
    val letItBe = DirectoryElement("let it be", beatles)
    val getDown = FileElement("get down", letItBe)
    val twoOfUs = FileElement("two of us", letItBe)

    @Test
    fun testRelationship(){
        assertEquals(beatles,help.parent);
        assertEquals(listOf<Element>(getDown, twoOfUs), letItBe.children);
    }

    @Test
    fun testDepth(){

        assertEquals(0, artists.depth)
        assertEquals(1, beatles.depth)
        assertEquals(2, letItBe.depth)
        assertEquals(3, getDown.depth)
    }

    @Test
    fun testPath(){
        assertEquals("/artists/beatles/let it be", letItBe.path)
    }

    @Test
    fun testAllFiles(){
        assertEquals(listOf<FileElement>(iNeedYou,getDown,twoOfUs), beatles.allFiles());
    }

    @Test
    fun testAllDirectories(){
        assertEquals(listOf<DirectoryElement>(help,letItBe), beatles.allDirectories());
    }
}
