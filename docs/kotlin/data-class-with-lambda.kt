package org.example

data class User (
    val name: String,
    val age: Int? = null
)
fun main() {

    val users = listOf(
        User("taewony", 30),
        User("xxx"),
    )
    
    val oldest = users.maxBy {
    	it.age ?:0
    }
    
    println("가장 나이가 많은 사람: $oldest")
}