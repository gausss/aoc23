fun main() {
    fun part1(input: List<String>): Int {
        return 0
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    val testInput = readInput("Ex1_test")
    println(testInput[0]);
    check(part1(testInput) == 3)
    check(part2(testInput) == 34)

    val input = readInput("Ex1")
    println(part1(input))
    println(part2(input))
}
