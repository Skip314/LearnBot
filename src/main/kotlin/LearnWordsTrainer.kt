package org.example

import java.io.File

data class Words(val original: String, val translate: String, var quantityApprove: Int)
data class Statistic(val learned: Int, val part: Int, val quantityWords: Int)

data class AnswerOptions(var variance: List<Words>) {
    val correctAnswer: Words = variance.random()
}

class LearnWordsTrainer(
    val approvedLearnWords: Int = 3,
    val quantityWords: Int = 4,
) {

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

    fun getStatistics(): Statistic {

        val learned = dictionaryWords.filter { it.quantityApprove >= approvedLearnWords }.size
        val part = 100 * learned / dictionaryWords.size

        return Statistic(learned, part, dictionaryWords.size)
    }

    fun getQuestion(): AnswerOptions? {

        val learnWords =
            dictionaryWords.filter { it.quantityApprove < approvedLearnWords }.toMutableList().take(
                quantityWords
            )
        if (learnWords.isEmpty()) {
            return null
        }

        val question = AnswerOptions(learnWords)

        if (question.variance.size < quantityWords) {
            val learnedWords =
                dictionaryWords.filter { it.quantityApprove >= approvedLearnWords }
                    .take(quantityWords - question.variance.size)
            question.variance = (question.variance + learnedWords).shuffled().toMutableList()
            return question
        }
        return question
    }
}


