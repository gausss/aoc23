import kotlin.io.path.Path
import kotlin.io.path.readLines

fun readInput(name: String) = Path({}.javaClass.getResource("$name.txt").path).readLines();
