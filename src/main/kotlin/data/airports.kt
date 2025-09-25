package org.example.data

fun airport(id: Int, abbreviation: String, fullName: String, city: String): MutableMap<String, Any>  {
    return mutableMapOf(
        "id" to id,
        "abbreviation" to abbreviation,
        "fullName" to fullName,
        "city" to city
    )
}

fun airports(): MutableList<MutableMap<String, Any>> {
    return mutableListOf(
        airport(1, "ORD", "O'Hare International Airport", "Chicago"),
        airport(2, "PEK", "Beijing Capital International Airport", "Beijing"),
        airport(3, "LAX", "Los Angeles International Airport", "Los Angeles"),
        airport(4, "LGA", "LaGuardia Airport", "New York City"),
        airport(5, "DFS", "Dallas/Fort Worth Internatioal Airport", "Dallas"),
        airport(6, "BOS", "Logan International Airport", "Boston"),
        airport(7, "DXB", "Dubai International Airport", "Dubai"),
        airport(8, "CSF", "Fiftyville Regional Airport", "Fiftyville"),
        airport(9, "HND", "Tokyo International Airport", "Tokyo"),
        airport(10, "CDG", "Charles de Gaulle Airport", "Paris"),
        airport(11, "SFO", "San Francisco International Airport", "San Francisco"),
        airport(12, "DEL", "Indira Gandhi International Airport", "Delhi"),
    )
}
