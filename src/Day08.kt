fun main() {
    fun lookupElement(key: Char, nodes: Pair<String, String>) = when (key) {
        'R' -> nodes.second
        'L' -> nodes.first
        else -> null
    }

    fun part1(input: List<String>): Long {
        val instruction = input.first().toCharArray()
        val nodes = input.drop(2).associate { n ->
            val mapNodes = n.split(" = ")
            val key = mapNodes.first()
            val value = mapNodes
                    .last().removePrefix("(").removeSuffix(")")
                    .split(", ")
            key to Pair(value.first(), value.last())
        }

        var node = "AAA"
        var steps = 0L
        while (node != "ZZZ") {
            instruction.forEach { i ->
                node = lookupElement(i, (nodes[node]) ?: return steps) ?: return steps
                steps++
            }
        }

        return steps
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day08_test")
    check(part1(testInput) == 6L)
    // check(part2(testInput) == 1)

    val input = readInput("Day08")
    part1(input).println()
    //  part2(input).println()
}