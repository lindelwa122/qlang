package org.example

data class QueryChain(
    var from: List< Map<String, Any> > = listOf(),
    var where: MutableList< List< (Map<String, Any>) -> Boolean > > = mutableListOf(),
    var groupBy: MutableList< (Map<String, Any>) -> String > = mutableListOf(),
    var having: MutableList< List< (Any) -> Boolean > > = mutableListOf(),
    var select: MutableList< (Map<String, Any>) -> Map<String, Any> > = mutableListOf(),
    var orderBy: MutableList< (Map<String, Any>, Map<String, Any>) -> Int > = mutableListOf(),
    var data: List< Map<String, Any> > = mutableListOf(),
)

