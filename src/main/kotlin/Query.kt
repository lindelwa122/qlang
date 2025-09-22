package org.example

class Query(val queryChain: QueryChain) : QueryInterface {

    override fun from(dataSource: List< Map<String, Any >>): Query {
        if (this.queryChain.from.isNotEmpty()) {
            throw Exception("from should be empty")
        }

        this.queryChain.from = dataSource
        return Query(this.queryChain)
    }

    override fun where(condition: Function1<Map<String, Any>, Boolean>): Query {
        this.queryChain.where.add(listOf(condition))
        return Query(this.queryChain)
    }

    override fun groupBy(group: Function1<Map<String, Any>, String>): Query {
        this.queryChain.groupBy.add(group)
        return Query(this.queryChain)
    }

    override fun having(condition: Function1<Any, Boolean>): Query {
        this.queryChain.having.add(listOf(condition))
        return Query(this.queryChain)
    }

    override fun select(selection: Function1<Map<String, Any>, Map<String, Any>>): Query {
        this.queryChain.select.add(selection)
        return Query(this.queryChain)
    }

    override fun orderBy(order: (Map<String, Any>, Map<String, Any>) -> Int): Query {
        this.queryChain.orderBy.add(order)
        return Query(this.queryChain)
    }

    private fun groupByOp(
        groups: MutableList< (Map<String, Any>) -> String >): List<Map<String, Any>>
    {
        val dataSource = this.queryChain.data
        if (groups.isEmpty()) return dataSource

        val groupings = mutableMapOf<String, MutableList<Map<String, Any>>>()
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

        val results = mutableListOf<Map<String, Any>>()
        for (entry in groupings.entries) {
            val key = entry.key

            results.add( mapOf(Pair(key, this.groupByOp(
                groups = groups.subList(1, groups.size)
            ))) )
        }

        return results
    }

    private fun fromOp() {
        if (this.queryChain.from.isEmpty()) {
            throw Exception("from was not used to specify the data source")
        }

        this.queryChain.data = this.queryChain.from
    }

    private fun whereOp() {
        if (this.queryChain.where.isEmpty()) return

        val filteredData = mutableListOf< Map<String, Any> >()

        for (datum in this.queryChain.data) {
            for (functionArr in this.queryChain.where) {
                var addRes = false

                for (function in functionArr) {
                    if (function.invoke(datum)) {
                        addRes = true
                    }

                    else {
                        addRes = false
                        break
                    }
                }

                if (addRes) {
                    filteredData.add(datum)
                    break
                }
            }
        }

        this.queryChain.data = filteredData
    }

    private fun havingOp() {
        if (this.queryChain.having.isEmpty()) return

        if (this.queryChain.groupBy.isEmpty()) {
            throw Exception("having should be used along with groupBy")
        }

        val filteredGroups = mutableListOf< Map<String, Any> >()

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
            val selectedData = mutableListOf<Map<String, Any>>()

            for (datum in this.queryChain.data) {
                selectedData.add(this.queryChain.select[0].invoke(datum))
            }

            this.queryChain.data = selectedData
        }
    }

    private fun sort() {
        if (this.queryChain.orderBy.isEmpty()) return
        this.queryChain.data = this.queryChain.data.sortedWith { val1, val2 ->
            this.queryChain.orderBy[0].invoke(val1, val2)
        }
    }

    override fun execute(): List< Map<String, Any> > {
        // FROM: Get data from the data source
        this.fromOp()

        // WHERE: Filter data source
        this.whereOp()

        // GROUP_BY: Group the data using these functions
        this.groupByOp(groups = this.queryChain.groupBy)

        // HAVING: Filter groups
        this.havingOp()

        // SELECT: Select columns to return
        this.selectOp()

        // SORT: Sort data
        this.sort()

        return this.queryChain.data
    }
}