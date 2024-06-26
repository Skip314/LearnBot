package org.example


fun main() {

    val trainer = LearnWordsTrainer()

    trainer.loadDictionary()

    while (true) {

        println("Меню: \n1 – Учить слова \n2 – Статистика \n0 – Выход")
        when (readln()) {

            "0" -> return
            "1" -> learnWords(trainer)
            "2" -> {
                val statistic = trainer.getStatistics()
                println("выучено ${statistic.learned} слов из ${statistic.quantityWords} | ${statistic.part}%")
            }

            else -> println("Выберите из списка:")
        }
        println()
    }
}

fun learnWords(trainer: LearnWordsTrainer) {

    while (true) {

        val question = trainer.getQuestion()
        if (question == null) {
            println("Все слова выучены")
            return
        }

        println()
        println("Выберите верный перевод ${question.correctAnswer.original}")
        question.variance.forEachIndexed { index, words -> println("${index + 1} - ${words.translate}") }
        println("0 - выход в главное меню")

        val answer = readln().toIntOrNull() ?: -1
        if (answer == 0) return
        trainer.checkAndRecordAnswer(answer, question)
    }
}