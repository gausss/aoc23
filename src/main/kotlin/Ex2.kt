enum class CubeColor { RED, GREEN, BLUE }
data class Cube(val color: CubeColor, val amount: Int)
data class Game(val id: Int, val cubes: Set<Cube>)

fun main() {

    fun parseCubeDraws(draws: String): Set<Cube> {
        return draws.split(',')
            .map { it.trim().split(' ') }
            .mapNotNull {
                when (it[1]) {
                    "red" -> Cube(CubeColor.RED, it[0].toInt())
                    "green" -> Cube(CubeColor.GREEN, it[0].toInt())
                    "blue" -> Cube(CubeColor.BLUE, it[0].toInt())
                    else -> null
                }
            }.toSet()
    }

    fun parseGame(line: String): Game {
        val (gameDefinition, draws) = line.split(':')
        val gameId = gameDefinition.split(' ')[1].toInt()
        val cubes = draws.split(';').flatMap { parseCubeDraws(it) }.toSet()
        return Game(gameId, cubes)
    }

    fun isValid(game: Game): Boolean {
        val anyViolation = game.cubes.any {
            when (it.color) {
                CubeColor.RED -> it.amount > 12
                CubeColor.GREEN -> it.amount > 13
                CubeColor.BLUE -> it.amount > 14
            }
        }

        return !anyViolation
    }

    fun part1(input: List<String>): Int {
        return input.map { line -> parseGame(line) }.filter { game -> isValid(game) }.sumOf { it.id }
    }


    val testInput = readInput("Ex2_test")
    check(part1(testInput) == 8)

    val input = readInput("Ex2")
    println(part1(input))
}