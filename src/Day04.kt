fun main() {
    fun part1(input: List<String>): Int {
        val solution = input.asSequence().map { c ->
            c.split(":").last().split(" | ")
        }.map {
            it
                    .map { l -> l.split(" ") }
                    .map { r -> r.mapNotNull { d -> d.toIntOrNull() } }.takeLast(2).flatten()
                    .groupingBy { p -> p }.eachCount().filter { w -> w.value > 1 }.keys
                    .toList()
        }.filter { it.isNotEmpty() }.map { List(it.size) { index -> index + 1 }.reduce { acc, _ -> acc * 2 } }.sum()

        return solution
    }

    fun part2(input: List<String>): Int {
        val cards = input.map { c ->
            c.removePrefix("Card ").split(":").map { it.split(" | ") }.flatMap {
                it
                        .map { l -> l.split(" ") }
                        .map { r -> r.mapNotNull { d -> d.toIntOrNull() } }
            }
        }

        return 0
    }


// test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 13)
// check(part2(testInput) == 1)

    val input = readInput("Day04")
    part1(input).println()
//  part2(input).println()
}