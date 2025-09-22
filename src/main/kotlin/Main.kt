package org.example

fun persons(name: String, profession: String, age: Int, maritalStatus: String): MutableMap<String, Any> {
    return mutableMapOf(
        "name" to name,
        "profession" to profession,
        "age" to age,
        "maritalStatus" to maritalStatus
    )
}

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {
    val dataSource = mutableListOf(
        persons(name = "Peter", profession = "teacher", age = 20, maritalStatus = "married"),
        persons(name = "Michael", profession = "teacher", age = 50, maritalStatus = "single"),
        persons(name = "Peter", profession = "teacher", age = 20, maritalStatus = "married"),
        persons(name = "Anna", profession = "scientific", age = 20, maritalStatus = "married"),
        persons(name = "Rose", profession = "scientific", age = 50, maritalStatus = "married"),
        persons(name = "Anna", profession = "scientific", age = 20, maritalStatus = "single"),
        persons(name = "Anna", profession = "politician", age = 50, maritalStatus = "married"),
    )

    val res = Query(QueryChain())
        .select { data ->
            mapOf(
                "name" to data["name"]!!,
                "profession" to data["profession"]!!,
                "age / 10" to data["age"]!! as Int / 10
            )
        }
        .from(dataSource)
        .execute()

    println(res)
}