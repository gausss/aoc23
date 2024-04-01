data class Node(val name: String, val left: String, val right: String) {
    fun traverse(direction: Char): String? {
        return when (direction) {
            'L' -> left
            'R' -> right
            else -> null
        }
    }
}

fun main() {

    fun parseNode(line: String): Node {
        val name = line.substring(0, 3)
        val left = line.substring(7, 10)
        val right = line.substring(12, 15)

        return Node(name, left, right)
    }

    fun solve(input: List<String>): Int {
        val steps = input.first()
        val nodes = input.subList(2, input.size).map { parseNode(it) }
        val nodeLookup = nodes.associateBy { it.name }

        var stepCount = 0
        var currentNode = nodeLookup["AAA"]
        while (currentNode?.name != "ZZZ") {
            for (step in steps.toCharArray()) {
                stepCount++
                currentNode?.traverse(step)?.let { currentNode = nodeLookup[it] }
                if (currentNode?.name == "ZZZ") {
                    break
                }
            }
        }
        return stepCount
    }

    val testInput = readInputLines("Ex8_test")
    check(solve(testInput) == 6)

    val input = readInputLines("Ex8")
    println(solve(input))
}
