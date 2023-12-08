enum class CardType {
    FIVEOFAKIND,
    FOUROFAKIND,
    FULLHOUSE,
    THREEOFAKIND,
    TWOPAIR,
    ONEPAIR,
    HIGHCARD
}

fun cardRank(card: Char): Int = when (card) {
    'A' -> 14
    'K' -> 13
    'Q' -> 12
    'J' -> 11
    'T' -> 10
    else -> card.digitToIntOrNull() ?: 0
}

fun cardRankJ(card: Char): Int = when (card) {
    'A' -> 14
    'K' -> 13
    'Q' -> 12
    'J' -> 1
    'T' -> 10
    else -> card.digitToIntOrNull() ?: 0
}

fun main() {
    fun cardScoring(hand: String, card: Map<Char, Int>): Pair<CardType, String> {
        return when (true) {
            card.containsValue(5) -> Pair(CardType.FIVEOFAKIND, hand)
            card.containsValue(4) -> Pair(CardType.FOUROFAKIND, hand)
            (card.containsValue(3) && card.containsValue(2)) -> Pair(CardType.FULLHOUSE, hand)
            card.containsValue(3) -> Pair(CardType.THREEOFAKIND, hand)
            (card.count { (_, v) -> v == 2 } == 2) -> Pair(CardType.TWOPAIR, hand)
            card.containsValue(2) -> Pair(CardType.ONEPAIR, hand)
            else -> Pair(CardType.HIGHCARD, hand)
        }
    }


    fun part1(input: List<String>): Int {
        val cardsBets = input
                .map { it.split(" ") }.associate { cb -> cb.first() to cb.last().toInt() }

        val score = cardsBets.keys
                .map { hand -> Pair(hand, hand.groupingBy { it }.eachCount()) }.map {
                    cardScoring(it.first, it.second)
                }
                .sortedWith(compareBy<Pair<CardType, String>> { it.first }
                        .thenByDescending { cardRank(it.second[0]) }
                        .thenByDescending { cardRank(it.second[1]) }
                        .thenByDescending { cardRank(it.second[2]) }
                        .thenByDescending { cardRank(it.second[3]) }
                        .thenByDescending { cardRank(it.second[4]) }
                        .thenByDescending { cardRank(it.second[5]) }
                )
                .reversed()
                .foldIndexed(0) { index, acc, pair ->
                    acc + (index + 1) * (cardsBets[pair.second] ?: 0)
                }

        return score
    }

    fun part2(input: List<String>): Int {
        val cardsBets = input
                .map { it.split(" ") }.associate { cb -> cb.first() to cb.last().toInt() }

        val score = cardsBets.keys
                .map { hand ->
                    Pair(hand, hand.groupingBy { it }.eachCount())
                }.map {

                    val rV = it.second
                            .toList().sortedWith(compareByDescending<Pair<Char, Int>> { d ->
                                d.second
                            }.thenByDescending { e -> cardRankJ(e.first) }).first().first

                    val r = if (rV == 'J') {
                        it.second
                                .toList().sortedWith(compareByDescending<Pair<Char, Int>> { d ->
                                    d.second
                                }.thenByDescending { e -> cardRankJ(e.first) }).getOrNull(1)?.first ?: 'A'
                    } else {
                        rV
                    }

                    Pair(it.first, it.first.replace('J', r))
                }.map { p ->
                    Triple(p.first, p.second, p.second.groupingBy { it }.eachCount())
                }
                .map {
                    Pair(it.first, cardScoring(it.second, it.third))
                }
                .sortedWith(compareBy<Pair<String, Pair<CardType, String>>> { it.second.first }
                        .thenByDescending { cardRankJ(it.first[0]) }
                        .thenByDescending { cardRankJ(it.first[1]) }
                        .thenByDescending { cardRankJ(it.first[2]) }
                        .thenByDescending { cardRankJ(it.first[3]) }
                        .thenByDescending { cardRankJ(it.first[4]) }
                        .thenByDescending { cardRankJ(it.first[5]) }
                )
                .reversed()
                .foldIndexed(0) { index, acc, pair ->
                    acc + (index + 1) * (cardsBets[pair.first] ?: 0)
                }

        return score
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
    check(part1(testInput) == 6440)
    check(part2(testInput) == 5905)


    val input = readInput("Day07")
    part1(input).println()
    part2(input).println()
}