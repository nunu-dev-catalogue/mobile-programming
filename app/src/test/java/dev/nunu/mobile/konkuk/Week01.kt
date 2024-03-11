package dev.nunu.mobile.konkuk

import java.time.LocalDate
import java.util.*

fun main() {
    print("Enter a number(x): ")
    val x = readln()
    print("Enter a number(y): ")
    val y = readln()
    val xInt = x.toInt()
    val yInt = y.toInt()
//    dev.nunu.mobile.konkuk.ifExample()
    ifExample(xInt, yInt)
//    dev.nunu.mobile.konkuk.whenExample1()
    whenExample1(xInt, yInt)
//    dev.nunu.mobile.konkuk.whenExample2()
    whenExample2(xInt)
//    dev.nunu.mobile.konkuk.forExample1()

//    dev.nunu.mobile.konkuk.forExample2()

}

fun forExample2() {
    val numbers = arrayOf(1, 2, 3, 4, 5)
    for (num in numbers) {
        print("$num\t")
    }
    println()

    for (index in numbers.indices) {
        println("number at $index is ${numbers[index]}")
    }
}

fun forExample1() {

    fun isEven(num: Int): String = when (num % 2) {
        0 -> "짝"
        else -> "홀"
    }

    println(isEven(100))
}

fun whenExample2(x: Int) {
    when (x) {
        1 -> println("x==1")
        2, 3 -> println("x==2 or x==3")
        in 4..7 -> println("4부터 7사이")
        !in 8..10 -> println("8부터 10사이가 아님")
        else -> {
            print("x는 8에서 10사이의 숫자임")
        }
    }
}

fun whenExample1(a: Int, b: Int) {
    print("Enter operator either +, -, * or /")
    val result = when (val op = readln()) {
        "+" -> a + b
        "-" -> a - b
        "*" -> a * b
        "/" -> a / b
        else -> "$op operator is invalid operator."
    }
    println(result)
}

fun ifExample(a: Int, b: Int) {
    val max = if (a > b) {
        println("$a is larger than $b.")
        println("max variable holds value of a.")
        a
    } else {
        println("$b is larger than $a.")
        println("max variable holds value of b.")
        b
    }
    println("max value : $max")
}

fun decimalDigitValue(c: Char): Int {
    if (c !in '0'..'9')
        throw IllegalArgumentException("Out of range")
    return c.code - '0'.code
}

fun testString() {
    val s1 = "홍길동"
    val s2 = "5".toInt()
    val s3 = "123.5".toDouble()
    println("이름: $s1 \n번호 : ${s2}번 \n응모가 : ${s3 - 10}원")
}

fun testOperator() {
    val start = LocalDate.now()
    println(start)
    val end = start..start.plusDays(15)
    println(start.plusWeeks(1) in end)
    println(end)
}

fun scannerExample() {
    val reader = Scanner(System.`in`)
    print("Enter a number: ")
    val integer: Int = reader.nextInt()
    println("You entered: $integer")
}



