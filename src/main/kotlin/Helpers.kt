package org.example

fun prettyPrintTable(data: List<DataEntry>) {
    if (data.isEmpty()) {
        println("No data to display.")
        return
    }

    val columns = data.flatMap { it.keys }.distinct().sorted()

    val maxLengths = columns.associateWith { column ->
        data.maxOfOrNull { it[column]?.toString()?.length ?: 0 }?.coerceAtLeast(column.length) ?: column.length
    }

    println(columns.joinToString(" | ") { column ->
        "%-${maxLengths[column]}s".format(column)
    })
    println("-".repeat(maxLengths.values.sum() + (columns.size - 1) * 3))

    data.forEach { row ->
        println(columns.joinToString(" | ") { column ->
            "%-${maxLengths[column]}s".format(row[column]?.toString() ?: "")
        })
    }
}

fun getValues(entries: List<Map<String, Any>>, value: String): MutableList<Any?> {
    val results = mutableListOf<Any?>()

    for (entry in entries) {
        results.add(entry[value])
    }

    return results
}