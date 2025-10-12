import java.util.*

//1. Explore the main() function
//fun printHello() {
//    println ("Hello World")
//}
//printHello()
////Step 1: Create a Kotlin file
////Step 2: Add code and run your program
//fun main(args: Array<String>) {
//    println("Hello, world!")
//}
//Step 3: Pass arguments to main()
//Step 4: Change the code to use a string template
//fun main(args: Array<String>) {
//    println("Hello, ${args[0]}")
//}// // Hello, Kotlin!

//2. Learn why (almost) everything has a value
// Will assign kotlin.Unit
val isUnit = println("This is an expression")//This is an expression
println(isUnit)//kotlin.Unit

//val temperature = 10
//val isHot = if (temperature > 50) true else false
//println(isHot)//false

val temperature = 10
val message = "The water temperature is ${ if (temperature > 50) "too warm" else "OK" }."
println(message)//The water temperature is OK.

//3. Learn more about functions
//Step 1: Create some functions

//fun randomDay() : String {
//    val week = arrayOf ("Monday", "Tuesday", "Wednesday", "Thursday",
//        "Friday", "Saturday", "Sunday")
//    return week[Random().nextInt(week.size)]
//}
//fun feedTheFish() {
//    val day = randomDay()
//    val food = "pellets"
//    println ("Today is $day and the fish eat $food")
//}
//feedTheFish()//Today is Wednesday and the fish eat pellets

//Step 2: Use a when expression(final version)
fun randomDay() : String {
    val week = arrayOf ("Monday", "Tuesday", "Wednesday", "Thursday",
        "Friday", "Saturday", "Sunday")
    return week[Random().nextInt(week.size)]
}

fun fishFood (day : String) : String {
    return when (day) {
        "Monday" -> "flakes"
        "Wednesday" -> "redworms"
        "Thursday" -> "granules"
        "Friday" -> "mosquitoes"
        "Sunday" -> "plankton"
        else -> "nothing"
    }
}

//fun feedTheFish() {
//    val day = randomDay()
//    val food = fishFood(day)
//    println ("Today is $day and the fish eat $food")
//}
//feedTheFish()//Today is Friday and the fish eat mosquitoes


//4. Explore default values and compact functions
//Step 1: Create a default value for a parameter
fun swim(speed: String = "fast") {
    println("swimming $speed")
}
swim()   // uses default speed
swim("slow")   // positional argument
swim(speed="turtle-like")   // named parameter
//swimming fast
//swimming slow
//swimming turtle-like

//Step 2: Add required parameters
//fun shouldChangeWater (day: String, temperature: Int = 22, dirty: Int = 20): Boolean {
//    return when {
//        temperature > 30 -> true
//        dirty > 30 -> true
//        day == "Sunday" ->  true
//        else -> false
//    }
//}
//fun feedTheFish() {
//    val day = randomDay()
//    val food = fishFood(day)
//    println ("Today is $day and the fish eat $food")
//    println("Change water: ${shouldChangeWater(day)}")
//}
//feedTheFish()//Today is Saturday and the fish eat nothing
//Change water: false

//Step 3: Make compact functions
fun isTooHot(temperature: Int) = temperature > 30

fun isDirty(dirty: Int) = dirty > 30

fun isSunday(day: String) = day == "Sunday"
fun shouldChangeWater (day: String, temperature: Int = 22, dirty: Int = 20): Boolean {
    return when {
        isTooHot(temperature) -> true
        isDirty(dirty) -> true
        isSunday(day) -> true
        else  -> false
    }
}
fun feedTheFish() {
    val day = randomDay()
    val food = fishFood(day)
    println ("Today is $day and the fish eat $food")
    println("Change water: ${shouldChangeWater(day)}")
}
feedTheFish()

//5. Get started with filters
val decorations = listOf ("rock", "pagoda", "plastic plant", "alligator", "flowerpot")
//fun main() {
//    println( decorations.filter {it[0] == 'p'})
//}//[pagoda, plastic plant]

//Step 2: Compare eager and lazy filters
fun main() {
    val decorations = listOf ("rock", "pagoda", "plastic plant", "alligator", "flowerpot")

    // eager, creates a new list
    val eager = decorations.filter { it [0] == 'p' }
    println("eager: $eager")
    // lazy, will wait until asked to evaluate
    val filtered = decorations.asSequence().filter { it[0] == 'p' }
    println("filtered: $filtered")
    // force evaluation of the lazy list
    val newList = filtered.toList()
    println("new list: $newList")  // eager: [pagoda, plastic plant]
//    filtered: kotlin.sequences.FilteringSequence@386cc1c4
//    new list: [pagoda, plastic plant]
    val lazyMap = decorations.asSequence().map {
        println("access: $it")
        it
    }
    println("lazy: $lazyMap")
    println("-----")
    println("first: ${lazyMap.first()}")
    println("-----")
    println("all: ${lazyMap.toList()}")
    //lazy: kotlin.sequences.TransformingSequence@5ba23b66
    //-----
    //access: rock
    //first: rock
    //-----
    //access: rock
    //access: pagoda
    //access: plastic plant
    //access: alligator
    //access: flowerpot
    //all: [rock, pagoda, plastic plant, alligator, flowerpot]

    val lazyMap2 = decorations.asSequence().filter {it[0] == 'p'}.map {
        println("access: $it")
        it
    }
    println("-----")
    println("filtered: ${lazyMap2.toList()}")
//    -----
//    access: pagoda
//    access: plastic plant
//    filtered: [pagoda, plastic plant]
    val mysports = listOf("basketball", "fishing", "running")
    val myplayers = listOf("LeBron James", "Ernest Hemingway", "Usain Bolt")
    val mycities = listOf("Los Angeles", "Chicago", "Jamaica")
    val mylist = listOf(mysports, myplayers, mycities)     // list of lists
    println("-----")
    println("Flat: ${mylist.flatten()}")}
//    -----
//    Flat: [basketball, fishing, running, LeBron James, Ernest Hemingway, Usain Bolt, Los Angeles, Chicago, Jamaica]

//6. Get started with lambdas and higher-order functions

//Step 1: Learn about lambdas

//var dirtyLevel = 20
//val waterFilter = { dirty : Int -> dirty / 2}
//println(waterFilter(dirtyLevel))// 10
//val waterFilter: (Int) -> Int = { dirty -> dirty / 2 }

//Step 2: Create a higher-order function

fun updateDirty(dirty: Int, operation: (Int) -> Int): Int {
    return operation(dirty)
}
val waterFilter: (Int) -> Int = { dirty -> dirty / 2 }
println(updateDirty(30, waterFilter))// 15
fun increaseDirty( start: Int ) = start + 1

println(updateDirty(15, ::increaseDirty))// 16
var dirtyLevel = 19
dirtyLevel = updateDirty(dirtyLevel) { dirtyLevel -> dirtyLevel + 23}
println(dirtyLevel)//42 