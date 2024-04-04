import kotlin.io.path.Path
import kotlin.io.path.readLines
import kotlin.io.path.readText

val WHITESPACE_REGEX = "\\s+".toRegex()
fun <T> List<T>.tail() = subList(1, size)
fun readInputLines(name: String) = Path({}.javaClass.getResource("$name.txt").path).readLines()
fun readInput(name: String) = Path({}.javaClass.getResource("$name.txt").path).readText()

data class Position(val x: Int, val y: Int) {
    fun getAdjacent(): Set<Position> {
        return setOf(
            Position(x - 1, y),
            Position(x + 1, y),
            Position(x, y - 1),
            Position(x, y + 1),
            Position(x - 1, y + 1),
            Position(x - 1, y - 1),
            Position(x + 1, y + 1),
            Position(x + 1, y - 1)
        )
    }
}