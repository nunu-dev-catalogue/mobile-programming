package dev.nunu.mobile.konkuk

fun main() {
    // dev.nunu.mobile.konkuk.personClassTest()
    // dev.nunu.mobile.konkuk.bankAccountClassTest()
    //dev.nunu.mobile.konkuk.runnableClassTest()
    //dev.nunu.mobile.konkuk.nestedClassTest()
    //dev.nunu.mobile.konkuk.innerClassTest()
    //dev.nunu.mobile.konkuk.companionObjectTest()
    //dev.nunu.mobile.konkuk.objectClassTest()
    //dev.nunu.mobile.konkuk.objectPersonClassTest()
    //dev.nunu.mobile.konkuk.dataClassTest()
    //dev.nunu.mobile.konkuk.sealedClassTest()
    //dev.nunu.mobile.konkuk.listTest()
    //dev.nunu.mobile.konkuk.mapTest()
    //dev.nunu.mobile.konkuk.setTest()
}

fun setTest() {
    val citySet = setOf("서울", "수원", "부산")
    println(citySet.size)
    println(citySet.contains("서울"))
    println()

    val citySet2 = mutableSetOf("서울", "수원", "부산")
    citySet2.add("안양")
    citySet2.add("안양")
    citySet2.add("수원")
    println(citySet2)
    println(citySet2.intersect(citySet))
    println()

    val citySet3 = HashSet<String>()
    citySet3.add("서울")
    citySet3.add("서울")
    citySet3.add("수원")
    citySet3.add("부산")
    citySet3.add("안양")
    println(citySet3)
}

fun mapTest() {
    val map = mapOf("a" to 1, "b" to 2, "c" to 3)
    val value: Int = map.getValue("a")
    println(value)

    for ((k, v) in map) {
        println("$k -> $v")
    }
    println()

    val citiesMap = mutableMapOf("한국" to "서울", "일본" to "동경", "중국" to "북경")
    citiesMap["한국"] = "서울특별시" // 덮어쓰기
    citiesMap["미국"] = "워싱턴"     // 추가
    for ((k, v) in citiesMap) {
        println("$k -> $v")
    }
    println()

    val hashMap = HashMap<String, String>()
    hashMap["item1"] = "greenjoa1"
    hashMap["item2"] = "greenjoa2"
    hashMap["item3"] = "greenjoa3"

    for ((k, v) in hashMap) {
        println("$k -> $v")
    }
}

fun listTest() {
    val foods: List<String> = listOf("라면", "갈비", "밥")
    val foods2 = listOf("라면", "갈비", "밥")
    for (food in foods2) {
        println(food)
    }
    println()

    val foods3: MutableList<String> = mutableListOf("라면", "갈비", "밥")
    val foods4 = mutableListOf("라면", "갈비", "밥")
    foods4.add("초밥")
    foods4.removeAt(0)
    foods4[1] = "부대찌개"
    foods4.set(1, "김치찌개")
    for (food in foods4) {
        println(food)
    }
    println()

    val list = ArrayList<String>()
    list.add("greenjoa1")
    list.add("greenjoa2")
    list.add("greenjoa3")
    for (greenjoa in list) {
        println(greenjoa)
    }

}

fun sealedClassTest() {
    val obj1 = Expr.Const(10.2)
    val obj2 = Expr.Sum(Expr.Const(10.0), Expr.Const(20.0))
    println(eval(obj1))
    println(eval(obj2))
    println(eval(Expr.NotANumber))
}

sealed class Expr {
    class Const(val value: Double) : Expr()
    class Sum(val left: Expr, val right: Expr) : Expr()
    data object NotANumber : Expr()
}

fun eval(e: Expr): Double = when (e) {
    is Expr.Const -> e.value
    is Expr.Sum -> eval(e.right) + eval(e.left)
    Expr.NotANumber -> Double.NaN
    else -> throw IllegalArgumentException("Unknown expression")
}

fun dataClassTest() {
    val jack = User("jack", 29)
    println("name = ${jack.name}")
    println("age = ${jack.age}")

    val u1 = User("John", 29)
    val u2 = u1.copy(name = "Randy")
    println("u1: name = ${u1.name}, name = ${u1.age}")
    println("u2: name = ${u2.name}, name = ${u2.age}")

    val u3 = User("John", 29)
    val (name, age) = u3
    println("name = $name")
    println("age = $age")

}

data class User(val name: String, val age: Int)

fun objectPersonClassTest() {
    val atheist = object : ObjectPerson("greenjoa", 23) {
        override fun pray() = println("I don't pray. I am an atheist.")
    }
    atheist.eat()
    atheist.talk()
    atheist.pray()
}

open class ObjectPerson(name: String, age: Int) {
    init {
        println("name: $name, age: $age")
    }

    fun eat() = println("Eating food.")
    fun talk() = println("Talking with people.")
    open fun pray() = println("Praying god.")
}


fun objectClassTest() {
    val result = Test.makeMe12()
    println("b = ${Test.b}")
    println("result = $result")
}

object Test {
    private var a: Int = 0
    var b: Int = 1
    fun makeMe12(): Int {
        a += 12
        return a
    }
}

fun companionObjectTest() {
    PersonClass.callMe()
}

class PersonClass {
    companion object {
        fun callMe() = println("I'm called")
    }
}


fun nestedClassTest() {
    val a = OuterClass.NestedClass()
    a.something1()
    val b = OuterClass.NestedClass().something2()
    println(b)
}

fun innerClassTest() {
    val a = OuterClass().InnerClass()
    a.something1()
    val b = OuterClass().InnerClass().something2()
    println(b)
    val c = a.getOuterReferences()
    println(c.num1)
}

class OuterClass {
    var num1 = 10

    class NestedClass {
        var num2 = 20
        fun something1() = println(num2)

        fun something2() = 20
    }

    inner class InnerClass {
        fun something1() {
            num1 = 20
            println(num1)
        }

        fun something2() = 20
        fun getOuterReferences(): OuterClass = this@OuterClass
    }
}


fun runnableClassTest() {
    val test = RunnableClass()
    test.fastRun()
    test.run()
    test.type = 50
    println(test.type)
}

interface Runnable {
    var type: Int
    fun run()
    fun fastRun() = println("빨리 달린다")
}

class RunnableClass : Runnable {
    override var type: Int = 0

    override fun run() {
        println("달린다")
    }

    override fun fastRun() {
        println("더 빨리 달린다")
    }
}


fun bankAccountClassTest() {
    val account = BankAccount(10, 1000.0)
    account.balanceLessFees = 1000.0
    println(account.balanceLessFees)
}

class BankAccount(val accountNumber: Int, var accountBalance: Double) {
    val fees: Double = 25.00
    var balanceLessFees: Double
        get() {
            return accountBalance - fees
        }
        set(value) {
            accountBalance += (value - fees)
        }
}

fun personClassTest() {
    val person = Person("greenjoa", "Seoul")
    person.printName()
    person.printAddr()
}

class Person(val name: String) {
    var addr: String? = null

    constructor(name: String, addr: String) : this(name) {
        this.addr = addr
    }

    fun printName() {
        println(name)
    }

    fun printAddr() {
        println(addr)
    }
}
