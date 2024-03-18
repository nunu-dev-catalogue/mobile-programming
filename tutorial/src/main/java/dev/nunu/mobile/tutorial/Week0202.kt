package dev.nunu.mobile.tutorial

fun main() {
    println("201811218 이현우")
//    (1..9)
//        .filter { it > 5 }
//        .toList()
//        .shuffled()
//        .sorted()
//        .forEach(::println)
    // 실습 1
    val names = arrayOf(
        "이현우",
        "김현우",
        "박현우",
        "차현우"
    )
    while (true) {
        println("1) 오름차순 2) 내림차순 3) 차씨 찾기 4) 종료")
        val choice = readln().toInt();

        when (choice) {
            1 -> names.sorted().forEach(::println)
            2 -> names.sortedDescending().forEach(::println)
            3 -> names.filter { it.startsWith("차") }.forEach(::println)
            4 -> break
        }
    }
}


