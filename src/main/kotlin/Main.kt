package org.example

import java.io.File

val dictionaryWords: MutableList<Words> = mutableListOf()

const val APPROVED_LEARN_WORDS = 3

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
            1 -> ""
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
}

fun getStatistic() {

    val approved = dictionaryWords.filter{it.quantityApprove >= APPROVED_LEARN_WORDS}.size
    val part = 100 * approved / dictionaryWords.size

    println("Выучено $approved из ${dictionaryWords.size} слов | ${part}%")
}
