private enum class CubeColor { RED, GREEN, BLUE }

private data class Cube(val color: CubeColor, val amount: Int)

private data class Game(val id: Int, val cubes: List<Cube>) {
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

private fun parseCubeDraws(draws: String): List<Cube> {
    return draws.split(',')
        .map { cubeString -> cubeString.trim().split(' ') }
        .mapNotNull { cubeSplit ->
            val (amount, color) = cubeSplit
            when (color) {
                "red" -> Cube(CubeColor.RED, amount.toInt())
                "green" -> Cube(CubeColor.GREEN, amount.toInt())
                "blue" -> Cube(CubeColor.BLUE, amount.toInt())
                else -> null
            }
        }
}

private fun parseGame(line: String): Game {
    val (gameDefinition, draws) = line.split(':')

    val gameId = gameDefinition.split(' ').last().toInt()
    val cubes = draws.split(';').flatMap { parseCubeDraws(it) }

    return Game(gameId, cubes)
}


private fun solve(input: List<String>): Int {
    return input.map { line -> parseGame(line) }.filter { game -> game.isValid() }.sumOf { it.id }
}

fun main() {
    val testInput = readInputLines("Ex2_test")
    check(solve(testInput) == 8)

    val input = readInputLines("Ex2")
    println(solve(input))
}