enum class CubeColor { RED, GREEN, BLUE }
data class Cube(val color: CubeColor, val amount: Int)
data class Game(val id: Int, val cubes: Set<Cube>) {
    fun isValid(): Boolean {
        val anyViolation = this.cubes.any {
            when (it.color) {
                CubeColor.RED -> it.amount > 12
                CubeColor.GREEN -> it.amount > 13
                CubeColor.BLUE -> it.amount > 14
            }
        }

        return !anyViolation
    }
}

fun main() {

    fun parseCubeDraws(draws: String): Set<Cube> {
        return draws.split(',')
            .map { cubeString -> cubeString.trim().split(' ', limit = 2) }
            .mapNotNull { cubeSplit ->
                val (amount, color) = cubeSplit
                when (color) {
                    "red" -> Cube(CubeColor.RED, amount.toInt())
                    "green" -> Cube(CubeColor.GREEN, amount.toInt())
                    "blue" -> Cube(CubeColor.BLUE, amount.toInt())
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


    fun part1(input: List<String>): Int {
        return input.map { line -> parseGame(line) }.filter { game -> game.isValid() }.sumOf { it.id }
    }


    val testInput = readInput("Ex2_test")
    check(part1(testInput) == 8)

    val input = readInput("Ex2")
    println(part1(input))
}