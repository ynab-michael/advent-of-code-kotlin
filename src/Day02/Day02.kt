package Day02

import readInput

fun main() {
    class Result() {
        var red = 0
        var green = 0
        var blue = 0

           fun isPossible(): Boolean {
               // only 12 red cubes, 13 green cubes, and 14 blue cubes
               return red <= 12 && green <= 13 && blue <= 14
           }
    }

    fun part1(input: List<String>): Int {
        var sum = 0
        for (line in input) {
            // Get game ID
            val colonIndex = line.indexOf(":")
            val id = line.substring(5, colonIndex).toInt()
            // Populate list of results
            val allResultsString = line.substring(colonIndex+2)
            val resultStrings = allResultsString.split("; ")
            val results: MutableList<Result> = mutableListOf()
            for (encodedResult in resultStrings) {
                val encodedColors = encodedResult.split(", ")
                val result = Result()
                for (encodedColor in encodedColors) {
                    val numAndColor = encodedColor.split(" ")
                    when (numAndColor[1]) {
                        "red" -> result.red = numAndColor[0].toInt()
                        "green" -> result.green = numAndColor[0].toInt()
                        "blue" -> result.blue = numAndColor[0].toInt()
                    }
                }
                results.add(result)
            }
            // Validate game
            val invalid = results.count { !it.isPossible() }
            if (invalid == 0) {
                // game is valid; add ID to sum
                sum += id
            }
        }
        return sum
    }

    fun part2(input: List<String>): Int {
        var sum = 0
        for (line in input) {
            // Populate list of results
            val colonIndex = line.indexOf(":")
            val allResultsString = line.substring(colonIndex+2)
            val resultStrings = allResultsString.split("; ")
            val results: MutableList<Result> = mutableListOf()
            for (encodedResult in resultStrings) {
                val encodedColors = encodedResult.split(", ")
                val result = Result()
                for (encodedColor in encodedColors) {
                    val numAndColor = encodedColor.split(" ")
                    when (numAndColor[1]) {
                        "red" -> result.red = numAndColor[0].toInt()
                        "green" -> result.green = numAndColor[0].toInt()
                        "blue" -> result.blue = numAndColor[0].toInt()
                    }
                }
                results.add(result)
            }
            // Find the fewest cubes required for this game
            var minRed = 0
            var minGreen = 0
            var minBlue = 0
            for (result in results) {
                if (minRed < result.red) {
                    minRed = result.red
                }
                if (minGreen < result.green) {
                    minGreen = result.green
                }
                if (minBlue < result.blue) {
                    minBlue = result.blue
                }
            }

            // add the power of the min cubes to the sum
            sum += minRed * minGreen * minBlue
        }
        return sum
    }

    val testInput = readInput("Day02/test")
    check(part1(testInput) == 8)
    check(part2(testInput) == 2286)

    val input = readInput("Day02/input")
    println("Part 1: ${part1(input)}")
    println("Part 2: ${part2(input)}")
}
