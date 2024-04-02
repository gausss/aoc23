data class SensorSequence(val values: List<Int>) {
    fun isAllZeros() = values.all { it == 0 }

    fun getDiffs() = SensorSequence(values.zipWithNext().map { it.second - it.first })
}

fun main() {
    fun extrapolateNextValue(sequence: SensorSequence): Int {
        if (sequence.isAllZeros()) {
            return 0
        } else {
            val diff = extrapolateNextValue(sequence.getDiffs())
            return sequence.values.last() + diff
        }
    }

    fun parseSequence(line: String): SensorSequence {
        val values = line.split(WHITESPACE_REGEX).map { it.toInt() }
        return SensorSequence(values)
    }

    fun solve(input: List<String>): Int {
        return input.map { parseSequence(it) }.sumOf { extrapolateNextValue(it) }
    }

    val testInput = readInputLines("Ex9_test")
    check(solve(testInput) == 114)

    val input = readInputLines("Ex9")
    println(solve(input))
}
