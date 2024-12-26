private data class AlmanacMappingTable(val mappings: List<AlmanacMapping>) {
    fun map(source: Long): Long {
        return mappings.find { it.canMap(source) }?.map(source) ?: source
    }
}

private data class AlmanacMapping(val destinationStart: Long, val sourceStart: Long, val length: Long) {
    private val sourceRange = sourceStart..<sourceStart + length
    private val destinationRange = destinationStart..<destinationStart + length

    fun canMap(source: Long): Boolean {
        return sourceRange.contains(source)
    }

    fun map(source: Long): Long? {
        val sourceMatchIndex = sourceRange.indexOfFirst { it == source }
        return destinationRange.elementAtOrNull(sourceMatchIndex)
    }
}

private fun parseSeedMapping(mappingString: String): AlmanacMappingTable {
    val mappings = mappingString.split(':').last().trim().split("\n")
        .map { mappingLine ->
            val (destination, source, range) = mappingLine.split(WHITESPACE_REGEX).map { it.toLong() }
            AlmanacMapping(destination, source, range)
        }
    return AlmanacMappingTable(mappings)
}

private fun parseSeedNumbers(seeds: String): List<Long> {
    return seeds.split(':').last().trim().split(WHITESPACE_REGEX).map { it.toLong() }
}

private fun getSeedLocation(seed: Long, mappings: List<AlmanacMappingTable>): Long {
    return mappings.fold(seed) { destination, mapping -> mapping.map(destination) }
}

private fun solve(input: String): Long {
    val splits = input.split("%")
    val seeds = parseSeedNumbers(splits.first())
    val almanac = splits.tail().map { parseSeedMapping(it) }

    return seeds.minOf { getSeedLocation(it, almanac) }
}

fun main() {
    val testInput = readInput("Ex5_test")
    check(solve(testInput) == 35L)

    val input = readInput("Ex5")
    println(solve(input))
}
