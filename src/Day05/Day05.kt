package Day05

import readInput
import java.math.BigInteger

fun main() {

    class SourceToDestination(val destinationStart: BigInteger, val sourceStart: BigInteger, val range:BigInteger) {
        fun inRange(source:BigInteger): Boolean {
            return source >= sourceStart && source < sourceStart+range
        }

        fun translate(source:BigInteger):BigInteger {
            return source - sourceStart + destinationStart
        }
    }

    fun part1(input: List<String>): BigInteger {
        val (_,seedsText) = input.first().split(": ")
        val seeds = seedsText.split(" ").map { it.toBigInteger() }

        val maps = mutableListOf<MutableList<SourceToDestination>>()

        var currentMap = mutableListOf<SourceToDestination>()
        for (line in input.slice(3..<input.size)) {
            if (line == "") {
                // finished building this map
                maps.add(currentMap)
                currentMap = mutableListOf<SourceToDestination>()
                continue
            }
            if (line.contains("map")) {
                continue
            }
            // add this line to the map
            val (dest, source, range) = line.split(" ").map { it.toBigInteger() }
            currentMap.add(SourceToDestination(dest, source, range))
        }

        val finals = mutableListOf<BigInteger>()
        for (seed in seeds) {
            var num = seed
            for (map in maps) {
                val applicableLookup = map.find { it.inRange(num) }
                if (applicableLookup != null) {
                    num = applicableLookup.translate(num)
                }
            }
            finals.add(num)
        }
        return finals.min()
    }

    fun part2(input: List<String>): BigInteger {
        val (_,seedsText) = input.first().split(": ")
        val seeds = mutableListOf<BigInteger>()
        var index: BigInteger? = null
        for (part in seedsText.split(" ")) {
            if (index == null) {
                index = part.toBigInteger()
            } else {
                val end = part.toBigInteger() - 1.toBigInteger() + index
                while (index < end) {
                    seeds.add(index)
                    index++
                }
                index = null
            }
        }

        val maps = mutableListOf<MutableList<SourceToDestination>>()

        var currentMap = mutableListOf<SourceToDestination>()
        for (line in input.slice(3..<input.size)) {
            if (line == "") {
                // finished building this map
                maps.add(currentMap)
                currentMap = mutableListOf<SourceToDestination>()
                continue
            }
            if (line.contains("map")) {
                continue
            }
            // add this line to the map
            val (dest, source, range) = line.split(" ").map { it.toBigInteger() }
            currentMap.add(SourceToDestination(dest, source, range))
        }

        var lowest = (0).toBigInteger()
        for (seed in seeds) {
            var num = seed
            for (map in maps) {
                val applicableLookup = map.find { it.inRange(num) }
                if (applicableLookup != null) {
                    num = applicableLookup.translate(num)
                }
            }
            if (lowest == (0).toBigInteger() || num < lowest) {
                println(num)
                lowest = num
            }
        }
        return lowest
    }

    val testInput = readInput("Day05/test")
    val part1TestResult = part1(testInput)
    check(part1TestResult == (35).toBigInteger()) { "$part1TestResult != 35" }
    val part2TestResult = part2(testInput)
    check(part2TestResult == (46).toBigInteger()) { "$part2TestResult != 46" }
    println("test part 2 passed")

    val input = readInput("Day05/input")
    val part1Result = part1(input)
    println("Part 1: $part1Result")
    println("Part 2: ${part2(input)}")
}
