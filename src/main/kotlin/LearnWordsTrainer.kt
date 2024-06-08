package org.example

import java.io.File

const val APPROVED_LEARN_WORDS = 3
const val QUANTITY_WORDS = 4

data class Words(val original: String, val translate: String, var quantityApprove: Int)

data class AnswerOptions(var variance: MutableList<Words>) {
    val correctAnswer: Words = variance.random()
}

class LearnWordsTrainer {

    val dictionaryWords: MutableList<Words> = mutableListOf()

    fun loadDictionary() {

        val wordsFile = File("words.txt")

        val lines: List<String> = wordsFile.readLines()

        for (i in lines) {
            val line = i.split("|")
            val word =
                Words(
                    original = line[0],
                    translate = line[1],
                    quantityApprove = line.getOrNull(2)?.toIntOrNull() ?: 0
                )
            dictionaryWords.add(word)
        }
    }

    fun saveDictionary() {

        val wordsFile = File("words.txt")
        wordsFile.writeText("")
        for (word in dictionaryWords) {
            wordsFile.appendText("${word.original}|${word.translate}|${word.quantityApprove}\n")
        }
    }

    fun getStatistics() {

        val learned = dictionaryWords.filter { it.quantityApprove >= APPROVED_LEARN_WORDS }.size
        val part = 100 * learned / dictionaryWords.size

        println("выучено ${learned} слов из ${dictionaryWords.size} | ${part}%")
    }
}