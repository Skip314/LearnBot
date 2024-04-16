package org.example

import java.io.File

data class Words(
    val original: String,
    val translate: String,
    val quantityApprove: Int,
)

fun main() {

    val wordsFile = File("words.txt")
    if (wordsFile.exists()) createStartWords()

    val lines: List<String> = wordsFile.readLines()

    val dictionaryWords: MutableList<Words> = mutableListOf()

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
}

fun createStartWords() {
    val wordsFile = File("words.txt")
    wordsFile.appendText("hello|привет|0\n")
    wordsFile.appendText("dog|собака|0\n")
    wordsFile.appendText("cat|кошка|0\n")
}