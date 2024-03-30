import kotlin.math.pow

data class ScratchCard(val winningNumbers: Set<String>, val scratchedNumbers: Set<String>) {
    fun getWorth(): Int {
        val matches = scratchedNumbers.intersect(winningNumbers).size
        return if (matches == 0) 0 else 2.0.pow(matches - 1).toInt()
    }
}

fun main() {
    val splitWhitespace = "\\s+".toRegex()

    fun parseCard(line: String): ScratchCard {
        val (_, numbers) = line.split(':')
        val (winningNumbersString, cardNumbersString) = numbers.split('|').map { it.trim() }
        val winningNumbers = winningNumbersString.split(splitWhitespace).toSet()
        val cardNumbers = cardNumbersString.split(splitWhitespace).toSet()

        return ScratchCard(winningNumbers, cardNumbers)
    }

    fun solve(input: List<String>): Int {
        return input.sumOf { parseCard(it).getWorth() }
    }


    val testInput = readInput("Ex4_test")
    check(solve(testInput) == 13)

    val input = readInput("Ex4")
    println(solve(input))
}
