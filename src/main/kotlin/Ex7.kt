private enum class HandType {
    HIGH_CARD, ONE_PAIR, TWO_PAIR, THREE_OF_KIND, FULL_HOUSE, FOUR_OF_KIND, FIVE_OF_KIND
}

private data class Hand(val cards: String, val bid: Int) : Comparable<Hand> {
    private val strengths = listOf('2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K', 'A')
    private fun getType(): HandType {
        val cardCounts = cards.groupingBy { it }.eachCount().values
        return when {
            5 in cardCounts -> HandType.FIVE_OF_KIND
            4 in cardCounts -> HandType.FOUR_OF_KIND
            3 in cardCounts && cardCounts.size == 2 -> HandType.FULL_HOUSE
            3 in cardCounts -> HandType.THREE_OF_KIND
            2 in cardCounts && cardCounts.size == 3 -> HandType.TWO_PAIR
            2 in cardCounts -> HandType.ONE_PAIR
            else -> HandType.HIGH_CARD
        }
    }

    override fun compareTo(other: Hand): Int {
        val typeComparison = this.getType().compareTo(other.getType())
        if (typeComparison != 0) {
            return typeComparison
        }

        val thisStrengths = this.cards.map { strengths.indexOf(it) }
        val otherStrengths = other.cards.map { strengths.indexOf(it) }
        val strengthPairs = thisStrengths.zip(otherStrengths)
        for (strength in strengthPairs) {
            val comparison = strength.first.compareTo(strength.second)
            if (comparison != 0) {
                return comparison
            }
        }

        return 0
    }
}

private fun parseHand(line: String): Hand {
    val (cards, bid) = line.split(' ')
    return Hand(cards, bid.toInt())
}

private fun solve(input: List<String>): Int {
    return input.map { parseHand(it) }
        .sorted()
        .mapIndexed { index, hand -> (index + 1) * hand.bid }
        .sum()
}


fun main() {
    val testInput = readInputLines("Ex7_test")
    check(solve(testInput) == 6440)

    val input = readInputLines("Ex7")
    println(solve(input))
}
