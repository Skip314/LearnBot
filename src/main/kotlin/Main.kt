package org.example

import java.io.File

data class Words(
    val original: String,
    val translate: String,
    val quantityApprove: Int,
)

fun main() {

    val wordsFile = File("words.txt")
    wordsFile.writeText("")
    if (wordsFile.exists()) createStartWords()

    val lines: List<String> = wordsFile.readLines()
    wordsFile.writeText("")

    val dictionaryWords: MutableList<Words> = mutableListOf()

    for (i in lines) {
        val line = i.split("|")
        val word =
            Words(original = line[0], translate = line[1], quantityApprove = line.getOrNull(2)?.toIntOrNull() ?: 0)
        dictionaryWords.add(word)
        wordsFile.appendText("${word.original}|${word.translate}|${word.quantityApprove}\n")
    }
}

fun createStartWords() {
    val wordsFile = File("words.txt")
    wordsFile.appendText("hello|привет|0\n")
    wordsFile.appendText("dog|собака|0\n")
    wordsFile.appendText("cat|кошка|0\n")
}