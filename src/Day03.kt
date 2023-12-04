fun main() {
    fun part1(input: List<String>): Int {
        val axes = input
                .flatMapIndexed { yi, y ->
                    y.toCharArray()
                            .mapIndexed { xi, x ->
                                if (!x.isDigit() && x != '.') {
                                    Pair(xi, yi)
                                } else {
                                    null
                                }
                            }
                }.filterNotNull()

        val solution = input
                .flatMapIndexed { yi, y ->
                    Regex("(\\d+)").findAll(y).toList().filter { m ->
                        m.range.any { xi -> matchAxes(axes, xi, yi) }
                    }.mapNotNull { it.value.toIntOrNull() }
                }

        return solution.sum()
    }

    fun part2(input: List<String>): Int {
        val axes = input
                .flatMapIndexed { yi, y ->
                    y.toCharArray()
                            .mapIndexed { xi, x ->
                                if (!x.isDigit() && x != '.') {
                                    Pair(xi, yi)
                                } else {
                                    null
                                }
                            }
                }.filterNotNull()

        val solution = input
                .asSequence()
                .flatMapIndexed { yi, y ->
                    Regex("(\\d+)").findAll(y).toList().flatMap { m ->
                        m.range.mapNotNull { xi -> findMatchAxes(axes, xi, yi) }.map {
                            Pair(it, m.value.toInt())
                        }.distinct()
                    }
                }.groupBy { it.first }.toList().map { it.second.map { s -> s.second } }
                .filter { it.size >= 2 }.sumOf { g ->
                    g.reduce { acc, i -> acc * i }
                }

        return solution
    }


// test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 4361)
    check(part2(testInput) == 467835)

    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}


fun findMatchAxes(axes: List<Pair<Int, Int>>, xi: Int, yi: Int) =
        axes.find {
            it == Pair(xi - 1, yi)
        } ?: axes.find {
            it == Pair(xi + 1, yi)
        } ?: axes.find {
            it == Pair(xi, yi - 1)
        } ?: axes.find {
            it == Pair(xi, yi + 1)
        } ?: axes.find {
            it == Pair(xi + 1, yi + 1)
        } ?: axes.find {
            it == Pair(xi + 1, yi - 1)
        } ?: axes.find {
            it == Pair(xi - 1, yi - 1)
        } ?: axes.find {
            it == Pair(xi - 1, yi + 1)
        }

fun matchAxes(axes: List<Pair<Int, Int>>, xi: Int, yi: Int) =
        axes.contains(Pair(xi - 1, yi))
                || axes.contains(Pair(xi + 1, yi))

                || axes.contains(Pair(xi, yi - 1))
                || axes.contains(Pair(xi, yi + 1))

                || axes.contains(Pair(xi + 1, yi + 1))
                || axes.contains(Pair(xi + 1, yi - 1))

                || axes.contains(Pair(xi - 1, yi - 1))
                || axes.contains(Pair(xi - 1, yi + 1))