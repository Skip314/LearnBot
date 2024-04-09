package org.example

import java.io.File

data class Words(
    val original: String,
    val translate: String,
    val quantityApprove: Int,
)

fun main() {

    val wordsFile: File = File("words.txt")
    wordsFile.writeText("")
    createStartWords()

    val lines: List<String> = wordsFile.readLines()
    wordsFile.writeText("")

    val dictionaryWords: MutableMap<String, String> = mutableMapOf()

    for (i in lines) {
        val line = i.split("|")
        val word = Words(original = line[0], translate = line[1], quantityApprove = line[2].toInt())
        dictionaryWords[word.original] = word.translate
        wordsFile.appendText("${word.original}|${word.translate}|${word.quantityApprove}\n")
    }
    println(dictionaryWords)
}
fun createStartWords(){
    val wordsFile: File = File("words.txt")
    wordsFile.appendText("hello|привет|0\n")
    wordsFile.appendText("dog|собака|0\n")
    wordsFile.appendText("cat|кошка|0\n")
}