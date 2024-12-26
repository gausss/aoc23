private fun parseEnclosingDigit(line: String): Int {
    val firstDigit = line.first { it.isDigit() }
    val lastDigit = line.last { it.isDigit() }
    return "$firstDigit$lastDigit".toInt()
}

private fun solve(input: List<String>): Int {
    return input.sumOf { line -> parseEnclosingDigit(line) }
}

fun main() {
    val testInput = readInputLines("Ex1_test")
    check(solve(testInput) == 142)

    val input = readInputLines("Ex1")
    println(solve(input))
}