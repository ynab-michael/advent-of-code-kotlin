package Day01

import readInput

fun main() {
    class Word(private val letters: String, val int: Int) {
        private var found = ""

        fun testChar(c: Char) {
            val indexToTest = found.length
            if (letters[indexToTest] == c) {
                found += c
            } else if (indexToTest > 0) {
                if (letters[0] == c) {
                    found = "" + c
                } else {
                    clear()
                }
            }
        }

        fun isComplete(): Boolean {
            return found == letters
        }

        fun clear() {
            found = ""
        }
    }

    class WordFinder {
        private val words: Array<Word> = arrayOf(
                Word("one", 1),
                Word("two", 2),
                Word("three",3),
                Word("four",4),
                Word("five",5),
                Word("six",6),
                Word("seven",7),
                Word("eight",8),
                Word("nine",9),
        )

        fun processLetter(c: Char) {
            for (word in words) {
                word.testChar(c)
            }
        }

        fun clear() {
            for (word in words) {
                word.clear()
            }
        }

        fun hasCompletedWord(): Boolean {
            return words.count { it.isComplete() } > 0
        }

        fun flushCompletedWord(): Int {
            val word = words.first { it.isComplete() }
            word.clear()
            return word.int
        }
    }

    fun part1(input: List<String>): Int {
        var sum = 0
        for (line in input) {
            // Identify first int in string
            var first: Int? = null
            var last = 0
            for (c in line) {
                if (c.isDigit()) {
                    last = c.digitToInt()
                    if (first == null) {
                        first = c.digitToInt()
                    }
                }
            }

            val concatenated = (first.toString().plus(last.toString())).toInt()
            sum += concatenated
        }
        return sum
    }

    fun part2(input: List<String>): Int {
        var sum = 0
        for (line in input) {
            val finder = WordFinder()
            // Identify first int in string
            var first: Int? = null
            var last = 0
            for (c in line) {
                var num: Int? = null
                if (c.isDigit()) {
                    num = c.digitToInt()
                    // character was not a letter, so interrupt word matching
                    finder.clear()
                } else {
                    finder.processLetter(c)
                    if (finder.hasCompletedWord()) {
                        num = finder.flushCompletedWord()
                    }
                }
                if (num != null) {
                    last = num
                    if (first == null) {
                        first = num
                    }
                }
            }

            val concatenated = (first.toString().plus(last.toString())).toInt()
            sum += concatenated
        }
        return sum
    }

    val test1Input = readInput("Day01/part1_test")
    check(part1(test1Input) == 142)
    val test2Input = readInput("Day01/part2_test")
    check(part2(test2Input) == 281)

    val input = readInput("Day01/input")
    println("Part 1: ${part1(input)}")
    println("Part 2: ${part2(input)}")
}
