package org.example

typealias DataEntry = Map<String, Any>

data class QueryChain(
    var from: List<DataEntry> = listOf(),
    var where: MutableList< List<(DataEntry) -> Boolean> > = mutableListOf(),
    var groupBy: MutableList<(DataEntry) -> String> = mutableListOf(),
    var having: MutableList< List<(Any) -> Boolean> > = mutableListOf(),
    var select: MutableList<(DataEntry) -> DataEntry> = mutableListOf(),
    var orderBy: MutableList<(DataEntry, DataEntry) -> Int> = mutableListOf(),
    var data: List<DataEntry> = mutableListOf(),
)

