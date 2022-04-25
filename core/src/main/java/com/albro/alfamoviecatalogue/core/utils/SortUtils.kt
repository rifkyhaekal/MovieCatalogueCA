package com.example.haekalmoviecatalogue.utils

import androidx.sqlite.db.SimpleSQLiteQuery

object SortUtils {

    const val NEWEST = "Newest"
    const val OLDEST = "Oldest"
    const val RANDOM = "Random"

    fun getSortedMovieQuery(filter: String): SimpleSQLiteQuery {
        val simpleQuery = StringBuilder().append("SELECT * FROM movieentities ")
        when (filter) {
            NEWEST -> {
                simpleQuery.append("ORDER BY movieId ASC")
            }
            OLDEST -> {
                simpleQuery.append("ORDER BY movieId DESC")
            }
            RANDOM -> {
                simpleQuery.append("ORDER BY RANDOM()")
            }
        }
        return SimpleSQLiteQuery(simpleQuery.toString())
    }

    fun getSortedTvShowQuery(filter: String): SimpleSQLiteQuery {
        val simpleQuery = StringBuilder().append("SELECT * FROM tvshowentities ")
        when (filter) {
            NEWEST -> {
                simpleQuery.append("ORDER BY tvShowId ASC")
            }
            OLDEST -> {
                simpleQuery.append("ORDER BY tvShowId DESC")
            }
            RANDOM -> {
                simpleQuery.append("ORDER BY RANDOM()")
            }
        }
        return SimpleSQLiteQuery(simpleQuery.toString())
    }
}