package org.example

interface QueryInterface {
    /**
     * Retrieve data from a data source
     */
    fun from(dataSource: List< DataEntry >): Query

    /**
     * Filters data from the data source where
     * condition is true
     */
    fun where(vararg condition: (DataEntry) -> Boolean): Query

    /**
     * Groups the data based on the columns
     * returned by the group function
     */
    fun groupBy(group: (DataEntry) -> String): Query

    /**
     * Filters groups where condition is true
     */
    fun having(condition: Function1<Any, Boolean>): Query

    /**
     * Selects columns to be returned from the data source
     */
    fun select(selection: ((DataEntry) -> DataEntry)? = null): Query

    /**
     * Orders the data by specified column
     */
    fun orderBy(order: (DataEntry, DataEntry) -> Int): Query

    /**
     * Executes the query
     */
    fun execute(): List< DataEntry >
}