fun main() {
    fun part1(input: List<String>): Int {
        val answer = input.map { str -> str.toCharArray().map { it.digitToIntOrNull() }.filterNotNull() }.mapNotNull {
            if (it.size > 1) {
                "${it.first()}${it.last()}".toIntOrNull()
            } else if (it.size == 1) {
                "${it.first()}${it.first()}".toIntOrNull()
            } else {
                null
            }
        }.sum()

        return answer
    }

    fun part2(input: List<String>): Int {
        val wordToInt = mapOf(
                "one" to "1",
                "two" to "2",
                "three" to "3",
                "four" to "4",
                "five" to "5",
                "six" to "6",
                "seven" to "7",
                "eight" to "8",
                "nine" to "9",
                "1" to "1",
                "2" to "2",
                "3" to "3",
                "4" to "4",
                "5" to "5",
                "6" to "6",
                "7" to "7",
                "8" to "8",
                "9" to "9",
        )

        val indexofWords = input.map { it ->
            wordToInt.flatMap { (word, number) ->
                val wordIndexes = mutableListOf<Pair<String, Int>>()
                var index = -1
                do {
                    val newIndex = it.indexOf(word, index + 1)
                    wordIndexes.add(Pair(number, newIndex))
                    index = newIndex
                } while (index != -1)
                return@flatMap wordIndexes
            }.filter { p -> p.second != -1 }.sortedBy { it.second }.map { it.first.toInt() }
        }.mapNotNull {
            if (it.size > 1) {
                "${it.first()}${it.last()}".toIntOrNull()
            } else if (it.size == 1) {
                "${it.first()}${it.first()}".toIntOrNull()
            } else {
                null
            }
        }.sum()

        return indexofWords
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 142)

    val input = readInput("Day01")
    part1(input).println()

    check(part2(testInput) == 281)
    part2(input).println()
}
