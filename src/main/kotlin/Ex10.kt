enum class Direction {
    LEFT, UP, RIGHT, DOWN;

    fun opposite(): Direction {
        return when (this) {
            UP -> DOWN
            DOWN -> UP
            LEFT -> RIGHT
            RIGHT -> LEFT
        }
    }
}

enum class PipeType {
    NORT_WEST_J, SOUTH_WEST_7, SOUTH_EAST_F, EAST_NORTH_L, VERTICAL, HORIZONTAL, START, NONE;

    companion object {
        fun fromSign(sign: Char): PipeType {
            return when (sign) {
                'J' -> NORT_WEST_J
                '7' -> SOUTH_WEST_7
                'F' -> SOUTH_EAST_F
                'L' -> EAST_NORTH_L
                '-' -> HORIZONTAL
                '|' -> VERTICAL
                'S' -> START
                else -> NONE
            }
        }
    }
}

data class Pipe(val type: PipeType, val position: Position) {

    fun getPossibleDirections(comingFrom: Direction?): Set<Direction> {
        val comingFromDirections = setOfNotNull(comingFrom?.opposite())
        return when (type) {
            PipeType.HORIZONTAL -> setOf(Direction.RIGHT, Direction.LEFT)
            PipeType.VERTICAL -> setOf(Direction.UP, Direction.DOWN)
            PipeType.EAST_NORTH_L -> setOf(Direction.UP, Direction.RIGHT)
            PipeType.NORT_WEST_J -> setOf(Direction.UP, Direction.LEFT)
            PipeType.SOUTH_WEST_7 -> setOf(Direction.DOWN, Direction.LEFT)
            PipeType.SOUTH_EAST_F -> setOf(Direction.DOWN, Direction.RIGHT)
            PipeType.START -> Direction.entries.toSet()
            PipeType.NONE -> emptySet()
        } - comingFromDirections
    }

    fun getNeighbor(direction: Direction): Position {
        return when (direction) {
            Direction.UP -> Position(position.x, position.y - 1)
            Direction.DOWN -> Position(position.x, position.y + 1)
            Direction.LEFT -> Position(position.x - 1, position.y)
            Direction.RIGHT -> Position(position.x + 1, position.y)
        }
    }

    fun canConnect(other: Pipe, direction: Direction): Boolean {
        return when (type) {
            PipeType.HORIZONTAL -> when (direction) {
                Direction.RIGHT -> other.type in listOf(
                    PipeType.SOUTH_WEST_7,
                    PipeType.HORIZONTAL,
                    PipeType.NORT_WEST_J,
                    PipeType.START
                )

                Direction.LEFT -> other.type in listOf(
                    PipeType.HORIZONTAL,
                    PipeType.SOUTH_EAST_F,
                    PipeType.EAST_NORTH_L,
                    PipeType.START
                )

                else -> false
            }

            PipeType.VERTICAL -> when (direction) {
                Direction.UP -> other.type in listOf(
                    PipeType.VERTICAL, PipeType.SOUTH_EAST_F, PipeType.SOUTH_WEST_7,
                    PipeType.START
                )

                Direction.DOWN -> other.type in listOf(
                    PipeType.VERTICAL, PipeType.NORT_WEST_J, PipeType.EAST_NORTH_L,
                    PipeType.START
                )

                else -> false
            }

            PipeType.EAST_NORTH_L -> when (direction) {
                Direction.UP -> other.type in listOf(
                    PipeType.SOUTH_WEST_7, PipeType.VERTICAL, PipeType.SOUTH_EAST_F,
                    PipeType.START
                )

                Direction.RIGHT -> other.type in listOf(
                    PipeType.HORIZONTAL,
                    PipeType.NORT_WEST_J,
                    PipeType.SOUTH_WEST_7,
                    PipeType.START
                )

                else -> false
            }

            PipeType.NORT_WEST_J -> when (direction) {
                Direction.UP -> other.type in listOf(
                    PipeType.SOUTH_WEST_7, PipeType.VERTICAL, PipeType.SOUTH_EAST_F,
                    PipeType.START
                )

                Direction.LEFT -> other.type in listOf(
                    PipeType.HORIZONTAL,
                    PipeType.SOUTH_EAST_F,
                    PipeType.EAST_NORTH_L,
                    PipeType.START
                )

                else -> false
            }

            PipeType.SOUTH_WEST_7 -> when (direction) {
                Direction.DOWN -> other.type in listOf(
                    PipeType.EAST_NORTH_L, PipeType.VERTICAL, PipeType.NORT_WEST_J,
                    PipeType.START
                )

                Direction.LEFT -> other.type in listOf(
                    PipeType.HORIZONTAL,
                    PipeType.SOUTH_EAST_F,
                    PipeType.EAST_NORTH_L,
                    PipeType.START
                )

                else -> false
            }

            PipeType.SOUTH_EAST_F -> when (direction) {
                Direction.DOWN -> other.type in listOf(
                    PipeType.EAST_NORTH_L, PipeType.VERTICAL, PipeType.NORT_WEST_J,
                    PipeType.START
                )

                Direction.RIGHT -> other.type in listOf(
                    PipeType.HORIZONTAL,
                    PipeType.SOUTH_WEST_7,
                    PipeType.NORT_WEST_J,
                    PipeType.START
                )

                else -> false
            }

            PipeType.START -> other.type != PipeType.NONE
            else -> false
        }
    }
}

fun main() {

    fun parsePipes(input: List<String>): Map<Position, Pipe> {
        return input.flatMapIndexed { y, row ->
            row.mapIndexed { x, sign ->
                Pipe(
                    PipeType.fromSign(sign),
                    Position(x, y)
                )
            }
        }.associateBy { pipe -> pipe.position }
    }

    fun solve(input: List<String>): Int {
        val pipes = parsePipes(input)
        val start = pipes.values.find { pipe -> pipe.type == PipeType.START }
        checkNotNull(start)

        var stepCounter = 0
        var currentPipe: Pipe = start
        var comingFrom: Direction? = null
        while (currentPipe.type != PipeType.START || stepCounter == 0) {
            for (direction in currentPipe.getPossibleDirections(comingFrom)) {
                val neighborPosition = currentPipe.getNeighbor(direction)
                val neighborPipe = pipes[neighborPosition] ?: Pipe(PipeType.NONE, Position(0, 0))

                if (currentPipe.canConnect(neighborPipe, direction)) {
                    println("Step $stepCounter: Moving $direction from $currentPipe to $neighborPipe")
                    stepCounter++
                    comingFrom = direction
                    currentPipe = neighborPipe
                    break
                }
            }
        }

        return stepCounter / 2
    }

    val testInput = readInputLines("Ex10_test")
    check(solve(testInput) == 8)

    val input = readInputLines("Ex10")
    println(solve(input))
}
