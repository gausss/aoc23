fun main() {

    fun parseNumbers(line: String): List<Int> {
        val (_, timeValues) = line.split(':')
        return timeValues.trim().split(WHITESPACE_REGEX).map { it.toInt() }
    }

    fun getPossibleDistances(time: Int): List<Int> {
        val distances = mutableListOf<Int>()
        val timeRange = 0..time
        for (holdTime in timeRange) {
            val travelTime = time - holdTime
            val distance = travelTime * holdTime
            distances.add(distance)
        }

        return distances
    }

    fun getWinningDistances(distances: List<Int>, record: Int): List<Int> {
        return distances.filter { it > record }
    }

    fun solve(input: List<String>): Int {
        val (times, distanceRecords) = input.map { parseNumbers(it) }
        return times.map { getPossibleDistances(it) }
            .mapIndexed { index, distances -> getWinningDistances(distances, distanceRecords.elementAt(index)) }
            .fold(1) { acc, winningDistances -> winningDistances.size * acc }
    }

    val testInput = readInputLines("Ex6_test")
    check(solve(testInput) == 288)

    val input = readInputLines("Ex6")
    println(solve(input))
}
