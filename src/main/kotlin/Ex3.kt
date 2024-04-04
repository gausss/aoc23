data class SchematicNumber(val value: Int, val start: Position, val end: Position) {
    private fun isAdjacentTo(position: Position): Boolean {
        val numberRange = (start.x..end.x).map { Position(it, start.y) }.toSet()
        return numberRange.intersect(position.getAdjacent()).isNotEmpty()
    }

    fun isAdjacentTo(position: Set<Position>): Boolean {
        return position.any { this.isAdjacentTo(it) }
    }
}

data class EngineSchematic(val numbers: Set<SchematicNumber>, val symbols: Set<Position>)

fun main() {
    fun parseSchematic(lines: List<String>): EngineSchematic {
        val symbols = mutableSetOf<Position>()
        val numbers = mutableSetOf<SchematicNumber>()

        for ((y, row) in lines.withIndex()) {
            var numberStart: Position? = null
            var numberValue = ""
            for ((x, value) in row.withIndex()) {
                if (value.isDigit()) {
                    if (numberStart == null) {
                        numberStart = Position(x, y)
                    }
                    numberValue += value
                } else {
                    if (numberStart != null) {
                        numbers.add(SchematicNumber(numberValue.toInt(), numberStart, Position(x - 1, y)))
                        numberStart = null
                        numberValue = ""
                    }
                    if (value != '.') {
                        symbols.add(Position(x, y))
                    }
                }
            }

            if (numberStart != null) {
                numbers.add(SchematicNumber(numberValue.toInt(), numberStart, Position(row.length - 1, y)))
            }
        }

        return EngineSchematic(numbers, symbols)
    }

    fun solve(input: List<String>): Int {
        val schematic = parseSchematic(input)
        return schematic.numbers.filter { it.isAdjacentTo(schematic.symbols) }.sumOf { it.value }
    }


    val testInput = readInputLines("Ex3_test")
    check(solve(testInput) == 4361)

    val input = readInputLines("Ex3")
    println(solve(input))
}
