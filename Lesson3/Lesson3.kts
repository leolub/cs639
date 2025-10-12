// Lesson 3.1 – Using Classes and Objects in Kotlin

//2. Create a class
//Step 1: Create a package
//package example.myapp

//Step 2: Create a class with properties
//class Aquarium {
//    var width: Int = 20
//    var height: Int = 40
//    var length: Int = 100
//}

//Step 3: Create a main() function
//fun buildAquarium() {
//    val myAquarium = Aquarium()
//}
//fun main() {
//    buildAquarium()
//}

//Step 4: Add a method
//fun printSize() {
//    println("Width: $width cm " +
//            "Length: $length cm " +
//            "Height: $height cm ")
//}
//fun buildAquarium() {
//    val myAquarium = Aquarium()
//    myAquarium.printSize()
//} //Width: 20 cm Length: 100 cm Height: 40 cm
//fun buildAquarium() {
//    val myAquarium = Aquarium()
//    myAquarium.printSize()
//    myAquarium.height = 60
//    myAquarium.printSize()
//}
//Width: 20 cm Length: 100 cm Height: 40 cm
//Width: 20 cm Length: 100 cm Height: 60 cm

//3. Add class constructors
//Step 1: Create a constructor
class Aquarium(length: Int = 100, width: Int = 20, height: Int = 40) {
    // Dimensions in cm
    var length: Int = length
    var width: Int = width
    var height: Int = height

    fun printSize() {
        println("Width: $width cm " +
                "Length: $length cm " +
                "Height: $height cm ")
        println("Volume: $volume liters")
    }

    //Step 2: Add init blocks
    init {
        println("aquarium initializing")
    }

    //Step 3: Learn about secondary constructors
    constructor(numberOfFish: Int) : this() {
        val tank = numberOfFish * 2000 * 1.1
        height = (tank / (length * width)).toInt()
    }

    //Step 4: Add a new property getter
    var volume: Int
        get() = width * height * length / 1000

        //Step 5: Add a property setter
        set(value) {
            height = (value * 1000) / (width * length)
        }
}

//Step 6: Build and print (defaults and modified height)
fun buildAquarium_v1() {
    val myAquarium = Aquarium()
    myAquarium.printSize()
    myAquarium.height = 60
    myAquarium.printSize()
}
//Width: 20 cm Length: 100 cm Height: 40 cm
//Width: 20 cm Length: 100 cm Height: 60 cm

//Step 7: Build with different constructors
fun buildAquarium_v2() {
    val aquarium1 = Aquarium()
    aquarium1.printSize()

    val aquarium2 = Aquarium(width = 25)
    aquarium2.printSize()

    val aquarium3 = Aquarium(height = 35, length = 110)
    aquarium3.printSize()

    val aquarium4 = Aquarium(width = 25, height = 35, length = 110)
    aquarium4.printSize()
}
//Width: 20 cm Length: 100 cm Height: 40 cm
//Width: 25 cm Length: 100 cm Height: 40 cm
//Width: 20 cm Length: 110 cm Height: 35 cm
//Width: 25 cm Length: 110 cm Height: 35 cm

//Step 8: Secondary constructor by fish count
fun buildAquarium_v3() {
    val aquarium6 = Aquarium(numberOfFish = 29)
    aquarium6.printSize()
}
//...

//Step 9: Setter to change volume
fun buildAquarium_v4() {
    val aquarium = Aquarium(numberOfFish = 29)
    aquarium.printSize()
    aquarium.volume = 70
    aquarium.printSize()
}
//...

//Step 10: Data class
//class Decoration(val rocks: String)
//Step 10 (replace with data class)
data class Decoration(val rocks: String)

fun decorationDemo() {
    val d1 = Decoration("granite")
    val d2 = Decoration("slate")
    val d3 = Decoration("slate")
    println(d1)
    println(d2)
    println(d3)
    println(d1.equals(d2))
    println(d3.equals(d2))
}

//Run
buildAquarium_v1()
println()
buildAquarium_v2()
println()
buildAquarium_v3()
println()
buildAquarium_v4()
println()
decorationDemo()


// 3.2 Pairs, Triples, Collections, Constants, Extensions

//Step 1: Constants
//val MAX_BORROW = 3
const val MAX_BORROW = 3
//val APP_VERSION = "1.0.0"
val APP_VERSION = "1.0.0"

//Step 2: Pair and Triple
fun pairsTriplesDemo() {
    //val score = Pair("Siming", 95)
    val score = "Siming" to 95
    //val name = score.first; val sc = score.second
    val (name, sc) = score
    println("$name -> $sc")

    //val product = Triple("SSD", 89.9, 2)
    val product = Triple("SSD", 89.9, 2)
    //val pname = product.first; val price = product.second; val qty = product.third
    val (pname, price, qty) = product
    println("$pname total = ${price * qty}")
}

//Step 3: Read-only and mutable collections
//val titles = mutableListOf("Kotlin 101", "Compose Basics")
val titles = listOf("Kotlin 101", "Compose Basics", "Data Structures")
val books = mutableListOf(
    Aquarium(length = 100, width = 20, height = 40),
    Aquarium(length = 110, width = 20, height = 35),
    Aquarium(length = 90,  width = 25, height = 50)
)

fun collectionsDemo() {
    println(titles)
    println(books.size)
    //books = listOf() //不能重新赋值不同类型
    books += Aquarium(length = 120, width = 25, height = 35)
    println(books.size)

    //val longOnes = books.map { it.length }
    val longOnes = books.filter { it.length >= 100 }.map { it.length }
    println(longOnes)

    //val total = 0
    //for (b in books) total += b.height
    val total = books.sumOf { it.height }
    println(total)

    //val byH = mapOf<Int, List<Aquarium>>()
    val byH = books.groupBy { it.height }
    println(byH.keys)
}

//Step 4: Map
//val m = mapOf("A" to 1, "B" to 2)
//val mm = mutableMapOf("A" to 1)
val m = mapOf("A" to 1, "B" to 2)
val mm = mutableMapOf("A" to 1)
fun mapDemo() {
    println(m["A"])
    mm["B"] = 2
    println(mm)
}

//Step 5: Extension functions (String)
////fun titleCase(s: String): String = ...
//fun String.titleCase(): String = this
fun String.titleCase(): String =
    trim().split(Regex("\\s+"))
        .joinToString(" ") { it.lowercase().replaceFirstChar { c -> c.titlecase() } }

fun extensionsStringDemo() {
    println("hello kotlin from codelab".titleCase())
}

//Step 6: Extension functions (List)
////fun totalPages(list: List<Aquarium>): Int = ...
//fun List<Aquarium>.totalHeight(): Int = 0
fun List<Aquarium>.totalHeight(): Int = sumOf { it.height }

fun extensionsListDemo() {
    println(books.totalHeight())
}

//Step 7: Nullable and safe extension
////fun safeLength(s: String?): Int = ...
//fun String?.safeLength(): Int = 0
fun String?.safeLength(): Int = this?.length ?: 0

fun extensionsNullableDemo() {
    val a: String? = null
    val b: String? = "Compose"
    println(a.safeLength())
    println(b.safeLength())
}

//Step 8: Higher-order with collections
//val shortOnes = books.filter { it.height < 40 }
val shortOnes = books.filter { it.height < 40 }.map { it.length }
fun hofDemo() {
    println(shortOnes)
}

//Run 3.2
println("\n-- 3.2 START --")
println(APP_VERSION)
println(MAX_BORROW)
pairsTriplesDemo()
collectionsDemo()
mapDemo()
extensionsStringDemo()
extensionsListDemo()
extensionsNullableDemo()
hofDemo()
println("-- 3.2 END --\n")
