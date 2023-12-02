fun main() {
    fun part1(input: List<String>): Int {
        val puzzleCubes = mapOf(
                "red" to 12,
                "green" to 13,
                "blue" to 14
        )

        val answer = input.map { ln ->
            val gameAndCubes = ln.removePrefix("Game ").split(":")
            val game = gameAndCubes.first().trim()
            val cubes = gameAndCubes.last().split(";").map {
                it.split(",").map { c ->
                    c.split(" ").reversed().filter { gc -> gc.trim() != "" }
                }.associate { x ->
                    x.first() to x.last().toInt()
                }
            }

            return@map Pair(game, cubes)
        }.filter { (_, cubes) ->
            cubes.all { c ->
                puzzleCubes.all { (p, i) ->
                    c.getOrDefault(p, 0) <= i
                }
            }
        }.sumOf {
            it.first.toInt()
        }

        return answer
    }

    fun part2(input: List<String>): Int {
        val answer = input.sumOf { ln ->
            val gameAndCubes = ln.removePrefix("Game ").split(":")
            val cubes = gameAndCubes.last().split(";").flatMap {
                it.split(",").map { c ->
                    c.split(" ").reversed().filter { gc -> gc.trim() != "" }
                }.map { x ->
                    Pair(x.first(), x.last().toInt())
                }
            }.sortedByDescending { it.second }.groupBy({ it.first }, { it.second }).toMap().mapValues {
                it.value.first()
            }.values.reduce { acc, i -> acc * i }
            cubes
        }

        return answer
    }

// test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 8)
    check(part2(testInput) == 2286)


    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}