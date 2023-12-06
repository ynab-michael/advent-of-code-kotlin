package Day06

import readInput
import java.math.BigInteger

fun main() {
    fun part1(input: List<String>): Int {
        fun distanceFromHoldTime(maxRaceMillis: Int, holdMillis: Int): Int {
            val travelMillis = maxRaceMillis - holdMillis
            return travelMillis * holdMillis
        }

        fun holdTimesWhichWin(gameMillis: Int, distanceToWin: Int):List<Int> {
            return buildList {
                for (i in 0..gameMillis) {
                    if (distanceFromHoldTime(gameMillis, i) > distanceToWin ) {
                        add(i)
                    }
                }
            }
        }

        // games is list of pairs gameTime->winDistance
        val games = mutableListOf<Pair<Int,Int>>()
        val (_,timesString) = input[0].split(":")
        val (_,distancesString) = input[1].split(":")
        val times = timesString.split("""\s+""".toRegex())
        val distances = distancesString.split("""\s+""".toRegex())
        for (i in 1..<times.size) {
            games.add(times[i].toInt() to distances[i].toInt())
        }

        var sum = 1
        for(game in games) {
            val winningTimes = holdTimesWhichWin(game.first, game.second)
            println("For a game of ${game.first} milliseconds, ${winningTimes.size} ways to beat ${game.second} millimeters: $winningTimes")
            sum *= winningTimes.size
        }

        println("answer: $sum")
        return sum
    }

    fun part2(input: List<String>): Int {
        fun distanceFromHoldTime(maxRaceMillis: Int, holdMillis: Int): BigInteger {
            val travelMillis = maxRaceMillis - holdMillis
            return travelMillis.toBigInteger() * holdMillis.toBigInteger()
        }

        fun holdTimesWhichWin(gameMillis: Int, distanceToWin: BigInteger):Int {
            // It may be faster to start at the center and test outward
            var i = gameMillis/2
            while (distanceFromHoldTime(gameMillis, i) > distanceToWin) {
                i++
            }
            i--
            val max = i
            i = gameMillis/2 - 1
            while (distanceFromHoldTime(gameMillis, i) > distanceToWin) {
                i--
            }
            val min = i
            return max-min
        }

        // games is list of pairs gameTime->winDistance
        val (_,timesString) = input[0].split(":")
        val (_,distancesString) = input[1].split(":")
        val time = timesString.replace(" ", "").toInt()
        val distance = distancesString.replace(" ", "").toBigInteger()

        val winningTimes = holdTimesWhichWin(time, distance)
        println("For a game of $time milliseconds, $winningTimes ways to beat $distance millimeters")

        return winningTimes
    }

    val testInput = readInput("Day06/test")
    check(part1(testInput) == 288)
    println("test part 1 passed")
    check(part2(testInput) == 71503)
    println("test part 2 passed")

    val input = readInput("Day06/input")
    println("Part 1: ${part1(input)}")
    println("Part 2: ${part2(input)}")
}
