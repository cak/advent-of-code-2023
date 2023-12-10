import kotlin.io.path.Path
import kotlin.io.path.readText

suspend fun main() {

    fun getSourceDest(maps: List<List<String>>) = maps
            .drop(1).map {
                it.first() to it.last().split("\n")
                        .map { e ->
                            e.split(" ")
                                    .mapNotNull { t ->
                                        t.trim().toLongOrNull()
                                    }
                        }
            }.map { l ->
                Pair(l.first,
                        l.second.drop(1)
                                .map { w -> listOf(w[0] until (w[0] + w[2]), w[1] until (w[1] + w[2])) }
                                .map { r -> Pair(r.first(), r.last()) })
            }

    suspend fun part1(input: String): Long {
        val maps = input.split("\n\n")
                .map { m -> m.split(":") }

        val seeds = maps.take(1).last().last()
                .split(" ")
                .mapNotNull { it.trim().toLongOrNull() }

        val sourceDest = getSourceDest(maps)

        val solution = seeds.asSequence().map { s ->
            sourceDest.fold(s) { d, m ->
                m.second.asSequence().map { (dest, src) ->
                    if (d >= src.first && d <= src.last) {
                        (dest.first - src.first) + d
                    } else {
                        d
                    }
                }.firstOrNull { it != d } ?: d
            }
        }.toList().minOf { it }

        return solution
    }

    suspend fun part2(input: String): Long {
        val maps = input.split("\n\n")
                .map { m -> m.split(":") }

        val seeds = maps.take(1).last().last()
                .split(" ")
                .mapNotNull { it.trim().toLongOrNull() }.chunked(2).map {
                    it[0]..(it[0] + it[1])
                }

        val sourceDest = getSourceDest(maps)

        val solution = seeds.asSequence().map { r ->
            r.map { s ->
                sourceDest.fold(s) { d, m ->
                    m.second.asSequence().map { (dest, src) ->
                        if (d >= src.first && d <= src.last) {
                            (dest.first - src.first) + d
                        } else {
                            d
                        }
                    }.firstOrNull { it != d } ?: d
                }
            }
        }.toList().flatten().minOf { it }

        return solution

    }

    // test if implementation meets criteria from the description, like:
    val testInput = Path("input/Day05_test.txt").readText()
    // check(part1(testInput) == 35.toLong())
    // check(part2(testInput) == 46.toLong())

    val input = Path("input/Day05.txt").readText()
    //part1(input).println()
    part2(input).println()
}