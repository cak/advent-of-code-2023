fun main() {
    fun part1(input: List<String>): Int {
        val (time, distance) = input.map { i ->
            i.split("  ").map { s -> s.trim() }.drop(1)
                    .filter { it.isNotEmpty() }
                    .mapNotNull { z ->
                        z.toIntOrNull()
                    }
        }

        val timeDistance = time.zip(distance).map { (t, d) ->
            (0..t).count { i ->
                ((t - i) * i) > d
            }
        }.reduce { acc, i -> acc * i }

        return timeDistance
    }

    fun part2(input: List<String>): Int {
        val (time, distance) = input.map { i ->
            i.replace(" ", "").split(":").drop(1)
                    .filter { it.isNotEmpty() }
                    .mapNotNull { z ->
                        z.toLongOrNull()
                    }
        }

        val timeDistance = time.zip(distance).map { (t, d) ->
            (0..t).count { i ->
                ((t - i) * i) > d
            }
        }.reduce { acc, i -> acc * i }

        return timeDistance
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
    check(part1(testInput) == 288)
    check(part2(testInput) == 71503)

    val input = readInput("Day06")
    part1(input).println()
    part2(input).println()
}