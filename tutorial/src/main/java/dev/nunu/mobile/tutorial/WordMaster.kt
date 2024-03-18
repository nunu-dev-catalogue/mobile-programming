package dev.nunu.mobile.tutorial

import java.io.FileInputStream

// Data Class
data class Word(
    val word: String,
    val meaning: String
) {
    fun print() {
        println("$word : $meaning")
    }
}

// Converter
fun interface WordsConverter {
    operator fun invoke(words: List<String>): List<Word>
}

class DefaultWordsConverter : WordsConverter {
    override fun invoke(words: List<String>): List<Word> {
        return words.chunked(2) { (word, meaning) -> Word(word, meaning) }
    }
}

// Parse Logic
fun interface WordsFileParserUseCase {
    operator fun invoke(fileName: String): Result<List<Word>>
}

class DefaultWordsFileParserUseCase(
    private val converter: WordsConverter
) : WordsFileParserUseCase {
    override operator fun invoke(fileName: String): Result<List<Word>> = runCatching {
        converter(
            FileInputStream(fileName)
                .bufferedReader()
                .readLines()
        )
    }
}

// View Logic
fun showChoiceMenu() {
    println("1) 영어 단어 검색 2) 부분 영단어 검색(입력값으로 시작하는)  3) 뜻 검색 4) 종료")
    print("메뉴를 선택하세요: ")
}

fun main() {
    println("201811218 이현우")
    val wordsConverter = DefaultWordsConverter()
    val defaultWordsFileParserUseCase = DefaultWordsFileParserUseCase(wordsConverter)
    defaultWordsFileParserUseCase("words2.txt")
        .onSuccess { words ->
            while (true) {
                showChoiceMenu()
                val choice = readln().toInt()
                when (choice) {
                    1 -> {
                        print("검색할 단어를 입력하세요: ")
                        val searchWord = readln()
                        words.find { it.word == searchWord }?.print()
                            ?: println("찾는 단어가 존재하지 않습니다.")
                    }

                    2 -> {
                        print("찾을 영단어의 일부를 입력하세요: ")
                        val searchWord = readln()
                        words.filter { it.word.startsWith(searchWord) }
                            .forEach { it.print() }
                    }

                    3 -> {
                        print("찾을 뜻을 입력하세요 (뜻의 일부만 포함): ")
                        val searchMeaning = readln()
                        words.filter { it.meaning.contains(searchMeaning) }
                            .forEach { it.print() }
                    }

                    4 -> break
                }
            }
        }
        .onFailure {
            println("파일 위치를 확인하세요.")
        }
}
