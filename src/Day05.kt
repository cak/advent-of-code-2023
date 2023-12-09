import kotlin.io.path.Path
import kotlin.io.path.readText

fun main() {
    fun part1(input: String): Int {
        val maps = input.split("\n\n")
            .map { m -> m.split(":")}
  
        val seeds = maps.take(1).last().last()
            .split(" ")
            .mapNotNull { it.trim().toIntOrNull() }
        
        println(seeds)

        val sourceDest = maps.drop(1).map {
            it.first() to it.last().split("\n")
                .map { it.split(" ")
                    .mapNotNull {
                        it.trim().toIntOrNull()
                    }
            }}.map { l ->
                Pair(l.first,
                     l.second.drop(1)
                         .map { w -> listOf(w[0] .. (w[0] + w[2]), w[1] .. (w[1] + w[2]))})
                

            }
            .forEach { println(it) }
        
        return 1
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput= Path("input/Day05_test.txt").readText()
    check(part1(testInput) == 1)
    // check(part2(testInput) == 1)

    val input = Path("input/Day05_test.txt").readText()
   // part1(input).println()
    //  part2(input).println()
}