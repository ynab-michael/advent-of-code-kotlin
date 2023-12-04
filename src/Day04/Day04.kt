package Day04

import readInput

fun main() {

    fun part1(input: List<String>): Int {
        var sum = 0
        for (line in input) {
            val colonIndex = line.indexOf(':')
            val barIndex = line.indexOf('|')
            val winningNumberText = line.substring(colonIndex+1,barIndex).trim()
            val winningNumbers = winningNumberText.split("\\s+".toRegex())
            val yourNumberText = line.substring(barIndex+1).trim()
            val yourNumbers = yourNumberText.split("\\s+".toRegex())
            val intersections = winningNumbers.intersect(yourNumbers.toSet())
            if (intersections.size > 0) {
                sum += 1 shl (intersections.size - 1)
            }
        }
        return sum
    }

    fun part2(input: List<String>): Int {
        val numberOfEachCard = MutableList(input.size) { 1 }
        input.forEachIndexed { lineIndex, line ->
            val colonIndex = line.indexOf(':')
            val barIndex = line.indexOf('|')
            val winningNumberText = line.substring(colonIndex + 1, barIndex).trim()
            val winningNumbers = winningNumberText.split("\\s+".toRegex())
            val yourNumberText = line.substring(barIndex + 1).trim()
            val yourNumbers = yourNumberText.split("\\s+".toRegex())
            val matches = winningNumbers.intersect(yourNumbers.toSet())
            for (i in matches.indices) {
                numberOfEachCard[lineIndex + i + 1] += numberOfEachCard[lineIndex]
            }
        }
        return numberOfEachCard.sum()
    }

    val testInput = readInput("Day04/test")
    check(part1(testInput) == 13)
    println("test part 1 passed")
    check(part2(testInput) == 30)
    println("test part 2 passed")

    val input = readInput("Day04/input")
    println("Part 1: ${part1(input)}")
    println("Part 2: ${part2(input)}")
}
