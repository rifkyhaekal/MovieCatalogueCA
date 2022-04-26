package com.albro.alfamoviecatalogue.core.data.source.local.entity

import androidx.room.ColumnInfo

data class TvShowDetailEntity(

    @ColumnInfo(name = "overview")
    var overview: String?,

    @ColumnInfo(name = "status")
    var status: String?,

    @ColumnInfo(name = "type")
    var type: String?,

    @ColumnInfo(name = "genre")
    var genre: String?,

    @ColumnInfo(name = "network")
    var network: String?,

    @ColumnInfo(name = "userScore")
    var userScore: Float,

    @ColumnInfo(name = "backdropPath")
    var backdropPath: String?

)
