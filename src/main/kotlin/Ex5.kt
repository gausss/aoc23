data class MappingGroup(val mappings: List<RangeMapping>) {
    fun map(source: Long): Long {
        return mappings.find { it.canMap(source) }?.map(source) ?: source
    }
}

data class RangeMapping(val destinationStart: Long, val sourceStart: Long, val length: Long) {
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

fun main() {
    fun parseSeedMapping(mappingString: String): MappingGroup {
        val mappings = mappingString.split(':').last().trim().split("\n")
            .map { mappingLine ->
                val (destination, source, range) = mappingLine.split(WHITESPACE_REGEX).map { it.toLong() }
                RangeMapping(destination, source, range)
            }
        return MappingGroup(mappings)
    }

    fun parseSeedNumbers(seeds: String): List<Long> {
        return seeds.split(':').last().trim().split(WHITESPACE_REGEX).map { it.toLong() }
    }

    fun getSeedLocation(seed: Long, mappings: List<MappingGroup>): Long {
        return mappings.fold(seed) { destination, mapping -> mapping.map(destination) }
    }

    fun solve(input: String): Long {
        val splits = input.split("%")
        val seeds = parseSeedNumbers(splits.first())
        val mappings = splits.tail().map { parseSeedMapping(it) }
        println("Parsing Done")

        return seeds.minOf { getSeedLocation(it, mappings) }
    }


    val testInput = readInput("Ex5_test")
    check(solve(testInput) == 35L)

    val input = readInput("Ex5")
    println(solve(input))
}
