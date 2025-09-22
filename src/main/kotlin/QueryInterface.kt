package org.example

interface QueryInterface {
    /**
     * Retrieve data from a data source
     */
    fun from(dataSource: List< Map<String, Any> >): Query

    /**
     * Filters data from the data source where
     * condition is true
     */
    fun where(condition: (Map<String, Any>) -> Boolean): Query

    /**
     * Groups the data based on the columns
     * returned by the group function
     */
    fun groupBy(group: (Map<String, Any>) -> String): Query

    /**
     * Filters groups where condition is true
     */
    fun having(condition: Function1<Any, Boolean>): Query

    /**
     * Selects columns to be returned from the data source
     */
    fun select(selection: (Map<String, Any>) -> Map<String, Any>): Query

    /**
     * Orders the data by specified column
     */
    fun orderBy(order: (Map<String, Any>, Map<String, Any>) -> Int): Query

    /**
     * Executes the query
     */
    fun execute(): List< Map<String, Any> >
}