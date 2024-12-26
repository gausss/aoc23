import kotlin.math.pow

private data class ScratchCard(val winningNumbers: Set<String>, val scratchedNumbers: Set<String>) {
    fun getWorth(): Int {
        val matches = scratchedNumbers.intersect(winningNumbers).size
        return if (matches == 0) 0 else 2.0.pow(matches - 1).toInt()
    }
}

private fun parseCard(line: String): ScratchCard {
    val (_, numbers) = line.split(':')
    val (winningNumbersString, cardNumbersString) = numbers.split('|').map { it.trim() }
    val winningNumbers = winningNumbersString.split(WHITESPACE_REGEX).toSet()
    val cardNumbers = cardNumbersString.split(WHITESPACE_REGEX).toSet()

    return ScratchCard(winningNumbers, cardNumbers)
}

private fun solve(input: List<String>): Int {
    return input.sumOf { parseCard(it).getWorth() }
}

fun main() {
    val testInput = readInputLines("Ex4_test")
    check(solve(testInput) == 13)

    val input = readInputLines("Ex4")
    println(solve(input))
}
