package org.example

fun person(name: String, profession: String, age: Int, maritalStatus: String): MutableMap<String, Any> {
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
        person(name = "Peter", profession = "teacher", age = 22, maritalStatus = "married"),
        person(name = "Michael", profession = "teacher", age = 51, maritalStatus = "single"),
        person(name = "Peter", profession = "teacher", age = 19, maritalStatus = "married"),
        person(name = "Anna", profession = "scientific", age = 22, maritalStatus = "married"),
        person(name = "Rose", profession = "scientific", age = 47, maritalStatus = "married"),
        person(name = "Anna", profession = "scientific", age = 29, maritalStatus = "single"),
        person(name = "Anna", profession = "politician", age = 49, maritalStatus = "married"),
    )

    fun orderByAsc(entry1: DataEntry, entry2: DataEntry): Int {
        val age1 = entry1["age"] as Int
        val age2 = entry2["age"] as Int

        return if (age1 > age2) 1
        else if (age1 < age2) -1
        else 0
    }

//    val res = Query()
//        .select { entry ->
//            mapOf("age" to entry["age"]!!, "name" to entry["name"]!!)
//        }
//        .from(dataSource)
////        .where({ entry -> entry["profession"] == "teacher" })
////        .where({ entry -> entry["maritalStatus"] == "married"})
//        .orderBy(::orderByAsc)
//        .execute()

    val query = Query()
    var res = query.select { entry ->
            mapOf("age" to entry["age"]!!, "name" to entry["name"]!!)
        }

    res = res.from(dataSource)

    res = res.orderBy(::orderByAsc)

    prettyPrintTable(res.execute())
}