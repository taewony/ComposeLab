fun max(a: Int, b: Int) = if (a>b) a else b

fun main() {
    println("max (9,5) = ${max(9, 5)} !")
    
    val fruitList = mutableListOf("apple", "banana")
    
  	fruitList.add("mellon")
    println("fruitList = $fruitList")
} 