package org.example

import java.io.File

val trainer = LearnWordsTrainer()

fun main() {

    trainer.loadDictionary()

    while (true) {

        println("Меню: \n1 – Учить слова \n2 – Статистика \n0 – Выход")
        when (readln()) {

            "0" -> return
            "1" -> learnWords()
            "2" -> trainer.getStatistics()
            else -> println("Выберите из списка:")
        }
        println()
    }
}

fun learnWords() {

    while (true) {

        val learnWords =
            trainer.dictionaryWords.filter { it.quantityApprove < APPROVED_LEARN_WORDS }.toMutableList()
        if (learnWords.isEmpty()) {
            println("Все слова выучены")
            return
        }

        val question = AnswerOptions(learnWords)

        if (question.variance.size < QUANTITY_WORDS) {
            val learnedWords = trainer.dictionaryWords.filter { it.quantityApprove >= APPROVED_LEARN_WORDS }
                .take(QUANTITY_WORDS - question.variance.size)
            question.variance = (question.variance + learnedWords).shuffled().toMutableList()
        }

        println()
        println("0 - выход в главное меню")
        println("Выберите верный перевод ${question.correctAnswer.original}")
        question.variance.forEachIndexed { index, words -> println("${index + 1} - ${words.translate}") }

        val answer = readln().toIntOrNull() ?: -1

        if (answer == 0) return
        if (question.variance.indexOf(question.correctAnswer) == answer - 1) {
            println("Верно")
            question.correctAnswer.quantityApprove += 1
            trainer.saveDictionary()
        }
    }
}
