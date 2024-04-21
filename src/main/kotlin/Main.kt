package org.example

import java.io.File

val dictionaryWords: MutableList<Words> = mutableListOf()

const val APPROVED_LEARN_WORDS = 3
const val QUANTITY_WORDS = 4

data class Words(
    val original: String,
    val translate: String,
    val quantityApprove: Int,
)

fun main() {

    val wordsFile = File("words.txt")
    if (!wordsFile.exists()) createStartWords()

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
    println(dictionaryWords)

    while (true) {

        println("Меню: \n1 – Учить слова, \n2 – Статистика, \n0 – Выход")
        when (readln().toInt()) {

            0 -> return
            1 -> learnWords()
            2 -> getStatistic()
            else -> println("Выберите из списка:")
        }
        println()
    }
}

fun createStartWords() {
    val wordsFile = File("words.txt")
    wordsFile.appendText("hello|привет|0\n")
    wordsFile.appendText("dog|собака|0\n")
    wordsFile.appendText("cat|кошка|0\n")
    wordsFile.appendText("i|я|0\n")
}

fun getStatistic() {

    val approved = dictionaryWords.filter { it.quantityApprove >= APPROVED_LEARN_WORDS }.size
    val part = 100 * approved / dictionaryWords.size

    println("Выучено $approved из ${dictionaryWords.size} слов | ${part}%")
}

fun learnWords() {

    while (true) {
        val unlearnedWords = dictionaryWords.filter { it.quantityApprove <= APPROVED_LEARN_WORDS }

        if (unlearnedWords.isEmpty()) {
            println("Все слова выучены")
            return
        }

        var learnWords = unlearnedWords.toMutableList().shuffled().take(QUANTITY_WORDS)


        if (learnWords.size < QUANTITY_WORDS) {
            val learnedWords = dictionaryWords.filter { it.quantityApprove > APPROVED_LEARN_WORDS }
                .take(QUANTITY_WORDS - learnWords.size)
            learnWords = (learnWords + learnedWords).shuffled()
        }

        val approve = learnWords.random().original

        println("Выберите верный перевод $approve")
        var num = 1
        learnWords.forEach { words ->
            println("$num - ${words.translate}")
            num++
        }

        val answer = readln()
    }
}
