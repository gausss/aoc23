fun main() {
    fun parseEnclosingDigit(line: String): Int {
        val firstDigit = line.first { it.isDigit() }
        val lastDigit = line.last { it.isDigit() }
        return "$firstDigit$lastDigit".toInt()
    }

    fun part1(input: List<String>): Int {
        return input.sumOf { line -> parseEnclosingDigit(line) }
    }


    val testInput = readInput("Ex1_test")
    check(part1(testInput) == 142)

    val input = readInput("Ex1")
    println(part1(input))
}
