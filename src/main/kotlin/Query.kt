package org.example

import org.example.exceptions.DuplicateFromError
import org.example.exceptions.DuplicateSelectError

class Query(val queryChain: QueryChain = QueryChain()) : QueryInterface {

    override fun from(dataSource: List< Map<String, Any >>): Query {
        if (this.queryChain.from.isNotEmpty()) {
            throw DuplicateFromError("from can only be used exactly once")
        }

        this.queryChain.from = dataSource
        return Query(this.queryChain)
    }

    override fun where(vararg condition: Function1<DataEntry, Boolean>): Query {
        this.queryChain.where.add(condition.toList())
        return Query(this.queryChain)
    }

    override fun groupBy(group: Function1<DataEntry, String>): Query {
        this.queryChain.groupBy.add(group)
        return Query(this.queryChain)
    }

    override fun having(condition: Function1<Any, Boolean>): Query {
        this.queryChain.having.add(listOf(condition))
        return Query(this.queryChain)
    }

    override fun select(selection: (Function1<DataEntry, DataEntry>)?): Query {
        if (selection == null) return Query(this.queryChain)

        if (this.queryChain.select.isNotEmpty()) {
            throw DuplicateSelectError("select can only be used once")
        }

        this.queryChain.select.add(selection)
        return Query(this.queryChain)
    }

    override fun orderBy(order: (DataEntry, DataEntry) -> Int): Query {
        this.queryChain.orderBy.add(order)
        return Query(this.queryChain)
    }

    private fun groupByOp(
        dataSource: List< DataEntry >,
        groups: MutableList< (DataEntry) -> String >): List<DataEntry>
    {
        if (groups.isEmpty()) {
            val sortedDataSource =
                if (this.queryChain.orderBy.isNotEmpty()) this.sort(dataSource) else dataSource

            val results = mutableListOf<DataEntry>()

            if (this.queryChain.select.isNotEmpty()) {
                val selection = this.queryChain.select[0]

                for (datum in sortedDataSource) {
                    results.add(selection(datum))
                }
            }

            else {
                results.addAll(sortedDataSource)
            }

            return results
        }

        val groupings = mutableMapOf<String, MutableList<DataEntry>>()
        val function = groups[0]
        for (datum in dataSource) {
            val group: String = function(datum)

            if (groupings.containsKey(group)) {
                groupings[group]?.add(datum)
            }

            else {
                groupings[group] = mutableListOf(datum)
            }
        }

        val results = mutableListOf<DataEntry>()
        for (entry in groupings.entries) {
            val key = entry.key
            val value = entry.value

            results.add( mapOf(Pair(key, this.groupByOp(
                dataSource = value,
                groups = groups.subList(1, groups.size)
            ))) )
        }

        return results
    }

    private fun fromOp() {
        this.queryChain.data = this.queryChain.from
    }

    private fun whereOp() {
        if (this.queryChain.where.isEmpty()) return

        val filteredData = mutableListOf< DataEntry >()

        for (datum in this.queryChain.data) {
            var andRes = false
            for (functionArr in this.queryChain.where) {
                var orRes = false

                for (function in functionArr) {
                    if (function.invoke(datum)) {
                        orRes = true
                        break
                    }

                    else {
                        orRes = false
                    }
                }

                if (orRes) {
                    andRes = true
                }

                else {
                    andRes = false
                    break
                }
            }

            if (andRes) {
                filteredData.add(datum)
            }
        }

        this.queryChain.data = filteredData
    }

    private fun havingOp() {
        if (this.queryChain.having.isEmpty()) return

        if (this.queryChain.groupBy.isEmpty()) {
            throw Exception("having should be used along with groupBy")
        }

        val filteredGroups = mutableListOf< DataEntry >()

        for (datum in this.queryChain.data) {
            for (functionArr in this.queryChain.having) {
                var addRes = false

                for (function in functionArr) {
                    if (function.invoke(datum.values.toList()[0])) {
                        addRes = true
                    }

                    else {
                        addRes = false
                        break
                    }
                }

                if (addRes) {
                    filteredGroups.add(datum)
                    break
                }
            }
        }

        this.queryChain.data = filteredGroups
    }

    private fun selectOp() {
        if (this.queryChain.select.isNotEmpty()) {
            val selectedData = mutableListOf<DataEntry>()

            for (datum in this.queryChain.data) {
                selectedData.add(this.queryChain.select[0].invoke(datum))
            }

            this.queryChain.data = selectedData
        }
    }

    private fun sort(dataSource: List<DataEntry>): List<DataEntry> {
        if (this.queryChain.orderBy.isEmpty()) return dataSource
        return dataSource.sortedWith { val1, val2 ->
            this.queryChain.orderBy[0].invoke(val1, val2)
        }
    }

    override fun execute(): List< DataEntry > {
        // FROM: Get data from the data source
        this.fromOp()

        // WHERE: Filter data source
        this.whereOp()

        // GROUP_BY: Group the data using these functions
        if (this.queryChain.groupBy.isNotEmpty()) {
            this.queryChain.data = this.groupByOp(dataSource = this.queryChain.data, groups = this.queryChain.groupBy)

            // HAVING: Filter groups
            this.havingOp()
        }

        else {
            // SORT: Sort data
            this.queryChain.data = this.sort(this.queryChain.data)

            // SELECT: Select columns to return
            this.selectOp()
        }

        return this.queryChain.data
    }
}