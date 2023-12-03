package Day03

import readInput

fun main() {
    class PartNumber(val number: Int, val line:Int, val startIndex: Int, val endIndex: Int) {

    }

    fun part1(input: List<String>): Int {
        var sum = 0
        input.forEachIndexed { lineIndex, line ->
            // Make a list of part numbers from this line
            val partNumbers: List<PartNumber> = buildList<PartNumber> {
                var buildingPartNumber = false
                var startIndex = 0
               for (i in line.indices) {
                   if (line[i].isDigit()) {
                       if (!buildingPartNumber) {
                           buildingPartNumber = true
                           startIndex = i
                       }
                       if (i == line.length-1) {
                           buildingPartNumber = false
                           val num = line.substring(startIndex).toInt()
                           val newPart = PartNumber(num, lineIndex, startIndex, i)
                           add(newPart)
                       }
                   } else {
                       if (i>0 && buildingPartNumber) {
                           buildingPartNumber = false
                           val num = line.substring(startIndex,i).toInt()
                           val newPart = PartNumber(num, lineIndex, startIndex, i-1)
                           add(newPart)
                       }
                   }
               }
            }

            // Find symbols around the part number
            for (partNumber in partNumbers) {
                // Search for symbols in the above line between startIndex-1 and endIndex+1
                var foundPart = false
                val startIndex = if (partNumber.startIndex - 1 >= 0) partNumber.startIndex - 1 else 0
                val endIndex = (partNumber.endIndex + 1).coerceAtMost(line.length - 1)
                val previousLine = input.getOrNull(lineIndex-1)
                if (previousLine != null) {
                    val search = previousLine.substring(startIndex..endIndex)
                    foundPart = search.count { !it.isDigit() && it != '.' } > 0
                }
                // Search for symbols in this line at startIndex-1 and endIndex+1
                if (!foundPart) {
                    foundPart = line.substring(startIndex..endIndex).count { !it.isDigit() && it!= '.' } > 0
                }
                // Search for symbols in the next line between startIndex-1 and endIndex+1
                if (!foundPart) {
                    val nextLine = input.getOrNull(lineIndex+1)
                    if (nextLine != null) {
                        val search = nextLine.substring(startIndex..endIndex)
                        foundPart = search.count { !it.isDigit() && it != '.' } > 0
                    }
                }
                // If any results were found, add the part number to the sum
                if (foundPart) {
                    sum += partNumber.number
                }
            }
        }
        return sum
    }

    fun part2(input: List<String>): Int {
        var sum = 0
        val partNumbers: MutableList<PartNumber> = mutableListOf()
        input.forEachIndexed { lineIndex, line ->
            // Make a list of part numbers from this line
            var buildingPartNumber = false
            var startIndex = 0
            for (i in line.indices) {
                if (line[i].isDigit()) {
                    if (!buildingPartNumber) {
                        buildingPartNumber = true
                        startIndex = i
                    }
                    if (i == line.length - 1) {
                        buildingPartNumber = false
                        val num = line.substring(startIndex).toInt()
                        val newPart = PartNumber(num, lineIndex, startIndex, i)
                        partNumbers.add(newPart)
                    }
                } else {
                    if (i > 0 && buildingPartNumber) {
                        buildingPartNumber = false
                        val num = line.substring(startIndex, i).toInt()
                        val newPart = PartNumber(num, lineIndex, startIndex, i - 1)
                        partNumbers.add(newPart)
                    }
                }
            }

        }

        // Find all * with 2 part numbers around it
        input.forEachIndexed { lineIndex, line ->
            var ai = line.indexOf("*")
            while (ai != -1) {
                val parts = buildList {
                    for (part in partNumbers.filter { it.line >= lineIndex - 1 && it.line <= lineIndex+1}) {
                        if (
                                (part.startIndex >= ai - 1 && part.startIndex <= ai + 1)
                                || (part.endIndex >= ai - 1 && part.endIndex <= ai + 1)
                        ) {
                            add(part.number)
                        }
                    }
                }
                if (parts.size == 2) {
                    sum += parts.first() * parts.last()
                }
                // now back to looking for gears
                ai = line.indexOf("*", ai + 1)
            }
        }

        return sum
    }

    val testInput = readInput("Day03/test")
    check(part1(testInput) == 4361)
    println("test part 1 passed")
    check(part2(testInput) == 467835)
    println("test part 2 passed")

    val input = readInput("Day03/input")
    println("Part 1: ${part1(input)}")
    println("Part 2: ${part2(input)}")
}
